package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.example.Game;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import utilizations.LoadSave;
import static utilizations.constants.Environments.*;

public class Playing extends State implements Statemethods{

    private Player player;
    private GameOverOverlay gameOverOverlay;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private boolean pausedOrNot = false;
    private boolean gameOver;

    private int xLevelOffset;
    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxXLevelOffset = maxTilesOffset * Game.TILES_SIZE;

    private BufferedImage movingBackground, bigCloudImage, smallCloudImage, bigCloudImage2, handsImage, bottomImage;
    private int[] smallClouds;
    private Random rand = new Random();

    public Playing(Game game) {
        super(game);
        initializeClasses();

        movingBackground = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND);
        bigCloudImage = LoadSave.GetSpriteAtlas(LoadSave.CLOUD_BIG);
        smallCloudImage = LoadSave.GetSpriteAtlas(LoadSave.CLOUD_SMALL);
        bigCloudImage2 = LoadSave.GetSpriteAtlas(LoadSave.CLOUD_BIG2);
        handsImage = LoadSave.GetSpriteAtlas(LoadSave.HANDS);
        bottomImage = LoadSave.GetSpriteAtlas(LoadSave.BOTTOM);
        smallClouds = new int[8];

        for (int i = 0; i < smallClouds.length; i++) 
            smallClouds[i] = (int)(120 * Game.SCALE) + rand.nextInt((int)(70*Game.SCALE));
    }

    private void initializeClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);

        // 130 and 570 for proper start or 200 for fall
        player = new Player(130, 200, (int)(64*Game.SCALE), (int)(64*Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    @Override
    public void update() {
        if(!pausedOrNot && !gameOver) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkIfAtBorder();
        }
        else {
            pauseOverlay.update();
        }
    }

    private void checkIfAtBorder() {
        int playerX = (int) player.getHitbox().x;
        int difference = playerX - xLevelOffset;

        if(difference > rightBorder) 
            xLevelOffset += difference - rightBorder;
        else if(difference < leftBorder) 
            xLevelOffset += difference - leftBorder;
        

        if(xLevelOffset > maxXLevelOffset) 
            xLevelOffset = maxXLevelOffset;
        else if(xLevelOffset < 0) 
            xLevelOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(movingBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);  
        
        drawClouds(g);

         // must change for smaller scale
        g.drawImage(handsImage, HANDS_X - (int)(xLevelOffset), HANDS_Y, HANDS_WIDTH, HANDS_HEIGHT, null);
        g.drawImage(bottomImage, GROUND_X - (int)(xLevelOffset), GROUND_Y, Game.GAME_WIDTH / 3, Game.GAME_HEIGHT / 3, null);

        levelManager.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);

        if(pausedOrNot) {
            g.setColor(new Color(0,0,0, 175));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
        else if(gameOver) {
            gameOverOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        // must change for smaller scale
        for (int i = 0; i < 4; i++) 
            g.drawImage(bigCloudImage2, -150 + i * BIG_CLOUD_WIDTH - (int)(xLevelOffset * 0.2), (int)(200 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT2, null);

        // change inner loop for smaller scale
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 16; j++)
                g.drawImage(bigCloudImage, 0 + i * BIG_CLOUD_WIDTH - (int)(xLevelOffset * 0.3), 0 + j*CLOUD_Y_REPEAT + (int)(224 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallClouds.length; i++)
            g.drawImage(smallCloudImage, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLevelOffset * 0.7), smallClouds[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver) {
            if(e.getButton() == MouseEvent.BUTTON1) {
                player.setAttack(true);
            }

            if(e.getButton() == MouseEvent.BUTTON3) {
                player.setAttack2(true);
            }

            if(e.getButton() == MouseEvent.BUTTON2) {
                player.setDead(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver)
            if (pausedOrNot) {
                pauseOverlay.mousePressed(e);
            }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver)
            if (pausedOrNot) {
                pauseOverlay.mouseReleased(e);
            }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver)
            if (pausedOrNot) {
                pauseOverlay.mouseMoved(e);
            }
    }

    public void mouseDragged(MouseEvent e) {
        if(!gameOver)
            if(pausedOrNot) {
                pauseOverlay.mouseDragged(e);
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver){
            gameOverOverlay.keyPressed(e);
        }
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true); 
                    break;  
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break; 
                case KeyEvent.VK_ESCAPE:
                    pausedOrNot = !pausedOrNot;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;  
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;  
            }
    }

    public void unpauseGame() {
        pausedOrNot = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetDirectionBooleans();
    }

    public void resetAll() {
        gameOver = false;
        pausedOrNot = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}

package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import com.example.Game;
import entities.Player;
import levels.LevelManager;
import ui.PauseOverlay;

public class Playing extends State implements Statemethods{

    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean pausedOrNot = false;

    public Playing(Game game) {
        super(game);
        initializeClasses();
    }

    private void initializeClasses() {
        levelManager = new LevelManager(game);
        // 130 and 570 for proper start
        player = new Player(130, 200, (int)(64*Game.SCALE), (int)(64*Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if(!pausedOrNot) {
            levelManager.update();
            player.update();
        }
        else {
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);

        if(pausedOrNot) {
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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

    @Override
    public void mousePressed(MouseEvent e) {
        if (pausedOrNot) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pausedOrNot) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (pausedOrNot) {
            pauseOverlay.mouseMoved(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(pausedOrNot) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
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

    @Override
    public void keyReleased(KeyEvent e) {
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
}

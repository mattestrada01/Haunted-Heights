package com.example;

import java.awt.Graphics;
import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoopThread;
    private final int FPS = 130;
    private final int UPS = 200;
    
    public final static int TILES_DEFAULT = 32;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private Player player;
    private LevelManager levelManager;

    public Game() {
        initializeClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initializeClasses() {
        levelManager = new LevelManager(this);
        // 130 and 570 for proper start
        player = new Player(130, 200, (int)(64*SCALE), (int)(64*SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
    }

    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }

    public void update() {
        levelManager.update();
        player.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }


    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        long previousTime = System.nanoTime();


        int frameCount = 0;
        long lastChecked = System.currentTimeMillis();
        long updateCount = 0;

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updateCount++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                deltaF--;
                frameCount++;
            }
        
            if(System.currentTimeMillis() - lastChecked >= 1000) {
                lastChecked = System.currentTimeMillis();
                System.out.println("FPS - " + frameCount + " | UPS - " + updateCount);
                frameCount = 0;
                updateCount = 0;
            }
        }
    }

    public void windowFocusLost() {
        player.resetDirectionBooleans();
    }

    public Player getPlayer() {
        return player;
    }

}


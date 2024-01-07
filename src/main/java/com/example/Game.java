package com.example;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoopThread;
    private final int FPS = 143;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }


    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frameCount = 0;
        long lastChecked = System.currentTimeMillis();

        while(true) {
            now = System.nanoTime();
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frameCount++;
            }
        
        if(System.currentTimeMillis() - lastChecked >= 1000) {
            lastChecked = System.currentTimeMillis();
            System.out.println("FPS - " + frameCount);
            frameCount = 0;
        }
        }
    }
}

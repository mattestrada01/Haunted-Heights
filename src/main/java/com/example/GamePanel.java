package com.example;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;
import com.example.inputs.KeyboardInputs;
import com.example.inputs.MouseInputs;

public class GamePanel extends JPanel{

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDirection = 1f, yDirection = 1f;
    private int frameCount = 0;
    private long lastChecked = 0;
    private Color color = Color.PINK;
    private Random random;

    public GamePanel() {
        random = new Random();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void changeXDelta(int value) {
        this.xDelta += value;
    }

    public void changeYDelta(int value) {
        this.yDelta += value;
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);

        g.fillRect((int)xDelta, (int)yDelta, 200, 45);
    }

    private void updateRectangle() {
        xDelta+=xDirection;
        if (xDelta > 400 || xDelta < 0) {
            xDirection *= -1;
            color = getRandomColor();
        }
        
        yDelta+=yDirection;
        if (yDelta > 400 || yDelta < 0) {
            yDirection *= -1;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return new Color(r,g,b);
    }
}

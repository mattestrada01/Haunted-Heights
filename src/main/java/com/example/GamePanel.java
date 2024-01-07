package com.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import com.example.inputs.KeyboardInputs;
import com.example.inputs.MouseInputs;

public class GamePanel extends JPanel{

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image, subImage;


    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImage();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void importImage() {
        File is = new File("src/main/resources/player_sprites.png");
    
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension dimension = new Dimension(1280, 800);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
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
        subImage = image.getSubimage(1*64, 8*40, 64, 40);
        g.drawImage(subImage, (int)xDelta, (int)yDelta, 128, 80, null);
    }
}

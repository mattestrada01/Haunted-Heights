package com.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import com.example.inputs.KeyboardInputs;
import com.example.inputs.MouseInputs;
import static utilizations.constants.PlayerConstants.*;
import static utilizations.constants.Directions.*;


public class GamePanel extends JPanel{

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage image;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 30;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;


    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImage();
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
        requestFocusInWindow();
    }

    private void loadAnimations() {
        animations = new BufferedImage[7][10];
        for (int j = 0; j < animations.length; j++){
             for (int i = 0; i < animations[j].length; i++){
                animations[j][i] = image.getSubimage(i*128, j*128, 128, 128);
        }
        }
    }

    private void importImage() {
        File is = new File("src/main/resources/enchant_sprite.png");
    
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

    public void setDirection(int direction) {
        this.playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteID(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        }
        else {
            playerAction = IDLE;
        }
    }

    private void updatePostion() {
        if(moving) {
            switch(playerDirection) {
                case LEFT:
                    xDelta -= 3;
                    break;
                case UP:
                    yDelta -= 3;
                    break;
                case RIGHT:
                    xDelta += 3;
                    break;
                case DOWN:
                    yDelta += 3;
                    break;
            }
        }
    }

    public void updateGame() {
        updateAnimation();
        setAnimation();
        updatePostion();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animations[playerAction][animationIndex], (int)xDelta, (int)yDelta, 192, 192, null);
    }
}

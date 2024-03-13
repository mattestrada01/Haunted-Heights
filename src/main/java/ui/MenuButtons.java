package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import gamestates.Gamestate;
import utilizations.LoadSave;
import static utilizations.constants.UI.Buttons.*;

public class MenuButtons {

    private int xPos, yPos, row, index;
    private Gamestate state;
    private BufferedImage[] images;
    private int xOffsetCenter = B_WIDTH / 2;
    private boolean mousePressed, mouseOver;
    private Rectangle bounds;

    public MenuButtons(int xPos, int yPos, int row, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.row = row;
        this.state = state;
        loadImages();
        initializeBounds();
    }

    private void initializeBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, row * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update() {
        index = 0;
        if(mouseOver) {
            index = 1;
        }
        if(mousePressed) {
            index = 2;
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGameState() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public Gamestate getState() {
        return state;
    }

}

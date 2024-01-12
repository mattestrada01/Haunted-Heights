package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilizations.constants.UI.URMButtons.*;

import utilizations.LoadSave;

public class Urm extends PauseButtons {

    private BufferedImage[] images;
    private int row, index;
    private boolean mouseOver, mousePressed;

    public Urm(int x, int y, int width, int height, int row) {
        super(x, y, width, height);
        this.row = row;
        loadImages();
    }
    
    private void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        images = new BufferedImage[3];
        for(int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, row * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
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

    public void draw(Graphics g) {
        g.drawImage(images[index], x, y, URM_SIZE, URM_SIZE, null);
    }
    
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
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

}

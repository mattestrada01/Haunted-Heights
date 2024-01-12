package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilizations.constants.UI.PauseButtons.*;
import utilizations.LoadSave;

public class SoundButtons extends PauseButtons{

    private BufferedImage[][] soundImages;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int row, column;

    public SoundButtons(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTON);
        soundImages = new BufferedImage[2][3]; 
        for (int j = 0; j < soundImages.length; j++) {
            for(int i = 0; i < soundImages[j].length; i++) {
                soundImages[j][i] = temp.getSubimage(i * SOUND_DIMENSION_DEFAULT, j * SOUND_DIMENSION_DEFAULT, SOUND_DIMENSION_DEFAULT, SOUND_DIMENSION_DEFAULT);
            }
        }
    }

    public void update () {
        if(!muted) {
            row = 0;
        }

        else  {
            row = 1;
        }

        column = 0;

        if (mouseOver) {
            column = 1;
        }

        if (mousePressed) {
            column = 2;
        }
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(soundImages[row][column], x, y, width, height, null);
    }

    public BufferedImage[][] getSoundImages() {
        return soundImages;
    }

    public void setSoundImages(BufferedImage[][] soundImages) {
        this.soundImages = soundImages;
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    
}

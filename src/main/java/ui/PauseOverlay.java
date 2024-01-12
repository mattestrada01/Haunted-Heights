package ui;

import static utilizations.constants.UI.PauseButtons.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import com.example.Game;
import entities.Player;
import gamestates.Gamestate;
import utilizations.LoadSave;

public class PauseOverlay {

    private BufferedImage image;
    private int bgX, bgY, bgHeight, bgWidth;
    private SoundButtons musicButtons, sfxButtons;

    public PauseOverlay() {
        loadBackground();
        createSoundButtons();
    }

    private void createSoundButtons() {
        int soundX = (int)(445 * Game.SCALE);
        int musicY = (int)(148 * Game.SCALE);
        int sfxY = (int)(193 * Game.SCALE);
        musicButtons = new SoundButtons(soundX, musicY, SOUND_DIMENSION, SOUND_DIMENSION);
        sfxButtons = new SoundButtons(soundX, sfxY, SOUND_DIMENSION, SOUND_DIMENSION);
    }

    private void loadBackground() {
        image = LoadSave.GetSpriteAtlas(LoadSave.PAUSE);
        bgWidth = (int)(image.getWidth() * Game.SCALE);
        bgHeight = (int)(image.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int)(30 * Game.SCALE);
    }

    public void update() {
        musicButtons.update();
        sfxButtons.update();
    }

    public void draw(Graphics g) {
        // background image
        g.drawImage(image, bgX, bgY, bgWidth, bgHeight, null);

        // the sound buttons
        musicButtons.draw(g);
        sfxButtons.draw(g);
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
        if(isIn(e, musicButtons)) {
            musicButtons.setMousePressed(true);
        }
        else if (isIn(e, sfxButtons)){
            sfxButtons.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, musicButtons)) {
            if(musicButtons.isMousePressed()){
                musicButtons.setMuted(!musicButtons.isMuted());
            }
        }

        else if (isIn(e, sfxButtons)){
            if(sfxButtons.isMousePressed()){
                sfxButtons.setMuted(!sfxButtons.isMuted());
            }
        }

        musicButtons.resetBools();
        sfxButtons.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
       musicButtons.setMouseOver(false);
       sfxButtons.setMouseOver(false);

       if(isIn(e, musicButtons)) {
        musicButtons.setMouseOver(true);
        }
        else if (isIn(e, sfxButtons)){
            sfxButtons.setMouseOver(true);
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, PauseButtons b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}

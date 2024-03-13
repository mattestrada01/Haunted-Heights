package ui;

import static utilizations.constants.UI.PauseButtons.*;
import static utilizations.constants.UI.VolumeButtons.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.example.Game;

import gamestates.Gamestate;

public class AudioOptions {

    private Volume volumeButton;
    private SoundButtons musicButtons, sfxButtons;

    public AudioOptions() {
        createSoundButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeX = (int)(309 * Game.SCALE);
        int volumeY = (int)(283 * Game.SCALE);

        volumeButton = new Volume(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createSoundButtons() {
        int soundX = (int)(445 * Game.SCALE);
        int musicY = (int)(145 * Game.SCALE);
        int sfxY = (int)(190 * Game.SCALE);
        musicButtons = new SoundButtons(soundX, musicY, SOUND_DIMENSION, SOUND_DIMENSION);
        sfxButtons = new SoundButtons(soundX, sfxY, SOUND_DIMENSION, SOUND_DIMENSION);
    }

    public void update() {
        musicButtons.update();
        sfxButtons.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        // the sound buttons
        musicButtons.draw(g);
        sfxButtons.draw(g);

        // slider button
        volumeButton.draw(g);
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
        if(isIn(e, musicButtons)) {
            musicButtons.setMousePressed(true);
        }
        else if (isIn(e, sfxButtons)){
            sfxButtons.setMousePressed(true);
        }
        else if(isIn(e, volumeButton)){
            volumeButton.setMousePressed(true);
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
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
       musicButtons.setMouseOver(false);
       sfxButtons.setMouseOver(false);
       volumeButton.setMouseOver(false);

       if(isIn(e, musicButtons)) {
        musicButtons.setMouseOver(true);
        }
        else if (isIn(e, sfxButtons)){
            sfxButtons.setMouseOver(true);
        }
        else if (isIn(e, volumeButton)){
            volumeButton.setMouseOver(true);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if(volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    private boolean isIn(MouseEvent e, PauseButtons b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}

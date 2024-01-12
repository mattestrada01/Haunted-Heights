package ui;

import static utilizations.constants.UI.PauseButtons.*;
import static utilizations.constants.UI.URMButtons.*;
import static utilizations.constants.UI.VolumeButtons.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import com.example.Game;
import entities.Player;
import gamestates.Gamestate;
import gamestates.Playing;
import utilizations.LoadSave;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage image;
    private int bgX, bgY, bgHeight, bgWidth;
    private SoundButtons musicButtons, sfxButtons;
    private Urm menuButton, replayButton, unpauseButton;
    private Volume volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createURMButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeX = (int)(309 * Game.SCALE);
        int volumeY = (int)(283 * Game.SCALE);

        volumeButton = new Volume(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createURMButtons() {
        int menuX = (int)(313 * Game.SCALE);
        int replayX = (int)(387 * Game.SCALE);
        int unpauseX = (int)(462 * Game.SCALE);
        int buttonY = (int)(330 * Game.SCALE);

        menuButton = new Urm(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
        replayButton = new Urm(replayX, buttonY, URM_SIZE, URM_SIZE, 1);
        unpauseButton = new Urm(unpauseX, buttonY, URM_SIZE, URM_SIZE, 0);
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

        menuButton.update();
        replayButton.update();
        unpauseButton.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        // background image
        g.drawImage(image, bgX, bgY, bgWidth, bgHeight, null);

        // the sound buttons
        musicButtons.draw(g);
        sfxButtons.draw(g);

        // the urm buttons
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

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
        else if(isIn(e, menuButton)){
            menuButton.setMousePressed(true);
        }
        else if(isIn(e, replayButton)){
            replayButton.setMousePressed(true);
        }
        else if(isIn(e, unpauseButton)){
            unpauseButton.setMousePressed(true);
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
        else if (isIn(e, menuButton)){
            if(menuButton.isMousePressed()){
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }
        else if (isIn(e, replayButton)){
            if(replayButton.isMousePressed()){
                System.out.println("replay the level");
            }
        }
        else if (isIn(e, unpauseButton)){
            if(unpauseButton.isMousePressed()){
                playing.unpauseGame();
            }
        }

        musicButtons.resetBools();
        sfxButtons.resetBools();
        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
       musicButtons.setMouseOver(false);
       sfxButtons.setMouseOver(false);
       menuButton.setMouseOver(false);
       replayButton.setMouseOver(false);
       unpauseButton.setMouseOver(false);
       volumeButton.setMouseOver(false);

       if(isIn(e, musicButtons)) {
        musicButtons.setMouseOver(true);
        }
        else if (isIn(e, sfxButtons)){
            sfxButtons.setMouseOver(true);
        }
        else if (isIn(e, menuButton)){
            menuButton.setMouseOver(true);
        }
        else if (isIn(e, replayButton)){
            replayButton.setMouseOver(true);
        }
        else if (isIn(e, unpauseButton)){
            unpauseButton.setMouseOver(true);
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

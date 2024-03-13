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

    private AudioOptions audioOptions;
    private Playing playing;
    private BufferedImage image;
    private int bgX, bgY, bgHeight, bgWidth;
    
    private Urm menuButton, replayButton, unpauseButton;
    

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createURMButtons();
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

    

    private void loadBackground() {
        image = LoadSave.GetSpriteAtlas(LoadSave.PAUSE);
        bgWidth = (int)(image.getWidth() * Game.SCALE);
        bgHeight = (int)(image.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int)(30 * Game.SCALE);
    }

    public void update() {
        menuButton.update();
        replayButton.update();
        unpauseButton.update();
        audioOptions.update();  
    }

    public void draw(Graphics g) {
        // background image
        g.drawImage(image, bgX, bgY, bgWidth, bgHeight, null);

        // the urm buttons
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        audioOptions.draw(g);
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
        if(isIn(e, menuButton)){
            menuButton.setMousePressed(true);
        }
        else if(isIn(e, replayButton)){
            replayButton.setMousePressed(true);
        }
        else if(isIn(e, unpauseButton)){
            unpauseButton.setMousePressed(true);
        }
        else {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)){
            if(menuButton.isMousePressed()){
                playing.setGameState(Gamestate.MENU);
                playing.unpauseGame();
            }
        }
        else if (isIn(e, replayButton)){
            if(replayButton.isMousePressed()){
                playing.resetAll();
                playing.unpauseGame();
            }
        }
        else if (isIn(e, unpauseButton)){
            if(unpauseButton.isMousePressed()){
                playing.unpauseGame();
            }
        }else {
            audioOptions.mouseReleased(e);
        }

        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
       menuButton.setMouseOver(false);
       replayButton.setMouseOver(false);
       unpauseButton.setMouseOver(false);

       if (isIn(e, menuButton)){
            menuButton.setMouseOver(true);
        }
        else if (isIn(e, replayButton)){
            replayButton.setMouseOver(true);
        }
        else if (isIn(e, unpauseButton)){
            unpauseButton.setMouseOver(true);
        }
        else {
            audioOptions.mouseMoved(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    private boolean isIn(MouseEvent e, PauseButtons b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}

package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import com.example.Game;
import ui.AudioOptions;
import ui.PauseButtons;
import ui.Urm;
import utilizations.LoadSave;

import static utilizations.constants.UI.URMButtons.*;

public class GameOptions extends State implements Statemethods {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImage, optionsBackgroundImage;
    private int bgX, bgY, bgWidth, bgHeight;
    private Urm menuButton;

    public GameOptions(Game game) {
        super(game);
        loadImages();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadButton() {
        int menuX = (int)(387*Game.SCALE);
        int menuY = (int)(330*Game.SCALE);

        menuButton = new Urm(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImages() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_BACKGROUND);
        optionsBackgroundImage = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

        bgX = Game.GAME_WIDTH / 3 - bgWidth / 2;
        bgWidth = (int)(optionsBackgroundImage.getWidth() * Game.SCALE);

        bgY = (int)(35*Game.SCALE);
        bgHeight = (int)(optionsBackgroundImage.getHeight() * Game.SCALE);
    }

    @Override
    public void update() {
        menuButton.update();   
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImage, bgX, bgY, bgWidth, bgHeight, null);

        menuButton.draw(g);
        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e, menuButton)) {
            menuButton.setMousePressed(true);
        }
        else{
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e, menuButton)) {
            if(menuButton.isMousePressed())
                Gamestate.state = Gamestate.MENU;
        }
        else{
            audioOptions.mouseReleased(e);
        }

        menuButton.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if(isIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        }
        else {
            audioOptions.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    private boolean isIn(MouseEvent e, PauseButtons b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}

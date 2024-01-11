package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.example.Game;

import ui.MenuButtons;
import utilizations.LoadSave;

public class Menu extends State implements Statemethods{

    private MenuButtons[] buttons = new MenuButtons[3];
    private BufferedImage background;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        background = LoadSave.GetSpriteAtlas(LoadSave.MENU);
        menuWidth = (int)(background.getWidth() * Game.SCALE);
        menuHeight = (int)(background.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int)(45 * game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButtons(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButtons(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButtons(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for(MenuButtons mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(background, menuX, menuY, menuWidth, menuHeight, null);

        for(MenuButtons mb : buttons) {
            mb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButtons mb : buttons) {
            if(isIn(e, mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButtons mb : buttons) {
            if(isIn(e, mb)){
                if(mb.isMousePressed()) {
                    mb.applyGameState();
                }

                break;
            }
        }

        resetButtons();
    }

    private void resetButtons() {
        for(MenuButtons mb : buttons) {
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButtons mb : buttons) {
            mb.setMouseOver(false);
        }

        for(MenuButtons mb : buttons) {
            if(isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() ==  KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    } 
}

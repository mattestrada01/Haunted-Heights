package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import static utilizations.constants.UI.URMButtons.URM_SIZE;
import com.example.Game;
import gamestates.Gamestate;
import gamestates.Playing;
import utilizations.LoadSave;

public class GameOverOverlay {

    private Playing playing;
    private BufferedImage image;
    private int imageX, imageY, imageWidth, imageHeight;
    private Urm menu, play;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImage();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (330 * Game.SCALE);
		int playX = (int) (440 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		play = new Urm(playX, y, URM_SIZE, URM_SIZE, 0);
		menu = new Urm(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    public void update() {
        menu.update();
        play.update();
    }

    private void createImage() {
        image = LoadSave.GetSpriteAtlas(LoadSave.DEATH);
        imageX = (Game.GAME_WIDTH / 3 - imageWidth / 2) + (int)(Game.SCALE*20);
		imageY = (int) (100 * Game.SCALE);
        imageWidth = (int) (image.getWidth() * Game.SCALE);
		imageHeight = (int) (image.getHeight() * Game.SCALE);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
		
        menu.draw(g);
        play.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        
    }

    private boolean isIn(Urm b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(play, e))
			play.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGameState(Gamestate.MENU);
			}
		} else if (isIn(play, e))
			if (play.isMousePressed()){
                playing.resetAll();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLevelIndex());
            }

		menu.resetBools();
		play.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(play, e))
			play.setMousePressed(true);
	}
}

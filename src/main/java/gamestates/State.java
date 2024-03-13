package gamestates;

import java.awt.event.MouseEvent;
import com.example.Game;
import audio.AudioPlayer;
import ui.MenuButtons;

public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isIn(MouseEvent e, MenuButtons mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }

    public void setGameState(Gamestate state) {
        switch (state) {
		    case MENU:
                game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
                break;
		    case PLAYING:
                game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
                break;
            default:
                break;
		}

		Gamestate.state = state;
    }
}

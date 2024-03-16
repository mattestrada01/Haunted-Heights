package objects;

import static utilizations.constants.ObjectConstants.*;

import com.example.Game;

public class GameContainer extends GameObject{

    public GameContainer(int x, int y, int objectType) {
        super(x, y, objectType);
        createHitbox();
    }

    private void createHitbox() {
        if(objectType == BOX) {
            initHitbox(25, 18);
            xDrawoffset = (int)(7*Game.SCALE);
            yDrawoffset = (int)(12*Game.SCALE);
        }else {
            initHitbox(23, 25);
            xDrawoffset = (int)(8*Game.SCALE);
            yDrawoffset = (int)(5*Game.SCALE);
        }

        hitbox.y += yDrawoffset + (int) (Game.SCALE * 2);
		hitbox.x += xDrawoffset / 2;
    }

    public void update() {
        if(doAnimation) {
            updateAnimationTick();
        }
    }
    
}

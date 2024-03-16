package objects;

import com.example.Game;

public class Spike extends GameObject{

    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);
        
        initHitbox(32, 16);
        xDrawoffset = 0;
        yDrawoffset = (int)(Game.SCALE * 16);
        hitbox.y += yDrawoffset;
    }
    
}

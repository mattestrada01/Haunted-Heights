package objects;

import static utilizations.constants.ANI_SPEED;
import static utilizations.constants.ObjectConstants.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import com.example.Game;


public class GameObject {
    protected int x, y, objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int animationTick;
    protected int animationIndex;
    protected int xDrawoffset, yDrawoffset;

    public GameObject(int x, int y, int objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    protected void updateAnimationTick() {
        animationTick++;
        if(animationTick >= ANI_SPEED) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= GetSpriteID(objectType)) {
                animationIndex = 0;
                if(objectType == BARREL || objectType == BOX) {
                    doAnimation = false;
                    active = false;
                }      
            }
        }
    }

    public void reset() {
        animationIndex = 0;
        animationTick = 0;
        active = true;

        if(objectType == BARREL || objectType == BOX)
            doAnimation = false;
        else
            doAnimation = true;
    }

    protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

    public void drawHitbox(Graphics g, int xLevelOffset) {
        // for debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int)hitbox.x - xLevelOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    public int getObjectType() {
        return objectType;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getxDrawoffset() {
        return xDrawoffset;
    }

    public int getyDrawoffset() {
        return yDrawoffset;
    }   
    
    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }
}

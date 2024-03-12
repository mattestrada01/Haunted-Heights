package entities;

import static utilizations.constants.EnemyConstants.*;
import static utilizations.helper.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilizations.constants.Directions.*;



import com.example.Game;

public class Enemy1 extends Enemy{

    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    
    public Enemy1(float x, float y) {
        super(x, y, ENEMY1_WIDTH, ENEMY1_HEIGHT, ENEMY1);
        initHitbox(x, y, (int)(20*Game.SCALE), (int)(30*Game.SCALE));  
        initAttackbox();
    }

    private void initAttackbox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(Game.SCALE*30), (int)(25*Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE*12);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {

        if(walkDirection == LEFT) {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        }else{
            attackBox.x = hitbox.x + (attackBoxOffsetX/4);
        }
        
        attackBox.y = hitbox.y;
    }

    public void drawAttackbox(Graphics g, int xLevelOffset) {
        g.setColor(Color.cyan);
        g.drawRect((int)(attackBox.x - xLevelOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch (state) {
            case IDLE:
                stateChange(RUNNING);
                break;
            case RUNNING:
                if(canSeePlayer(lvlData, player))
                    turnTowardsPlayer(player);
                if(playerAttackable(player))
                    stateChange(ATTACK);
                updateMoving(lvlData);
                break;
            case ATTACK:
                if(animationIndex == 0)
                    attackChecked = false;
                if(animationIndex == 3 && !attackChecked)
                    checkEnemyHit(attackBox, player);
                break;
            case HIT:
                break;
            }
        }

    }

    

    public int flipX() {
        if(walkDirection == RIGHT) {
            return 0;
        }
        else{
            return width - (int)(Game.SCALE*8);
        }
    }

    public int flipW() {
        if(walkDirection == RIGHT) {
            return 1;
        }
        else {
            return -1;
        }
    }
}

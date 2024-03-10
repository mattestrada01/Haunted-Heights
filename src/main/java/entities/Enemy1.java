package entities;

import static utilizations.constants.EnemyConstants.*;
import static utilizations.helper.*;
import static utilizations.constants.Directions.*;



import com.example.Game;

public class Enemy1 extends Enemy{
    
    public Enemy1(float x, float y) {
        super(x, y, ENEMY1_WIDTH, ENEMY1_HEIGHT, ENEMY1);
        initHitbox(x, y, (int)(20*Game.SCALE), (int)(30*Game.SCALE));  
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData, Player player) {
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
            }
        }

    }
}

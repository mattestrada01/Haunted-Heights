package entities;

import static utilizations.constants.EnemyConstants.*;
import static utilizations.helper.*;
import static utilizations.constants.Directions.*;

import com.example.Game;

public abstract class Enemy extends Entity {

        private int animationIndex, state, type;
        private int animationTick, animationSpeed = 25;
        private boolean inAir;
        private boolean firstUpdate = true;
        private float fallSpeed;
        private float gravity = 0.04f * Game.SCALE;
        private float walkSpeed = 1.0f * Game.SCALE;
        private int walkDirection = LEFT;


        public Enemy(float x, float y, int width, int height, int type) {
            super(x, y, width, height);
            this.type = type;
            initHitbox(x, y, width, height);
            
        }


        private void updateAnimationTick() {
            animationTick++;
            if(animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;

                if (animationIndex >= GetSpriteID(type, state)) {
                    animationIndex = 0;
                }
            }
        }

        public void update(int[][] lvlData) {
            updateMove(lvlData);
            updateAnimationTick();
        }

        public int getAnimationIndex() {
            return animationIndex;
        }

        public int getState() {
            return state;
        }

        private void updateMove(int[][] lvlData) {
            if (firstUpdate) {
                if (!IsEntityOnFloor(hitbox, lvlData))
                    inAir = true;
                firstUpdate = false;
            }
    
            if (inAir) {
                if (CanMoveHereEnemy(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
                    hitbox.y += fallSpeed;
                    fallSpeed += gravity;
                } else {
                    inAir = false;
                    hitbox.y = GetEnemyYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
                }
            } else {
                switch (state) {
                case IDLE:
                    state = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;
    
                    if (walkDirection == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;
    
                    if (CanMoveHereEnemy(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                        if (IsFloor(hitbox, xSpeed, lvlData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
    
                    changeWalkDirection();
    
                    break;
                }
            }
    
        }
    


        private void changeWalkDirection() {
            if(walkDirection == LEFT)
                walkDirection = RIGHT;
            else
                walkDirection = LEFT;
        }
}

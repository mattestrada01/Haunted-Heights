package entities;

import static utilizations.constants.EnemyConstants.*;
import static utilizations.helper.*;
import static utilizations.constants.Directions.*;

import com.example.Game;

public abstract class Enemy extends Entity {

        protected int tileY;
        protected float attackRange = Game.TILES_SIZE;
        protected int animationIndex, state, type;
        protected int animationTick, animationSpeed = 25;
        protected boolean inAir;
        protected boolean firstUpdate = true;
        protected float fallSpeed;
        protected float gravity = 0.04f * Game.SCALE;
        protected float walkSpeed = 0.3f * Game.SCALE;
        protected int walkDirection = LEFT;


        public Enemy(float x, float y, int width, int height, int type) {
            super(x, y, width, height);
            this.type = type;
            initHitbox(x, y, width, height); 
        }


        protected void updateAnimationTick() {
            animationTick++;
            if(animationTick >= animationSpeed) {
                animationTick = 0;
                animationIndex++;

                if (animationIndex >= GetSpriteID(type, state)) {
                    animationIndex = 0;

                    if(state == ATTACK) {
                        state = IDLE;
                    }
                }
            }
        }

        protected void firstUpdateCheck(int[][] lvlData) {
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;
            firstUpdate = false;
        }

        protected void updateInAir(int[][] lvlData) {
                if (CanMoveHereEnemy(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
                    hitbox.y += fallSpeed;
                    fallSpeed += gravity;
                } else {
                    inAir = false;
                    hitbox.y = GetEnemyYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
                    tileY = (int)(hitbox.y / Game.TILES_SIZE);
                }     
        }

        protected boolean canSeePlayer(int[][] lvlData, Player player) {
            int playerTileY = (int)(player.getHitbox().y / Game.TILES_SIZE);

            if(Math.abs(playerTileY - tileY) <= 2) 
                if(playerIsInRange(player)) {
                    if(isSightClear(lvlData, hitbox, player.hitbox, tileY))
                        return true;
                }
        
            return false;
        }

        protected void turnTowardsPlayer(Player player) {
            if(player.hitbox.x > hitbox.x) 
                walkDirection = RIGHT; 
            else 
                walkDirection = LEFT;    
        }


        private boolean playerIsInRange(Player player) {
            int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
            return absValue <= attackRange*5;
        }

        protected void stateChange(int state) {
            this.state = state;
            animationTick = 0;
            animationIndex = 0;
        }

        protected boolean playerAttackable(Player player) {
            int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
            return absValue <= attackRange;
        }

        protected void updateMoving(int[][] lvlData) {
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
        }


        public int getAnimationIndex() {
            return animationIndex;
        }

        public int getState() {
            return state;
        }

        protected void changeWalkDirection() {
            if(walkDirection == LEFT)
                walkDirection = RIGHT;
            else
                walkDirection = LEFT;
        }
}

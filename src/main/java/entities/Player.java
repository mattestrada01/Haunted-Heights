package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.example.Game;

import utilizations.LoadSave;
import static utilizations.constants.PlayerConstants.*;
import static utilizations.helper.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 40;
    private int playerAction = IDLE;
    private boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false, attacking2 = false, inAir = false, dead = false;
    private float playerSpeed = Game.SCALE;
    private int[][] lvlData;
    private float xOffset = 20*Game.SCALE;
    private float yOffset = 29*Game.SCALE;

    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.3f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 23*Game.SCALE, 34*Game.SCALE);
    }

    public void update() {
        updatePosition();
        updateAnimation();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int)(hitbox.x - xOffset), (int)(hitbox.y - yOffset), width, height, null);
        //drawHitbox(g);
    }

    private void updateAnimation() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if(animationIndex >= GetSpriteID(playerAction)) {
                animationIndex = 0;
                attacking = false;
                attacking2 = false;
                dead = false;
            }
        }
    }

    private void setAnimation() {

        int startAnimation = playerAction;

        if (moving) {
            playerAction = RUNNING;
            this.animationSpeed = 20;
        }
        else {
            playerAction = IDLE;
            this.animationSpeed = 40;

        }

        if (attacking) {
            playerAction = ATTACK_1;
            this.animationSpeed = 15;
        }

        if (attacking2) {
            playerAction = ATTACK_2;
            this.animationSpeed = 10;
        }

        if (dead) {
            playerAction = DEAD;
            this.animationSpeed = 30;
        }

        if (inAir) { 
                playerAction = JUMPING;
                this.animationSpeed = 15;
        }

        if (startAnimation != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePosition() {
		moving = false;

		if (jump)
			jump();
		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				    hitbox.y += airSpeed;
				    airSpeed += gravity;
				    updateXPos(xSpeed);
                
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}

    private void jump() {
        if (inAir) {
            return;
        }

        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}

    public void setAnimationSpeed(int speed) {
        this.animationSpeed = speed;
    }

    private void loadAnimations() {        
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[8][10];
        for (int j = 0; j < animations.length; j++){
             for (int i = 0; i < animations[j].length; i++){
                animations[j][i] = image.getSubimage(i*128, j*128, 128, 128);
            }
        }   
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData)){
            inAir = true;
        }
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirectionBooleans() {
        up = false;
        down = false;
        left = false;
        right = false;
    } 

    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public void setAttack2(boolean attacking2) {
        this.attacking2 = attacking2;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }    
}

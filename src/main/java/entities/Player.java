package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.example.Game;

import audio.AudioPlayer;
import gamestates.Playing;
import utilizations.LoadSave;
import static utilizations.constants.PlayerConstants.*;
import static utilizations.helper.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 40;
    private boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false, attacking2 = false, inAir = false, dead = false;
    private float playerSpeed = Game.SCALE;
    private int[][] lvlData;
    private float xOffset = 20*Game.SCALE;
    private float yOffset = 29*Game.SCALE;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;

    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.3f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    // StatusBarUI
	private BufferedImage statusBarImg;
    private Rectangle2D.Float attackBox;


	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);

    private int powerBarWidth = (int) (104 * Game.SCALE);
	private int powerBarHeight = (int) (2 * Game.SCALE);
	private int powerBarXStart = (int) (44 * Game.SCALE);
	private int powerBarYStart = (int) (34 * Game.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;

	private int maxHealth;
	private int currentHealth;
	private int healthWidth = healthBarWidth;
    private Playing playing;
    private boolean dashAttackActive;
    private int dashAttackTick;
    private int dashGrowSpeed = 15;
    private int dashGrowTick;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = 75;
        loadAnimations();
        initHitbox(x, y, (int)(23*Game.SCALE), (int)(34*Game.SCALE));
        initAttackbox();
    }

    public void setSpawn(Point spawnPoint) {
        this.x = spawnPoint.x;
        this.y = spawnPoint.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackbox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(20*Game.SCALE), (int)(20*Game.SCALE));
    }

    public void update() {
        updateHealth();
        updatePower();
        if(currentHealth <= 0) {
            if(state != DEAD) {
                state = DEAD;
                animationTick = 0;
                animationIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            } else if(animationIndex == GetSpriteID(DEAD) - 1 && animationTick >= animationSpeed - 1) {
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            }else {
                updateAnimation();
            }

            return;
        }

        updateAttackbox();
        updatePosition();

        if(moving) {
            checkPotionTouched();
            checkTouchedSpikes();

            if(dashAttackActive) {
                dashAttackTick++;
                if(dashAttackTick >= 35) {
                    dashAttackTick = 0;
                    dashAttackActive = false;
                }
            }
        }
        if(attacking || dashAttackActive)
            checkAttack();
        updateAnimation();
        setAnimation();
    }

    private void checkTouchedSpikes() {
        playing.checkTouchedSpikes(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        // this aniIndex can be 2 if you updat the animation start index below
        // checking at animation index 1
        if(attackChecked || (animationIndex != 2 && animationIndex != 4)) {
            return;
        }

        attackChecked = true;

        if(dashAttackActive) {
            attackChecked = false;
        }

        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void updateAttackbox() {
        if(right || (dashAttackActive && flipW == 1)) {
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE*5);
        }else if (left || (dashAttackActive && flipW == -1)) {
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE*2);
        }

        attackBox.y = hitbox.y + (Game.SCALE*10);
    }

    private void updateHealth() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    private void updatePower() {
        powerWidth = (int)((powerValue / (float) powerMaxValue) * powerBarWidth);
        dashGrowTick++;

        if(dashGrowTick >= dashGrowSpeed) {
            dashGrowTick = 0;
            changePower(2);
        }
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int i) {
        powerValue += i;
        if(powerValue >= powerMaxValue) {
            powerValue = powerMaxValue;
        }
        else if(powerValue <= 0) {
            powerValue = 0;
        }
    }

    public void changeHealth(int healthValue) {
        currentHealth += healthValue;
        if(currentHealth <=0) {
            currentHealth = 0;
            //gameOver();
        }else if (currentHealth >= maxHealth) 
            currentHealth = maxHealth;
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[state][animationIndex], (int)(hitbox.x - xOffset) - lvlOffset + flipX, (int)(hitbox.y - yOffset), width*flipW, height, null);
        //drawHitbox(g, lvlOffset);
        //drawAttackbox(g, lvlOffset);
        drawUI(g);
    }

    private void drawAttackbox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.cyan);
        g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        // health bar
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

        // power bar
        g.setColor(Color.cyan);
        g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
    }

    private void updateAnimation() {
        animationTick++;

        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;

            if(animationIndex >= GetSpriteID(state)) {
                animationIndex = 0;
                attacking = false;;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {

        int startAnimation = state;

        if (moving) {
            state = RUNNING;
            this.animationSpeed = 20;
        }
        else {
            state = IDLE;
            this.animationSpeed = 40;
        }

        if (inAir) { 
            state = JUMPING;
            this.animationSpeed = 15;
        }

        if(dashAttackActive) {
            state = ATTACK_1;
            this.animationSpeed = 10;
            animationIndex = 4;
            animationTick = 0;
            return;
        }

        if (attacking) {
            state = ATTACK_1;
            this.animationSpeed = 10;

            // causes jumping bug when clicking attack
            if(startAnimation != ATTACK_1) {
                animationIndex = 2;
                animationTick = 0;
                return;
            }
        }

        // causes jumping bug when clicking attack
        //if (attacking2) {
         //   state = ATTACK_2;
         //   this.animationSpeed = 10;

            // causes jumping bug when clicking attack
            //if(startAnimation != ATTACK_2) {
            //    animationIndex = 2;
            //    animationTick = 0;
            //    return;
            //}
        //}

        if (startAnimation != state) {
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
		
        if(!inAir)
            if(!dashAttackActive)
                if((!left && !right) || (right && left))
                    return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
		if (right) {
			xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if(dashAttackActive) {
            if(!left && !right){
                if(flipW == -1) {
                    xSpeed = -playerSpeed;
                }
                else {
                    xSpeed = playerSpeed;
                }
            }

            xSpeed *= 3;
        }

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir && !dashAttackActive) {
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
        if (inAir || powerValue < 40) {
            return;
        }

        changePower(-40);
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
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
            if(dashAttackActive) {
                dashAttackActive = false;
                dashAttackTick = 0;
            }
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
        
        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH);
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

    public void resetAll() {
        resetDirectionBooleans();
        inAir = false;
        attacking = false;
        attacking2 = false;
        dead = false;
        moving = false;
        state = IDLE;
        currentHealth = 75;
        powerValue = powerMaxValue;

        hitbox.x = x;
        hitbox.y = y;

        if(!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    } 
    
    public void powerAttack() {
        if(dashAttackActive) {
            return;  
        }
        if(powerValue >= 70) {
            dashAttackActive = true;
            changePower(-70);
        }
    }
}

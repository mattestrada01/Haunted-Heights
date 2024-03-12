package entities;

import static utilizations.constants.EnemyConstants.*;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.*;
import levels.Level;
import utilizations.LoadSave;

public class EnemyManager {

    private ArrayList<Enemy1> enemies1 = new ArrayList<>();
    private Playing playing;
    private BufferedImage[][] enemy1arr;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        enemies1 = level.getEnemy1s();
    }

    private void loadEnemyImages() {
        enemy1arr = new BufferedImage[5][7];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY1_SPRITE);

        for (int j = 0; j < enemy1arr.length; j++) {
            for(int i = 0; i < enemy1arr[j].length; i++){
                enemy1arr[j][i] = temp.getSubimage(i*ENEMY1_WIDTH_DEFAULT, j*ENEMY1_HEIGHT_DEFAULT, ENEMY1_WIDTH_DEFAULT, ENEMY1_HEIGHT_DEFAULT);
            }
        }
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
		for (Enemy1 c : enemies1)
			if (c.isActive()) {
				c.update(lvlData, player);
				isAnyActive = true;
			}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawEnemy1(g, xLevelOffset);
    }

    private void drawEnemy1(Graphics g, int xLevelOffset) {
        for (Enemy1 c : enemies1) 
            if(c.isActive())
            {
                g.drawImage(enemy1arr[c.getState()][c.getAnimationIndex()], (int)c.getHitbox().x - xLevelOffset - ENEMY1_DRAWOFFSET_X + c.flipX(), 
                                (int)c.getHitbox().y - ENEMY1_DRAWOFFSET_Y, ENEMY1_WIDTH * c.flipW(), 
                                ENEMY1_HEIGHT, null);
                c.drawHitbox(g, xLevelOffset);

                c.drawAttackbox(g, xLevelOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Enemy1 c : enemies1) {
            if(c.isActive())
                if(attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
        }

    }

    public void resetAllEnemies() {
        for(Enemy1 c : enemies1) {
            c.resetEnemy();
        }
    }
}

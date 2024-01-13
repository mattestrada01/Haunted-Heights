package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.example.Game;

import utilizations.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level level1;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        level1 = new Level(LoadSave.GetLevelData());
    }


    private void importOutsideSprites() {
        BufferedImage image = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        // 12x4 pixels
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j*12 + i;
                levelSprite[index] = image.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }


    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < level1.getLevelData()[0].length; i++) {
                int index = level1.getSpriteIndex(i, j);
                 g.drawImage(levelSprite[index], Game.TILES_SIZE*i-lvlOffset, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return level1;
    }
}

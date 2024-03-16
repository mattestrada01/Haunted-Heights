package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.example.Game;

import gamestates.Gamestate;
import utilizations.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }


    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();

        for(BufferedImage image : allLevels) {
            levels.add(new Level(image));
        }
    }


    public void loadNextLevel() {
        levelIndex++;

        // could add an overlay here
        if(levelIndex >= levels.size()) {
            levelIndex = 0;
            System.out.println("no more levels");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(levelIndex);

        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffset(newLevel.getLevelOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
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
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                 g.drawImage(levelSprite[index], Game.TILES_SIZE*i-lvlOffset, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getAmountofLevels() {
        return levels.size();
    }

    public int getLevelIndex() {
        return levelIndex;
    }
}

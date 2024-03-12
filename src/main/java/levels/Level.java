package levels;

import static utilizations.helper.GetLevelData;
import static utilizations.helper.GetEnemies1;
import static utilizations.helper.GetPlayerSpawn;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import com.example.Game;
import entities.Enemy1;

public class Level {

    private BufferedImage image;
    private ArrayList<Enemy1> enemy1s;
    private int[][] lvlData;
    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;
        createLevelData();
        createEnemies();
        calculateLevelOffset();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(image);
    }

    private void calculateLevelOffset() {
        levelTilesWide = image.getWidth();
        maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        enemy1s = GetEnemies1(image);
    }

    private void createLevelData() {
        lvlData = GetLevelData(image);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLevelOffset() {
        return maxLevelOffsetX;
    }

    public ArrayList<Enemy1> getEnemy1s() {
        return enemy1s;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}

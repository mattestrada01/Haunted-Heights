package levels;

import static utilizations.helper.GetLevelData;
import static utilizations.helper.GetEnemies1;
import static utilizations.helper.GetPlayerSpawn;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import com.example.Game;
import entities.Enemy1;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utilizations.helper;

public class Level {

    private BufferedImage image;
    private ArrayList<Enemy1> enemy1s;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;    
    private ArrayList<Spike> spikes;
    
    private int[][] lvlData;
    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage image) {
        this.image = image;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        calculateLevelOffset();
        calcPlayerSpawn();
    }

    private void createSpikes() {
        spikes = helper.GetSpikes(image);
    }

    private void createContainers() {
        containers = helper.GetContainers(image);
    }

    private void createPotions() {
        potions = helper.GetPotions(image);
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

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}

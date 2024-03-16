package utilizations;

import static utilizations.constants.EnemyConstants.ENEMY1;
import static utilizations.constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.example.Game;

import entities.Enemy1;
import objects.GameContainer;
import objects.Potion;

public class helper {


    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
			// to check midwall on right side
				if (!IsSolid(x + width, y + height - (32*Game.SCALE), lvlData))
					if (!IsSolid(x + width, y, lvlData))
						if (!IsSolid(x, y + height, lvlData))
						// to check the mid wall on left side
							if (!IsSolid(x, y + height - (32*Game.SCALE), lvlData))
								return true;
		return false;
	}

	public static boolean CanMoveHereEnemy(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {

		int maxWidth = lvlData[0].length * Game.TILES_SIZE;

		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[ yTile][ xTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);

		if(xSpeed > 0) {
			// to the right
			int tileXpos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXpos + xOffset - 1;
		}
		else {
			// to the left
			return currentTile * Game.TILES_SIZE;
		}
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
			// you are falling
			int tileYpos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE);
			return tileYpos + yOffset - 2.5f*Game.SCALE;
		}
		else {
			// you are jumping
			return currentTile * Game.TILES_SIZE;
		}
	}

	public static float GetEnemyYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else
			// Jumping
			return currentTile * Game.TILES_SIZE;
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;
	}

	public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for(int i = 0; i < xEnd - xStart; i++) {
			if(IsTileSolid(xStart + i, y, lvlData))
				return false;
			if(!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}

		return true;
	}

	public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXtile = (int)(firstHitbox.x / Game.TILES_SIZE);
		int secondXtile = (int)(secondHitbox.x / Game.TILES_SIZE);

		if (firstXtile > secondXtile) 
			return isAllTilesWalkable(secondXtile, firstXtile, yTile, lvlData);
		else 
			return isAllTilesWalkable(firstXtile, secondXtile, yTile, lvlData);
	}

	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}

	public static int[][] GetLevelData(BufferedImage image) {
        int[][] lvlData = new int[image.getHeight()][image.getWidth()];

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48){
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }

        return lvlData;
    }

	public static ArrayList<Enemy1> GetEnemies1(BufferedImage image) {
        ArrayList<Enemy1> list = new ArrayList<>();
		for (int j = 0; j < image.getHeight(); j++)
			for (int i = 0; i < image.getWidth(); i++) {
				Color color = new Color(image.getRGB(i, j));
				int value = color.getGreen();
				if (value == ENEMY1)
					list.add(new Enemy1(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;
    }

	public static Point GetPlayerSpawn(BufferedImage image) {
			for (int j = 0; j < image.getHeight(); j++)
				for (int i = 0; i < image.getWidth(); i++) {
					Color color = new Color(image.getRGB(i, j));
					int value = color.getGreen();
					if (value == 100)
						return new Point(i*Game.TILES_SIZE, j*Game.TILES_SIZE);
				}

			return new Point(2 * Game.TILES_SIZE, 4 * Game.TILES_SIZE);
	}

	public static ArrayList<Potion> GetPotions(BufferedImage image) {
        ArrayList<Potion> list = new ArrayList<>();
		for (int j = 0; j < image.getHeight(); j++)
			for (int i = 0; i < image.getWidth(); i++) {
				Color color = new Color(image.getRGB(i, j));
				int value = color.getBlue();
				if (value == RED_POTION || value == BLUE_POTION)
					list.add(new Potion(i*Game.TILES_SIZE, j*Game.TILES_SIZE, value));	
			}
		return list;
    }

	public static ArrayList<GameContainer> GetContainers(BufferedImage image) {
        ArrayList<GameContainer> list = new ArrayList<>();
		for (int j = 0; j < image.getHeight(); j++)
			for (int i = 0; i < image.getWidth(); i++) {
				Color color = new Color(image.getRGB(i, j));
				int value = color.getBlue();
				if (value == BOX || value == BARREL)
					list.add(new GameContainer(i*Game.TILES_SIZE, j*Game.TILES_SIZE, value));	
			}
		return list;
    }


}

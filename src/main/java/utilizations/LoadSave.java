package utilizations;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.example.Game;
import static utilizations.constants.EnemyConstants.ENEMY1;
import entities.Enemy1;

public class LoadSave {

    public static final String PLAYER_ATLAS = "src/main/resources/enchant_sprite1.png";
    public static final String LEVEL_ATLAS = "src/main/resources/outsideSprites1.png";
    //public static final String LEVEL_ONE_DATA = "src/main/resources/level_one_data.png";
    public static final String LEVEL_ONE_DATA = "src/main/resources/level_one_data_long.png";
    public static final String MENU_BUTTONS = "src/main/resources/menu_buttons3.png";
    public static final String MENU = "src/main/resources/menu3.png";
    public static final String PAUSE = "src/main/resources/pause3.png";
    public static final String SOUND_BUTTON = "src/main/resources/sound_button.png";
    public static final String URM_BUTTONS = "src/main/resources/urm_buttons.png";
    public static final String VOLUME_BUTTONS = "src/main/resources/volume_buttons.png";
    public static final String MENU_IMAGE = "src/main/resources/scaryBackground.png";
    public static final String PLAYING_BACKGROUND = "src/main/resources/background1.0.png";
    public static final String CLOUD_BIG = "src/main/resources/clouds4.png";
    public static final String CLOUD_BIG2 = "src/main/resources/clouds3.png";
    public static final String CLOUD_SMALL = "src/main/resources/small.png";
    public static final String HANDS = "src/main/resources/hands.png";
    public static final String BOTTOM = "src/main/resources/bottomGround.png";
    public static final String ENEMY1_SPRITE = "src/main/resources/enemy1.png";
    public static final String HEALTH = "src/main/resources/health_power_bar.png";



    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage image = null;
        File is = new File(fileName);
        //InputStream is = LoadSave.class.getResourceAsStream("src/main/resources/enchant_sprite1.png");
        try {
            image = ImageIO.read(is);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
         return image;
        }


    public static ArrayList<Enemy1> GetEnemies1() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Enemy1> list = new ArrayList<>();

        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == ENEMY1){
                    list.add(new Enemy1(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
                }
                
            }
        }

        return list;
    }

    public static int[][] GetLevelData() {
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
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
}


package utilizations;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.example.Game;
import static utilizations.constants.EnemyConstants.ENEMY1;
import entities.Enemy1;

public class LoadSave {

    public static final String PLAYER_ATLAS = "src/main/resources/enchant_sprite1.png";
    public static final String LEVEL_ATLAS = "src/main/resources/outsideSprites1.png";
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
    public static final String COMPLETED = "src/main/resources/completed.png";


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

    public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvlz");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
	}

}
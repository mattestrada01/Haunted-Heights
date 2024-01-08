package utilizations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "src/main/resources/enchant_sprite1.png";

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
}


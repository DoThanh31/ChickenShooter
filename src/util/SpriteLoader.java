package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpriteLoader {

    private static SpriteLoader instance;
    private Map<String, BufferedImage> cache = new HashMap<>();

    private SpriteLoader() {}

    public static SpriteLoader getInstance() {
        if (instance == null) instance = new SpriteLoader();
        return instance;
    }

    public BufferedImage load(String path) {
        try {
            if (cache.containsKey(path)) {
                return cache.get(path);
            }

            File file = new File(path);

            System.out.println("Loading: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("âŒ File khÃ´ng tá»“n táº¡i!");
                return null;
            }

            BufferedImage img = ImageIO.read(file);
            cache.put(path, img);

            return img;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

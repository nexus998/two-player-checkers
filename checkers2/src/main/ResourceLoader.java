package main;

import entities.FigureColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class ResourceLoader {

    public static final Dictionary<FigureColor, String> spritePaths = new Hashtable<FigureColor, String>();
    public static final String BLACK_MAN_PATH = "src/assets/sprites/man_black.png";
    public static final String WHITE_MAN_PATH = "src/assets/sprites/man_white.png";
    public static final String WHITE_KING_PATH = "src/assets/sprites/king_white.png";
    public static final String BLACK_KING_PATH = "src/assets/sprites/king_black.png";
    public static final String INVISIBLE_PATH = "src/assets/sprites/man_invisible.png";

    public ResourceLoader() {
        spritePaths.put(FigureColor.Black, BLACK_MAN_PATH);
        spritePaths.put(FigureColor.White, WHITE_MAN_PATH);
    }

    public Image loadSprite(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}

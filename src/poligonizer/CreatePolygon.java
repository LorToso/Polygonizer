package poligonizer;

import java.awt.image.BufferedImage;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class CreatePolygon {
    public static BuildablePolygon of(BufferedImage image)
    {
        return new BuildablePolygon(image);
    }
}

package poligonizer;

import java.awt.image.BufferedImage;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class PolygonBuilder {
    public static BuildablePolygon CreatePolygonFor(BufferedImage image)
    {
        return new BuildablePolygon(image);
    }
}

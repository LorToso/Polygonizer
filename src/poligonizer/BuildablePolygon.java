package poligonizer;

import filters.Channel;
import filters.ChannelFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class BuildablePolygon {
    private static final int DEFAULT_APPROXIMATION_POINT_COUNT = 5;

    private final BufferedImage image;
    private int pointCount = DEFAULT_APPROXIMATION_POINT_COUNT;
    private final ChannelFilter filter = new ChannelFilter();
    private boolean drawPoints = false;

    public BuildablePolygon(BufferedImage image) {
        this.image = image;
    }
    public Polygon build()
    {
        PolygonCreator creator  = new PolygonCreator(image, pointCount, filter);
        Polygon polygon = creator.createPolygon();

        if(drawPoints)
        {
            creator.drawPoints();
        }

        return polygon;
    }
    public BuildablePolygon filterWhere(Channel channel)
    {
        filter.addChannel(channel);
        return this;
    }
    public BuildablePolygon drawPoints()
    {
        drawPoints = true;
        return this;
    }
    public BuildablePolygon withPointCountOf(int pointCount)
    {
        this.pointCount = pointCount;
        return this;
    }
}

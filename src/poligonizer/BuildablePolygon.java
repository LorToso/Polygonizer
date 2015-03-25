package poligonizer;

import filters.FilterCollector;
import filters.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This was created by lorenzo toso on 25.03.15.
 */
public class BuildablePolygon {
    private static final int DEFAULT_APPROXIMATION_POINT_COUNT = 5;

    private BufferedImage image;
    private int pointCount = DEFAULT_APPROXIMATION_POINT_COUNT;
    private final FilterCollector filter = new FilterCollector();
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
    public BuildablePolygon filterWhere(Filter channel)
    {
        filter.addFilter(channel);
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
    public BuildablePolygon changeImageTo(BufferedImage image)
    {
        this.image = image;
        return this;
    }
}

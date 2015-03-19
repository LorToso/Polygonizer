import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by lorenzo toso on 19.03.15.
 */
public class Polygonizer {

    private static final int DEFAULT_POINT_COUNT = 5;
    public static Polygon CreatePolygonFromImage(BufferedImage inputImage)
    {
        return CreatePolygonFromImage(inputImage, DEFAULT_POINT_COUNT);
    }

    public static Polygon CreatePolygonFromImage(BufferedImage inputImage, int pointCount)
    {
        double[] vectorAngles = generateVectorAngles(pointCount);
        Point[] farestPointPositions = generateFarestPointPositions(vectorAngles);
        return null;
    }

    private static Point[] generateFarestPointPositions(double[] vectorAngles) {
        return null;
    }

    private static double[] generateVectorAngles(int pointCount) {
        double[] vectorAngles = new double[pointCount];

        double degreePerPoint = 360.0/pointCount;
        for(int i=0; i < pointCount; i++)
        {
            vectorAngles[i] = i*degreePerPoint;
        }
        return vectorAngles;
    }
}

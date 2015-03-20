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
        Point[] farestPointPositions = generateFarestPointPositions(vectorAngles, new Point(inputImage.getMinX(), inputImage.getMinY()));


        Polygon polygon = new Polygon();

        for (Point farestPointPosition : farestPointPositions) {
            MovePointTowardsMid(farestPointPosition, inputImage);
            polygon.addPoint(farestPointPosition.x, farestPointPosition.y);
        }
        return polygon;
    }

    private static void MovePointTowardsMid(Point farestPointPosition, BufferedImage inputImage) {
        Point imageCenter = new Point(inputImage.getWidth()/2, inputImage.getHeight()/2);

        while(isTransparentAt(inputImage, farestPointPosition))
        {
            Point referenceX;
            Point referenceY;

            if(farestPointPosition.x > imageCenter.x)
                referenceX = new Point(farestPointPosition.x-1, farestPointPosition.y);
            else
                referenceX = new Point(farestPointPosition.x+1, farestPointPosition.y);

            if(farestPointPosition.y > imageCenter.y)
                referenceY = new Point(farestPointPosition.x, farestPointPosition.y-1);
            else
                referenceY = new Point(farestPointPosition.x, farestPointPosition.y+1);

            farestPointPosition = referenceX.distance(imageCenter) < referenceY.distance(imageCenter) ? referenceX : referenceY;

        }
    }

    private static Point[] generateFarestPointPositions(double[] vectorAngles, Point imageSize) {
        Point[] farestPoints = new Point[vectorAngles.length];
        for(int i=0; i < farestPoints.length; i++)
        {
            double angle = vectorAngles[i];
            farestPoints[i] = new Point();
            farestPoints[i].x = (int) (Math.cos(angle)*imageSize.y/2);
            farestPoints[i].y = (int) (Math.sin(angle)*imageSize.x/2);
        }
        return farestPoints;
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
    private static int getAlphaAt(BufferedImage image, int x, int y)
    {
        return (image.getRGB(x,y) & 0xFF000000) >> 24;
    }
    private static boolean isTransparentAt(BufferedImage image, Point point)
    {
        return getAlphaAt(image, point.x, point.y) > 0;
    }
}

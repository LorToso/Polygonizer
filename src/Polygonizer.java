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
        Point[] farestPointPositions = generateFarestPointPositions(vectorAngles, new Point(inputImage.getWidth(), inputImage.getHeight()));

        Polygon polygon = new Polygon();

        for (Point farestPointPosition : farestPointPositions) {
            System.out.println("Start X: " + farestPointPosition.x + " | Y: " + farestPointPosition.y);

            inputImage.getGraphics().setColor(Color.RED);
            inputImage.getGraphics().drawRect(farestPointPosition.x, farestPointPosition.y, 2,2);

            MovePointTowardsMid(farestPointPosition, inputImage);
            polygon.addPoint(farestPointPosition.x, farestPointPosition.y);
            System.out.println("End X: " + farestPointPosition.x + " | Y: " + farestPointPosition.y);
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

            farestPointPosition.setLocation(referenceX.distance(imageCenter) < referenceY.distance(imageCenter) ? referenceX : referenceY);

        }
    }

    private static Point[] generateFarestPointPositions(double[] vectorAngles, Point imageSize) {

        Point imageCenter = new Point(imageSize.x/2, imageSize.y/2);
        Point[] farestPoints = new Point[vectorAngles.length];


        for(int i=0; i < farestPoints.length; i++)
        {
            double angle = Math.toRadians(vectorAngles[i]);

            int sector = getSpecialSector(angle, imageSize);
            farestPoints[i] = new Point();

            angle -= Math.PI/2*sector;

            switch (sector)
            {
                case 0:
                    farestPoints[i].x = imageCenter.x + (int) (Math.atan(angle)*imageCenter.y);
                    farestPoints[i].y = 0;
                    break;
                case 1:
                    farestPoints[i].x = imageSize.x-1;
                    farestPoints[i].y = imageCenter.y + (int) (Math.atan(angle)*imageCenter.y);
                    break;
                case 2:
                    farestPoints[i].x = imageCenter.x + (int) (Math.atan(angle)*imageCenter.x);
                    farestPoints[i].y = imageSize.y-1;
                    break;
                case 3:
                    farestPoints[i].x = 0;
                    farestPoints[i].y = imageCenter.y + (int) (Math.atan(angle)*imageCenter.y) ;
                    break;
            }

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
        return (image.getRGB(x,y) & 0xFF000000) >> 24 & 0x000000FF;
    }
    private static boolean isTransparentAt(BufferedImage image, Point point)
    {
        System.out.println("Alpha: " + getAlphaAt(image, point.x, point.y));
        return getAlphaAt(image, point.x, point.y) == 0 ;
    }
    private static int getSpecialSector(double angle, Point imageSize)
    {
        double threshAngle = Math.atan((imageSize.y/2.0)/(imageSize.x/2.0));

        if(angle <= threshAngle)
            return 0;
        if(angle <= Math.PI/2 + threshAngle)
            return 1;
        if(angle <= Math.PI + threshAngle)
            return 2;
        if(angle <= 3*Math.PI/2 + threshAngle)
            return 3;
        return 0;
    }
}

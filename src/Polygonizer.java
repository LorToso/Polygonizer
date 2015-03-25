import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by lorenzo toso on 19.03.15
 */
public class Polygonizer {

    private static final int DEFAULT_POINT_COUNT = 5;
    private static ArrayList<Point> pointsToDraw = new ArrayList<>();

    public static Polygon CreatePolygonFromImage(BufferedImage inputImage)
    {
        return CreatePolygonFromImage(inputImage, DEFAULT_POINT_COUNT);
    }

    public static Polygon CreatePolygonFromImage(BufferedImage inputImage, int pointCount)
    {
        Point[] approximationPoints = generateBorderPoints(new Point(inputImage.getWidth(), inputImage.getHeight()), pointCount);

        Polygon polygon = new Polygon();

        for (Point point : approximationPoints) {
            MovePointTowardsMid(point, inputImage);
            polygon.addPoint(point.x, point.y);
        }

        for(Point p : pointsToDraw)
            drawPoint(inputImage,p);

        return polygon;
    }

    private static Point[] generateBorderPoints(Point imageSize, int pointCount) {
        int circumference = 2*imageSize.x+2*imageSize.y;
        double trackPerSegment = (double)(circumference)/pointCount;

        Point[] allPoints = new Point[pointCount];

        for(int i=0; i < pointCount; i++)
        {
            double trackLength = trackPerSegment*i;
            int x;
            int y;

            if(trackLength < imageSize.x)
            {
                x = (int) trackLength;
                y = 0;
            }
            else if(trackLength < imageSize.x+imageSize.y)
            {
                x = imageSize.x-1;
                y = (int) (trackLength - imageSize.x);
            }
            else if(trackLength < 2*imageSize.x+imageSize.y)
            {
                x = (int) (imageSize.x - (trackLength - imageSize.x - imageSize.y) -1);
                y = imageSize.y-1;
            }
            else
            {
                x = 0;
                y = (int) (imageSize.y- (trackLength - imageSize.x*2 - imageSize.y) -1);
            }

            allPoints[i] = new Point(x,y);
        }
        return allPoints;
    }

    private static void MovePointTowardsMid(Point point, BufferedImage inputImage) {
        Point imageCenter = new Point(inputImage.getWidth()/2, inputImage.getHeight()/2);

        while(isTransparentAt(inputImage, point))
        {
            Point xMovement;
            Point yMovement;
            Point diagonalMovement;

            if(point.x > imageCenter.x)
                xMovement = new Point(point.x-1, point.y);
            else
                xMovement = new Point(point.x+1, point.y);

            if(point.y > imageCenter.y)
                yMovement = new Point(point.x, point.y-1);
            else
                yMovement = new Point(point.x, point.y+1);

            diagonalMovement = new Point(xMovement.x, yMovement.y);

            double distanceX = xMovement.distance(imageCenter);
            double distanceY = yMovement.distance(imageCenter);
            double distanceD = diagonalMovement.distance(imageCenter);

            if(distanceX < distanceY)
            {
                if(distanceX < distanceD)
                    point.setLocation(xMovement);
                else
                    point.setLocation(diagonalMovement);
            }
            else
            {
                if(distanceY < distanceD)
                    point.setLocation(yMovement);
                else
                    point.setLocation(diagonalMovement);
            }
            //pointsToDraw.add(new Point(farestPointPosition));
        }
    }

    private static void drawPoint(BufferedImage inputImage, Point point) {
        Graphics g = inputImage.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect(point.x, point.y, 2,2);
    }

    private static int getAlphaAt(BufferedImage image, int x, int y)
    {
        return (image.getRGB(x,y) & 0xFF000000) >> 24 & 0x000000FF;
    }
    private static boolean isTransparentAt(BufferedImage image, Point point)
    {
        return getAlphaAt(image, point.x, point.y) == 0 ;
    }
}

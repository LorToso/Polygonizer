import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by lorenzo toso on 19.03.15.
 */
public class Polygonizer {

    private static final int DEFAULT_POINT_COUNT = 5;
    private static double threshAngle = 0;
    private static ArrayList<Point> pointsToDraw = new ArrayList<>();

    public static Polygon CreatePolygonFromImage(BufferedImage inputImage)
    {
        return CreatePolygonFromImage(inputImage, DEFAULT_POINT_COUNT);
    }

    public static Polygon CreatePolygonFromImage(BufferedImage inputImage, int pointCount)
    {
        threshAngle = 2*Math.atan(((double) inputImage.getHeight()) / inputImage.getWidth());

        //double[] vectorAngles = generateVectorAngles(pointCount);
        //Point[] farestPointPositions = generateFarestPointPositions(vectorAngles, new Point(inputImage.getWidth(), inputImage.getHeight()));
        Point[] farestPointPositions = generateFarestPointPositionsClever(new Point(inputImage.getWidth(), inputImage.getHeight()), pointCount);
        for (int i=0; i<farestPointPositions.length; i++) {
            Point farestPointPosition = farestPointPositions[i];
            //double angle = vectorAngles[i];

            System.out.println("Start X: " + farestPointPosition.x + " | Y: " + farestPointPosition.y);
            //System.out.println("Angle: " + Math.toDegrees(angle));


            MovePointTowardsMid(farestPointPosition, inputImage);

            System.out.println("End X: " + farestPointPosition.x + " | Y: " + farestPointPosition.y);
            System.out.println();
        }

        int[] xPoints = new int[farestPointPositions.length];
        int[] yPoints = new int[farestPointPositions.length];


        for(int i=0; i< farestPointPositions.length; i++)
        {
            xPoints[i] = farestPointPositions[i].x;
            yPoints[i] = farestPointPositions[i].y;
        }

        for(Point p : pointsToDraw)
            drawPoint(inputImage,p);

        return new Polygon(xPoints,yPoints,xPoints.length);
    }

    private static Point[] generateFarestPointPositionsClever(Point imageSize, int pointCount) {
        int umfang = 2*imageSize.x+2*imageSize.y;
        double skips = (double)(umfang)/pointCount;

        Point[] allPoints = new Point[pointCount];

        for(int i=0; i < pointCount; i++)
        {
            double completeSkip = skips*i;
            int x;
            int y;

            if(completeSkip < imageSize.x)
            {
                // sector 1
                x = (int) completeSkip;
                y = 0;
            }
            else if(completeSkip < imageSize.x+imageSize.y)
            {
                x = imageSize.x-1;
                y = (int) (completeSkip - imageSize.x);
            }
            else if(completeSkip < 2*imageSize.x+imageSize.y)
            {
                x = (int) (imageSize.x - (completeSkip - imageSize.x - imageSize.y) -1);
                y = imageSize.y-1;
            }
            else
            {
                x = 0;
                y = (int) (imageSize.y- (completeSkip - imageSize.x*2 - imageSize.y) -1);
            }

            allPoints[i] = new Point(x,y);
        }
        return allPoints;
    }

    private static void MovePointTowardsMid(Point farestPointPosition, BufferedImage inputImage) {
        Point imageCenter = new Point(inputImage.getWidth()/2, inputImage.getHeight()/2);
        ArrayList<Point> p = new ArrayList<>();
        while(isTransparentAt(inputImage, farestPointPosition))
        {
            Point referenceX;
            Point referenceY;
            Point referenceD;

            if(farestPointPosition.x > imageCenter.x)
                referenceX = new Point(farestPointPosition.x-1, farestPointPosition.y);
            else
                referenceX = new Point(farestPointPosition.x+1, farestPointPosition.y);

            if(farestPointPosition.y > imageCenter.y)
                referenceY = new Point(farestPointPosition.x, farestPointPosition.y-1);
            else
                referenceY = new Point(farestPointPosition.x, farestPointPosition.y+1);

            referenceD = new Point(referenceX.x, referenceY.y);
            double distanceX = referenceX.distance(imageCenter);
            double distanceY = referenceY.distance(imageCenter);
            double distanceD = referenceD.distance(imageCenter);

            if(distanceX < distanceY)
            {
                if(distanceX < distanceD)
                    farestPointPosition.setLocation(referenceX);
                else
                    farestPointPosition.setLocation(referenceD);
            }
            else
            {
                if(distanceY < distanceD)
                    farestPointPosition.setLocation(referenceY);
                else
                    farestPointPosition.setLocation(referenceD);
            }
            pointsToDraw.add(new Point(farestPointPosition));
        }
    }

    private static void drawPoint(BufferedImage inputImage, Point point) {
        Graphics g = inputImage.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect(point.x, point.y, 2,2);
    }

    private static Point[] generateFarestPointPositions(double[] vectorAngles, Point imageSize) {

        Point imageCenter = new Point(imageSize.x/2, imageSize.y/2);
        Point[] farestPoints = new Point[vectorAngles.length];


        for(int i=0; i < farestPoints.length; i++)
        {
            double angle = vectorAngles[i];

            int sector = getSpecialSector(angle, imageSize);
            System.out.println("Sector: " + sector);
            farestPoints[i] = new Point();


            switch (sector)
            {
                case 0:
                    farestPoints[i].x = (int) Math.min((Math.atan(angle)*imageSize.x), imageSize.x-1);
                    farestPoints[i].y = 0;
                    break;
                case 1:
                    farestPoints[i].x = imageSize.x-1;
                    farestPoints[i].y = (int) (Math.atan(angle-(Math.PI-threshAngle))*imageSize.y);
                    break;
                case 2:
                    farestPoints[i].x = Math.max(imageSize.x -1 -(int) (Math.atan(angle-Math.PI)*imageSize.x),0);
                    farestPoints[i].y = imageSize.y-1;
                    break;
                case 3:
                    farestPoints[i].x = 0;
                    farestPoints[i].y = imageSize.y - (int) (Math.atan(angle-(2*Math.PI-threshAngle))*imageSize.y) ;
                    break;
            }

        }
        return farestPoints;
    }

    private static double[] generateVectorAngles(int pointCount) {
        double[] vectorAngles = new double[pointCount];

        double radianPerPoint = (2*Math.PI)/pointCount;
        for(int i=0; i < pointCount; i++)
        {
            vectorAngles[i] = i*radianPerPoint;
        }
        //double[] vectorAngles = new double[1];
        //vectorAngles[0] = Math.tan(1)+0.00001;

        return vectorAngles;
    }
    private static int getAlphaAt(BufferedImage image, int x, int y)
    {
        return (image.getRGB(x,y) & 0xFF000000) >> 24 & 0x000000FF;
    }
    private static boolean isTransparentAt(BufferedImage image, Point point)
    {
        return getAlphaAt(image, point.x, point.y) == 0 ;
    }
    private static int getSpecialSector(double angle, Point imageSize)
    {
        if(angle < Math.PI-threshAngle)
            return 0;
        if(angle < Math.PI)
            return 1;
        if(angle < 2*Math.PI-threshAngle)
            return 2;
        if(angle < 2*Math.PI)
            return 3;
        return 0;
    }
}

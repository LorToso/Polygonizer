package poligonizer;

import filters.ChannelFilter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by lorenzo toso on 19.03.15
 */
class PolygonCreator {
    private final ArrayList<Point> pointsToDraw = new ArrayList<>();
    private final BufferedImage image;
    private final int pointCount;
    private final ChannelFilter filter;


    public PolygonCreator(BufferedImage inputImage, int pointCount, ChannelFilter filter)
    {
        this.image = inputImage;
        this.pointCount = pointCount;
        this.filter = filter;
    }


    public Polygon createPolygon()
    {
        Point[] approximationPoints = generateBorderPoints(pointCount);

        Polygon polygon = new Polygon();

        for (Point point : approximationPoints) {
            MovePointTowardsMid(point);
            polygon.addPoint(point.x, point.y);
        }

        return polygon;
    }

    private Point[] generateBorderPoints(int pointCount) {
        Point imageSize = new Point(image.getWidth(), image.getHeight());

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

    private void MovePointTowardsMid(Point point) {
        Point imageCenter = new Point(image.getWidth()/2, image.getHeight()/2);

        while(!filter.isFiltered(getARGBAt(point)))
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
            pointsToDraw.add(new Point(point));
        }
    }

    private int getARGBAt(Point point) {
        return image.getRGB(point.x, point.y);
    }

    private static void drawPoint(BufferedImage inputImage, Point point) {
        Graphics g = inputImage.getGraphics();
        g.setColor(Color.BLUE);
        g.drawRect(point.x, point.y, 2,2);
    }

    public void drawPoints() {
        for(Point p : pointsToDraw)
            drawPoint(image, p);
    }
}

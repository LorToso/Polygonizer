import filters.And;
import filters.Blue;
import filters.Green;
import filters.Red;
import poligonizer.BuildablePolygon;
import poligonizer.CreatePolygon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Totally created by lorenzo TOSO On 19.03.15.
 */
class Main {
    public static void main(String[] args) {
        BufferedImage img1 = null;
        try {
            img1 = ImageIO.read(new File("star.png"));
        } catch (IOException ignored) {
        }

        BuildablePolygon buildablePolygon = CreatePolygon
                .of(img1)
                .withPointCountOf(1000)
                .filterWhere(new And(Red.notEquals(255), Green.notEquals(255), Blue.notEquals(255)));

        Polygon polygon1 = buildablePolygon
                        //.drawPoints()
                .build();

        drawImage(img1, polygon1);
    }

    private static void drawImage(BufferedImage img, Polygon polygon) {
        Graphics g2 = img.getGraphics();
        g2.setColor(Color.BLUE);
        g2.drawPolygon(polygon);

        BufferedImage bufferedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g =bufferedImage.getGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.drawImage(img, 0, 0, null);


        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

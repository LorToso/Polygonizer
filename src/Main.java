import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lorenzo on 19.03.15.
 */
public class Main {
    public static void main(String[] args) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("test.png"));
        } catch (IOException ignored) {
        }

        Polygon polygon = Polygonizer.CreatePolygonFromImage(img, 8);

        //BufferedImage bufferedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        img.getGraphics().setColor(Color.BLUE);
        //img.getGraphics().drawPolygon(polygon);

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

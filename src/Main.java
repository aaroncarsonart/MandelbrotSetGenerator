import javax.swing.*;
import java.awt.*;

public class Main {

    public static void testMandelbrot() {
        for (double y = -2.0; y <= 2.0; y += 0.05) {
            for (double x = -2.0; x <= 2.0; x += 0.05) {
                ComplexNumber c = new ComplexNumber(x, y);
                ComplexNumber zn = MandelbrotSet.iterate(c, 100);
                if (MandelbrotSet.contains(zn)) {
                    System.out.print("# ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public static void displayGraphicsWindow() {
        CustomGraphicsPanel panel = new CustomGraphicsPanel();
        panel.setPreferredSize(new Dimension(1000, 750));

        JFrame window = new JFrame("MandelbrotSet Fractal Generator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        //testMandelbrot();
        displayGraphicsWindow();
    }
}

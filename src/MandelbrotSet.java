public class MandelbrotSet {

    public static ComplexNumber iterate(ComplexNumber c, int steps) {
        ComplexNumber z = new ComplexNumber(0, 0);
        for (int i = 1; i < steps; ++i) {
            z = z.square().add(c);
        }
        return z;
    }

    public static ComplexNumber iterate(double cx, double cy, int steps) {
        double zx, zy, nzx, nzy;
        zx = 0;
        zy = 0;
        for (int i = 1; i < steps; ++i) {
            nzx = Math.pow(zx, 2) - Math.pow(zy, 2) + cx;
            nzy = 2 * zx * zy + cy;
            zx = nzx;
            zy = nzy;
        }
        return new ComplexNumber(zx, zy);
    }

    public static boolean contains(ComplexNumber c) {
        return Math.abs(c.x) < 2 || Math.abs(c.y) < 2;
    }
}

public class ComplexNumber {

    public final double x;
    public final double y;

    public ComplexNumber(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ComplexNumber add(ComplexNumber that) {
        double nx = this.x + that.x;
        double ny = this.y + that.y;
        return new ComplexNumber(nx, ny);
    }

    public ComplexNumber square() {
        double nx = Math.pow(x, 2) - Math.pow(y, 2);
        double ny = 2 * x * y;
        return new ComplexNumber(nx, ny);
    }
}
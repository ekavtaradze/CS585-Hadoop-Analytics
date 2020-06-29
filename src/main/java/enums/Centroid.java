package enums;

public class Centroid {

    private double x;
    private double y;

    public Centroid() {

    }

    public Centroid(String lineOfCenters) {
        String[] split = lineOfCenters.split(",");
        String x = split[0];
        String y = split[1];
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }

    public Centroid(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toString() {
        return x + "," + y;
    }

}

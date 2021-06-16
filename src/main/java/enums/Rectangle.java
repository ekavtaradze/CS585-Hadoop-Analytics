package enums;

public class Rectangle {

    private double xBottomLeft;
    private double yBottomLeft;
    private double height;
    private double width;
    private String input;

    public Rectangle(String line) {
        input = line;
        String[] parts = line.split(",");
        xBottomLeft = Double.parseDouble(parts[0]);
        yBottomLeft = Double.parseDouble(parts[1]);
        height = Double.parseDouble(parts[2]);
        width = Double.parseDouble(parts[3]);

    }

    public double getxBottomLeft() {
        return xBottomLeft;
    }

    public void setxBottomLeft(double xBottomLeft) {
        this.xBottomLeft = xBottomLeft;
    }

    public double getyBottomLeft() {
        return yBottomLeft;
    }

    public void setyBottomLeft(double yBottomLeft) {
        this.yBottomLeft = yBottomLeft;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean insideThisRectangle(Point point) {
        double x = point.getX();
        double y = point.getY();
        if (x >= xBottomLeft && x <= (xBottomLeft + width)
                && y >= yBottomLeft && y <= (yBottomLeft + height)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return input;
    }
}
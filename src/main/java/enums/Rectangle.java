package enums;

public class Rectangle {

    private double xBottomLeft;
    private double yBottomLeft;
    private double height;
    private double width;
    private String asString;

    public Rectangle(String line) {
        String[] parts = line.split(",");
        xBottomLeft = Integer.parseInt(parts[0]);
        yBottomLeft = Integer.parseInt(parts[1]);
        height = Integer.parseInt(parts[2]);
        width = Integer.parseInt(parts[3]);
        asString = line;
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
        if (x >= xBottomLeft && x <= (xBottomLeft + height) && x <= (xBottomLeft + width)
                && y >= yBottomLeft && y <= (yBottomLeft + height) && y <= (yBottomLeft + width)) {
                return true;
        }
        return false;
    }

    public String toString(){
        return asString;
    }
}

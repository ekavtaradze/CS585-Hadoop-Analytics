package enums;

public class Window {

    private double xLower;
    private double yLower;
    private double xUpper;
    private double yUpper;
    private String input;

    public Window(){

    }

    public Window(String line){
        input = line;
        String[] values = line.split("\\(")[1].split("\\)")[0].split(","); //should be 4 left
        xLower = Double.parseDouble(values[0]);
        yLower = Double.parseDouble(values[1]);
        xUpper = Double.parseDouble(values[2]);
        yUpper = Double.parseDouble(values[3]);

        // System.out.println("Window" + values[0] +" "+ xLower);
    }

    public double getxLower() {
        return xLower;
    }

    public void setxLower(double xLower) {
        this.xLower = xLower;
    }

    public double getyLower() {
        return yLower;
    }

    public void setyLower(double yLower) {
        this.yLower = yLower;
    }

    public double getxUpper() {
        return xUpper;
    }

    public void setxUpper(double xUpper) {
        this.xUpper = xUpper;
    }

    public double getyUpper() {
        return yUpper;
    }

    public void setyUpper(double yUpper) {
        this.yUpper = yUpper;
    }

    boolean isInside(double x, double y){
        if(x>= xLower && x<=xUpper && y>= yLower && y<=yUpper){
            return true;
        }
        return false;
    }
    public boolean rectangleIsInside(Rectangle rectangle){
        double x1 = rectangle.getxBottomLeft();
        double y1 = rectangle.getyBottomLeft();
        double x2 = x1+ rectangle.getWidth();
        double y2 = y1+ rectangle.getHeight();
        return isInside(x1, y1) || isInside(x2, y2);
    }
    public boolean pointIsInside(Point point){
        double x = point.getX();
        double y = point.getY();
        return isInside(x,y);
    }
    public String toString() {
        return input;
    }
}
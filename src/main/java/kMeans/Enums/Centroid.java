package kMeans.Enums;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Centroid {

    private double x;
    private double y;
    //private static Map<String, String> centroids = new HashMap<String, String>();

    public Centroid(){

    }

    public Centroid(String lineOfCenters, String div){
        String[] split = lineOfCenters.split(div);
        String x = split[0];
        String y = split[1];
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
      //  this.x = Integer.parseInt(x);
       // this.y = Integer.parseInt(y);
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
}

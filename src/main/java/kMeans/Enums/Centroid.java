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

    public Centroid(String lineOfCenters){
        String[] split = lineOfCenters.split(" ");
        String x = split[0];
        String y = split[1];
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
    }
    public Centroid(Double x, Double y){
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

    public String toString(){
        return x+" "+y;
    }

    public boolean equals(Centroid n){
        if(this.x==n.getX() && this.y==n.getY()){
            return true;
        }
        return false;

    }
}

package kMeans;

import kMeans.Enums.Centroid;
import kMeans.Enums.Point;

public class Distance {


    public Distance() {

    }

    public static double findEucledianDistance(Centroid centroid, Point point) {

        double xCentr = centroid.getX();
        double yCentr = centroid.getY();

        double x = point.getX();
        double y = point.getY();
        return Math.sqrt(Math.pow((xCentr - x), 2) + Math.pow((yCentr - y), 2));

    }
}

package kMeans.Enums;

import java.util.ArrayList;

public class CentroidList {

    private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();

    private static void add(Centroid c){
        centroids.add(c);
    }
    private static ArrayList<Centroid> get(){
        return centroids;
    }
}

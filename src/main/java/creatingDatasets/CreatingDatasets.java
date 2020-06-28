package creatingDatasets;

import javafx.util.Pair;

import java.io.*;
import java.util.Hashtable;
import java.util.Random;

/**
 * Dataset	P (set	 of	 points	in	 two	 dimensional	 space)	 as
 * shown	in	Figure	1a,	and	Dataset	R	(set	of	rectangles	in	 two	dimensional	space)
 * as	shown	in	Figure	1b.
 * The	spatial	join	operation	is	to	join	these	two	datasets
 * and	report	any	pair	(rectangle	r,	point	p)	where	p
 * is	contained	inside r (or	on the	boundaries of	r).
 */
public class CreatingDatasets {

    //Dataset P - set of 2D points
    //Dataset R - set of 2D rectangles
    //Whole space is X x Y, each 1...10'000

    //Space min and max
    static int min = 1;
    static int max = 10000;
    static int fileSizeMin = 104857600; // more than 100 mb

    static int minCentroid = 10;
    static int maxCentroid = 100;

   /* static Hashtable<String, String> pCoordinates = new Hashtable<String, String>();
    static Hashtable<String, String> rCoordinates = new Hashtable<String, String>();
    static Hashtable<String, String> rCoordinatesWidthAndHeight = new Hashtable<String, String>();*/

    public static void main(String[] args) {
        createDatasets();
        createCentroids();
    }

    public static void createCentroids() {
        try {
            System.out.println("Creating Centroids");
            String nameP = "centroids.txt";

            File centroids = new File(nameP);
           // centroids.getParentFile().mkdirs();
            //centroids.createNewFile();

            BufferedWriter writerP = new BufferedWriter(new FileWriter(centroids));

            int numOfCentroids = CreatingDatasets.createRandomInt(1, 6);

            int i = 0;
            while (i < numOfCentroids) { //100MB
                writerP.write(createRandomInt(minCentroid, maxCentroid) + "," +
                        createRandomInt(minCentroid, maxCentroid));
                writerP.newLine();
                i++;
            }
            writerP.close();
        } catch (Exception e) {

        }
    }

    public static void createDatasets() {
        try {
            System.out.println("Creating datasets P and R");
            String nameP = "datasetP.txt";
            String nameR = "datasetR.txt";

            File datasetP = new File(nameP);
            File datasetR = new File(nameR);

            BufferedWriter writerP = new BufferedWriter(new FileWriter(nameP));
            BufferedWriter writerR = new BufferedWriter(new FileWriter(nameR));

            while (datasetP.length() <= fileSizeMin) { //100MB
                writerP.write(createP());
                writerP.newLine();
            }
            writerP.close();
            while (datasetR.length() <= fileSizeMin) { //100MB
                writerR.write(createR());
                writerR.newLine();
            }
            writerR.close();
        } catch (Exception e) {

        }
    }

    public static int createRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static Pair<Integer, Integer> createRandomCoordinates(int min, int max) {
        return new Pair(createRandomInt(min, max), createRandomInt(min, max));
    }


    public static String createP() {
        int x = createRandomInt(min, max);
        int y = createRandomInt(min, max);
        String pair = x + "" + y;
        return pair;
       /* if (pCoordinates.containsKey(pair)) {
            createRandomCoordinates(min, max);
            return "null";
        } else {
            pCoordinates.put(pair, "");
            return x + "," + y;
        }*/

    }

    public static String createR() {
        // Pair<Integer, Integer> coordinates = createRandomCoordinates(min, max);
        // int x1 = coordinates.getKey(); //bottomLeftX
        // int y1 = coordinates.getValue(); //bottomLeftY

        int x = createRandomInt(min, max); //bottomLeftX
        int y = createRandomInt(min, max); //bottomLeftY

        int width = createRandomInt(1, 5);
        int height = createRandomInt(1, 20);

        String pair = x + "," + y;
        String whPair = pair + "," + width + "," + height;

        return whPair;
      /*  if (rCoordinatesWidthAndHeight.containsKey(whPair)) {
            createR();
        } else {
            rCoordinatesWidthAndHeight.put(whPair, null);
            if (x + width <= max && x + height <= max
                    && y + width <= max && y + height <= max) {
                return x + "," + y + "," + height + "," + width;
            } else {
                createR();
            }
        }

        System.out.println("not found");
        return "not found";*/
    }
}

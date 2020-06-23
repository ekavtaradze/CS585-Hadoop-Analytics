package problemOne;

import javafx.util.Pair;

import java.io.*;
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

    public static void main(String[] args) {

        try{
            String nameP = "datasetP.txt";
            String nameR = "datasetR.txt";

            File datasetP = new File(nameP);
            File datasetR = new File(nameR);

            BufferedWriter writerP = new BufferedWriter(new FileWriter(nameP));
            BufferedWriter writerR = new BufferedWriter(new FileWriter(nameR));

            while(datasetP.length()<= 104857600) {
                writerP.write(createP(min, max));
                writerP.newLine();
            }
            writerP.close();
            while(datasetR.length()<= 104857600) {
                writerR.write(createR());
                writerR.newLine();
            }
            writerR.close();
        } catch(Exception e){

        }

    }

    public static int createRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static Pair<Integer, Integer> createRandomCoordinates(int min, int max){
        return new Pair(createRandomInt(min, max), createRandomInt(min, max));
    }

    public static String createP(int min, int max){
        Pair<Integer, Integer> coordinates = createRandomCoordinates(min, max);
        int x1 = coordinates.getKey();
        int y1 = coordinates.getValue();
        return x1 +","+y1;
    }

    public static String createR() {
        Pair<Integer, Integer> coordinates = createRandomCoordinates(min, max);
        int x1 = coordinates.getKey(); //bottomLeftX
        int y1 = coordinates.getValue(); //bottomLeftY

        int width = createRandomInt(1,5);
        int height = createRandomInt(1, 20);

        if(x1+width <=max && x1+height<=max
         && y1+width <=max && y1+height<=max){
           return x1 +"," + y1 +"," + height +"," + width;
        }
        else {
            createR();
        }
        return "not found";
    }
}

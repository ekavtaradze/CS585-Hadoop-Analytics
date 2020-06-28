package kMeans;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import kMeans.Enums.Centroid;
import kMeans.Enums.Point;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KMeans {

    private static int maxIteration = 6; //max number of iterations allowed
    private static boolean hasNOTChanged = false;
    private static int numOfCentroids = 0;
    private static int numChanges = 0;

    public static void setNumOfCentroids(int num) {
        numOfCentroids = num;
    }

    public static void centersDidNotChange() {
        if (numChanges == numOfCentroids) {
            hasNOTChanged = true;
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Path input = new Path(args[0]);
        Path centers = new Path(args[1]);
        Path output = new Path(args[2]);
        Path outputTemp = new Path(args[2] + "/temp");

        run(input, outputTemp, centers);
        // writeFile(outputTemp.toString(), input.toString());
     /*  int i = 1;
        while(i<=6 && true){
        run(input, output, centers);
        i++;
        }*/
    }

    public static void writeFile(String input, String output) throws IOException {
        FileInputStream fis = new FileInputStream(input);

        /* assuming that the file exists and need not to be
           checked */
        FileOutputStream fos = new FileOutputStream(output);

        int i;
        while ((i = fis.read()) != -1) {
            fos.write(i);
        }
        fis.close();
        fos.close();
    }


    public static void run(Path input, Path output, Path centers) throws IOException, ClassNotFoundException, InterruptedException {

//        Path input = new Path(args[0]);
//        Path centers = new Path(args[1]);
//        Path output = new Path(args[2]);

        hasNOTChanged = false;
        numChanges =0;
        Configuration config = new Configuration();
        config.set("centroids", centers.toString());
        Job job = new Job(config, "wordcount");

        FileSystem fs = FileSystem.get(output.toUri(), config);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }

        job.setJarByClass(KMeans.class);

        FileInputFormat.addInputPath(job, input);

        job.setMapperClass(KMeansMapper.class);
        job.setCombinerClass(KMeansCombiner.class);
        job.setReducerClass(KMeansReducer.class);
        job.setNumReduceTasks(1);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileOutputFormat.setOutputPath(job, output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }


}

package kMeans;

import java.io.*;
import java.util.ArrayList;

import enums.Centroid;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KMeans {


    private static int maxIteration = 6; //max number of iterations allowed

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {


        Path input = new Path(args[0]);
        Path centers = new Path(args[1]);
        Path output = new Path(args[2]);

        ArrayList<Centroid> oldCentroids;
        ArrayList<Centroid> newCentroids;

        Path outputTemp = new Path(args[2] + "/part-r-00000");

        oldCentroids = saveOutput(centers);

        //first run // i=0
        run(input, output, centers);
        newCentroids = saveOutput(outputTemp); //write this to centroids

        int i = 1;
        while (i < maxIteration && centroidsHaveNotChanged(oldCentroids, newCentroids)) {
            oldCentroids = newCentroids;
            run(input, output, centers);
            newCentroids = saveOutput(outputTemp);
            i++;
        }
    }


    public static boolean centroidsHaveNotChanged(ArrayList<Centroid> oldC, ArrayList<Centroid> newC) {

        for (Centroid c : oldC) {
            System.out.print(c.toString() + " ");
        }
        System.out.println();
        for (Centroid c : newC) {
            System.out.print(c.toString() + " ");
        }
        return !oldC.equals(newC);
    }

    /**
     * Read from HDFS copied from https://www.netjstech.com/2018/02/java-program-to-read-file-in-hdfs.html
     */
    public static ArrayList<Centroid> saveOutput(Path input) throws IOException {

        Configuration conf = new Configuration();
        FSDataInputStream in = null;
        ArrayList<Centroid> list = new ArrayList<Centroid>();
        try {
            FileSystem fs = FileSystem.get(conf);
            Path inFile = input;
            if (!fs.exists(inFile)) {
                System.out.println("Input file not found");
                throw new IOException("Input file not found");
            }
            in = fs.open(inFile);
            String str = IOUtils.toString(in, "UTF-8");
            String[] split = str.split("\n");
            int i = 0;
            while (i < split.length) {
                list.add(new Centroid(split[i]));
                i++;
            }
            in.close();
            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static void run(Path input, Path output, Path centers) throws IOException, ClassNotFoundException, InterruptedException {


        Configuration config = new Configuration();
        config.set("centroids", centers.toString());
        Job job = new Job(config, "KMeans");

        FileSystem fs = FileSystem.get(output.toUri(), config);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }

        job.setJarByClass(KMeans.class);

        FileInputFormat.addInputPath(job, input);

        job.setMapperClass(KMeansMapper.class);
        job.setCombinerClass(KMeansCombiner.class);
        job.setReducerClass(KMeansReducer.class);
        job.setNumReduceTasks(1); //requirement?


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileOutputFormat.setOutputPath(job, output);

        job.waitForCompletion(true);
    }


}

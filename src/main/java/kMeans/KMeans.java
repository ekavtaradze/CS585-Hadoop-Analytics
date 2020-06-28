package kMeans;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import kMeans.Enums.Centroid;
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
    private static boolean hasNOTChanged = false;
    private static int numOfCentroids = 0;
    private static int numChanges = 0;
    private static List<Centroid> list = new ArrayList<Centroid>();

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
       //

        run(input, output, centers);
        Path outputTemp = new Path(args[2] + "/part-r-00000");
        saveOutput(outputTemp);
        //System.out.println("Input file not found");
        //writeFile(output.toString(), input.toString());
        int i = 1;
        while (i < 6 && true) {
           // run(input, output, centers);
            i++;
        }
    }

    /**
     * Read from HDFS copied from https://www.netjstech.com/2018/02/java-program-to-read-file-in-hdfs.html
     */
    public static void saveOutput(Path input) throws IOException {
       // System.out.println("Input file not found");
        Configuration conf = new Configuration();
       // System.out.println("saveOutput");
       // FileSystem fs = FileSystem.get(path.toUri(), conf);
        FSDataInputStream in = null;
        OutputStream out = null;
        try {
            FileSystem fs = FileSystem.get(conf);
            // Input file path
            Path inFile = input;
            // Check if file exists at the given location
            if (!fs.exists(inFile)) {
                System.out.println("Input file not found");
                throw new IOException("Input file not found");
            }
            // open and read from file
            in = fs.open(inFile);
            //displaying file content on terminal
            System.out.println("elene");
            out = System.out;
            byte buffer[] = new byte[256];

            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("error");
            e.printStackTrace();

        }finally {
            // Closing streams
            try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.out.println("error2");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    public static void writeFile(String input, String output) throws IOException {
        FileInputStream fis = new FileInputStream(input);

        FileOutputStream fos = new FileOutputStream(output);

        int i;
        while ((i = fis.read()) != -1) {
            fos.write(i);
        }
        fis.close();
        fos.close();
    }


    public static void run(Path input, Path output, Path centers) throws IOException, ClassNotFoundException, InterruptedException {

        hasNOTChanged = false;
        numChanges = 0;
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

       // System.exit(job.waitForCompletion(true) ? 0 : 1);
        job.waitForCompletion(true);
    }


}

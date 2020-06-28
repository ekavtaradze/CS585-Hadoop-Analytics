package kMeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kMeans.Enums.Centroid;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IterationControl {
    public class KMeansMapper extends Mapper<Text, Text, Object, Text> {
        // private final IntWritable one = new IntWritable(1);

        private Text word = new Text();
        private List<Centroid> centroids = new ArrayList<Centroid>();

        /*setup should be called only once before the task begins
        so hopefully
        * */
        protected void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            Path centroidsPath = new Path(conf.get("centers"));
            //  CentroidTracker centroidTracker = new CentroidTracker();
            FileSystem fs = FileSystem.get(context.getConfiguration());
            InputStreamReader inputStream = new InputStreamReader(fs.open(centroidsPath));

            BufferedReader reader = new BufferedReader(inputStream);
            String line = reader.readLine();
            while (line != null) {
                //centroidTracker.add(line);
                centroids.add(new Centroid(line, " "));
              //  System.out.println(centroids.contains());
                line = reader.readLine();
            }
            reader.close();
        }

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] points =line.split(",");

            Centroid point = new Centroid(line, ",");
            //StringTokenizer tokenizer = new StringTokenizer(line);
            //while (tokenizer.hasMoreTokens()) {
            //    word.set(tokenizer.nextToken());
            // output.collect(word, one);
            //  }
            Double minDistance = Double.MAX_VALUE;
            Centroid minCenter = null;

            for(Centroid centroid: centroids )
            {
                Double compare = Distance.findEucledianDistance(centroid, point);
                    if (minDistance > compare) {
                        minDistance = compare;
                        minCenter = centroid;
                    }
                }
                context.write(minCenter, new Text(minDistance.toString()));

          //  context.write(new Text("elene"), new Text("elene"));

        }
    }

    public class KMeansReducer extends Reducer<Object, Text, Object, Text> {
        public void reduce(Object key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
      /* int sum = 0;
        while (values.hasNext()) {
            sum += values.next().get();
        }
        output.collect(key, new IntWritable(sum));*/

            context.write(new Text((byte[]) key), new Text("Text"));
        }

    }

    static int maxIteration = 6; //max number of iterations allowed
    static String centroidPath = "kMeans/centroids.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        Configuration conf = new Configuration();
        // String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
        Path input = new Path(args[0]);
        Path centers = new Path(args[1]);
        Path output = new Path(args[2]);


        conf.set("centers", centers.toString());
        Job job = new Job(conf, "wordcount");

        job.setJarByClass(IterationControl.class);

        FileInputFormat.addInputPath(job, input);

        job.setMapperClass(KMeansMapper.class);
        job.setReducerClass(KMeansReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileOutputFormat.setOutputPath(job, output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);


    }


}

package kMeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public class KMeansMapper extends Mapper<LongWritable, Text, Text, Text> {
        // private final IntWritable one = new IntWritable(1);

        private Text word = new Text();
        private List<Centroid> centroids = new ArrayList<Centroid>();

        /**
         * setup should be called only once before the task begis
         * reads the centroids dataset, so that each mapped records has access to it
         * @param context
         * @throws IOException
         */
        protected void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            Path centroidsPath = new Path(conf.get("centroids"));

            FileSystem fs = FileSystem.get(context.getConfiguration());
            InputStreamReader inputStream = new InputStreamReader(fs.open(centroidsPath));

            BufferedReader reader = new BufferedReader(inputStream);
            String line = reader.readLine();
            while (line != null) {
                centroids.add(new Centroid(line));
                line = reader.readLine();
            }
            reader.close();
        }

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            Point point = new Point(line);

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
                context.write(new Text(minCenter.toString()), new Text(minDistance.toString()));

        }
    }

    public class KMeansReducer extends Reducer<Object, Text, Object, Text> {
        public void reduce(Object key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            context.write(new Text((byte[]) key), new Text("Text"));
        }

    }

    static int maxIteration = 6; //max number of iterations allowed

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Path input = new Path(args[0]);
        Path centers = new Path(args[1]);
        Path outputFinal = new Path(args[2]);
        Path outputTemp = new Path(args[2] +"/temp");

        run(input, outputTemp, centers);
       /* int i = 1;
        while(i<=6 && true){
        run(input, outputTemp, centers);
        i++;
        }*/
    }

    public static void run(Path input, Path output, Path centers) throws IOException, ClassNotFoundException, InterruptedException {

//        Path input = new Path(args[0]);
//        Path centers = new Path(args[1]);
//        Path output = new Path(args[2]);

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
        job.setReducerClass(KMeansReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileOutputFormat.setOutputPath(job, output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }


}

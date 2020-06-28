package kMeans;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class IterationControl {
    public class KMeansMapper extends Mapper<Text, Text, Text, Text> {
       // private final IntWritable one = new IntWritable(1);
        private Text word = new Text();


        public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            //StringTokenizer tokenizer = new StringTokenizer(line);
            //while (tokenizer.hasMoreTokens()) {
            //    word.set(tokenizer.nextToken());
            // output.collect(word, one);
            //  }
            context.write(new Text("elene"), new Text("elene"));

        }
    }

    public class KMeansReducer extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
      /* int sum = 0;
        while (values.hasNext()) {
            sum += values.next().get();
        }
        output.collect(key, new IntWritable(sum));*/
            context.write(new Text("elene"), new Text("Text"));
        }

    }

    static int maxIteration = 6; //max number of iterations allowed
    static String centroidPath = "kMeans/centroids.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        Configuration conf = new Configuration();
        // String[] files=new GenericOptionsParser(c,args).getRemainingArgs();
        Path input = new Path(args[0]);
        Path output = new Path(args[1]);

        Job job = new Job(conf, "wordcount");

        job.setJarByClass(IterationControl.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));

        job.setMapperClass(KMeansMapper.class);
        job.setReducerClass(KMeansReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileOutputFormat.setOutputPath(job, output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);


    }


}

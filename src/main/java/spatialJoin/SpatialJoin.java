package spatialJoin;

import enums.Point;
import enums.Rectangle;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class SpatialJoin {

    //Space min and max
    static int min = 1;
    static int max = 10000;

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Path datasetP = new Path(args[0]);
        Path datasetR = new Path(args[1]);
        Path output = new Path(args[2]);
        ;

        String window = null;

        if (args.length==4) {
            window = args[3];
        } else {
            window = "W(" + min + "," + min + "," + max + "," + max + ")";
        }

        Configuration config = new Configuration();
        config.set("rectangles", datasetR.toString());
        config.set("windowOfOperation", window);
        Job job = new Job(config, "spatialJoin");

        FileSystem fs = FileSystem.get(output.toUri(), config);
        if (fs.exists(output)) {
            fs.delete(output, true);
        }

        job.setJarByClass(SpatialJoin.class);

        FileInputFormat.addInputPath(job, datasetP);

        job.setMapperClass(SpatialJoinMapper.class);
        job.setReducerClass(SpatialJoinReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileOutputFormat.setOutputPath(job, output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
        // job.waitForCompletion(true);
    }
}
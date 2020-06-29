package spatialJoin;

import enums.Rectangle;
import kMeans.Distance;
import enums.Centroid;
import enums.Point;
import kMeans.KMeans;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SpatialJoinMapper extends Mapper<LongWritable, Text, Rectangle, Point> {
    // private final IntWritable one = new IntWritable(1);

   // private Text word = new Text();
   private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

    /**
     * setup should be called only once before the task begis
     * reads the centroids dataset, so that each mapped records has access to it
     *
     * @param context
     * @throws IOException
     */
    protected void setup(Context context) throws IOException {
        Configuration conf = context.getConfiguration();
        Path centroidsPath = new Path(conf.get("rectangles"));
        FileSystem fs = FileSystem.get(context.getConfiguration());
        InputStreamReader inputStream = new InputStreamReader(fs.open(centroidsPath));
        BufferedReader reader = new BufferedReader(inputStream);
        String line = reader.readLine();
        int i =0;
        while (line != null) {
            rectangles.add(new Rectangle(line));
           // System.out.println(line);
            line = reader.readLine();
            i++;
        }
        KMeans.setNumOfCentroids(i);
        reader.close();
    }

    //processes Pints
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        Point point = new Point(line);
        for (Rectangle rectangle : rectangles) {

            if(rectangle.insideThisRectangle(point)){
                //context.write(new Text(rectangle.toString()), new Text(point.toString()));
                context.write(rectangle, point);
            }

        }


    }
}


package spatialJoin;

import enums.Rectangle;
import enums.Window;
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

public class SpatialJoinMapper extends Mapper<LongWritable, Text, Text, Text> {
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

        System.out.println("Entering SpatialJoin Mapper Setup");

        Configuration conf = context.getConfiguration();

        Path datasetRPath = new Path(conf.get("rectangles"));
        FileSystem fs = FileSystem.get(context.getConfiguration());
        InputStreamReader inputStream = new InputStreamReader(fs.open(datasetRPath));
        BufferedReader reader = new BufferedReader(inputStream);
        String line = reader.readLine();

        Window window = new Window(conf.get("windowOfOperation"));

        Rectangle rectangle;
        // int i =0;
        while (line != null) {
            // System.out.println(line);
            rectangle = new Rectangle(line);
            if(window.rectangleIsInside(rectangle)){
                rectangles.add(rectangle);
            }

            line = reader.readLine();
            //  i++;
        }
        reader.close();
        System.out.println("Exiting SpatialJoin Mapper Setup" + rectangles.size());
    }

    //processes Pints
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //System.out.println("Entering SpatialJoin Mapper map");
        String line = value.toString();
        Configuration conf = context.getConfiguration();
        Window window = new Window(conf.get("windowOfOperation"));
        Point point = new Point(line);
        //  System.out.println(point.toString());
        int length = rectangles.size();
        int i =0;
        for(Rectangle rectangle: rectangles){
            if(rectangle.insideThisRectangle(point)){ // && window.pointIsInside(point)
                //System.out.println("Passed"+rectangle.toString()+" "+point.toString());
                context.write(new Text(rectangle.toString()), new Text(point.toString()));
                //context.write(rectangle, point);
            }
        }

        // System.out.println("Exiting SpatialJoin Mapper map");
    }
}
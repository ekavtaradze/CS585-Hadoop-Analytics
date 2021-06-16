package spatialJoin;

import enums.Rectangle;
import enums.Window;
import enums.Point;
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

public class SpatialJoinMapper extends Mapper<LongWritable, Text, Text, Text> {
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
        while (line != null) {
            rectangle = new Rectangle(line);
            if (window.rectangleIsInside(rectangle)) {
                rectangles.add(rectangle);
            }

            line = reader.readLine();
        }
        reader.close();
        System.out.println("Exiting SpatialJoin Mapper Setup" + rectangles.size());
    }

    //processes Pints
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        Configuration conf = context.getConfiguration();
        Window window = new Window(conf.get("windowOfOperation"));
        Point point = new Point(line);
        for (Rectangle rectangle : rectangles) {
            if (rectangle.insideThisRectangle(point) && window.pointIsInside(point)) { // && window.pointIsInside(point)
                context.write(new Text(rectangle.toString()), new Text(point.toString()));
            }
        }

    }
}
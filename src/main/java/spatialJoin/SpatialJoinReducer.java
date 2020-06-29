package spatialJoin;

import enums.Point;
import enums.Rectangle;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SpatialJoinReducer extends Reducer<Rectangle, Point, Text, Text> {
    public void reduce(Rectangle key, Iterable<Point> values, Context context) throws IOException, InterruptedException {
        System.out.println("reducer");
        for (Point point : values) {
            System.out.println(point.toString());
            context.write(new Text(key.toString()), new Text(point.toString()));
        }
    }

}


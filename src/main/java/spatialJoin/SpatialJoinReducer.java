package spatialJoin;

import enums.Point;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SpatialJoinReducer extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text text : values) {
            Point point = new Point(text.toString());
            context.write(new Text(key.toString()), new Text(point.toString()));
        }
    }

}


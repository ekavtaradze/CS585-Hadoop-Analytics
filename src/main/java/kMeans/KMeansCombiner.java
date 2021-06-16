package kMeans;

import enums.Point;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class KMeansCombiner extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        int x = 0;
        int y = 0;
        Double xSum = 0.0;
        Double ySum = 0.0;

        System.out.println("Combiner" + key.toString());
        for (Text value : values) {
            Point point = new Point(value.toString());
            x++;
            y++;
            xSum += point.getX();
            ySum += point.getY();

        }
        Text output = new Text(xSum + "," + x + "," + ySum + "," + y);
        context.write(key, output);
    }
}

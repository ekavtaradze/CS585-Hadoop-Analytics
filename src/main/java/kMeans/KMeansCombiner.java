package kMeans;

import kMeans.Enums.Point;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class KMeansCombiner extends Reducer<Text, Text, Text, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        //mapper output: context.write(new Text(center.toString()), new Text(minDistance.toString()));
        int x = 0;
        int y = 0;
        Double xSum = 0.0;
        Double ySum = 0.0;

        System.out.println("Combiner"+ key.toString());
        for (Text value : values) {
           // System.out.println(value.toString());
            Point point = new Point(value.toString());
            x++; y++;
            xSum += point.getX();
            ySum += point.getY();
            System.out.println("Combinerhere");
        }
        Text output = new Text(xSum +","+x +","+ySum+","+y);
        context.write(key, output);
    }
}

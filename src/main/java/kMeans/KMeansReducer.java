package kMeans;

import enums.Centroid;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class KMeansReducer extends Reducer<Text, Text, Object, Text> {
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int x = 0;
        int y = 0;
        Double xSum = 0.0;
        Double ySum = 0.0;
        System.out.println("reducer");
        for (Text value : values) {
            String[] vals = value.toString().split(",");
            x += Integer.parseInt(vals[1]);
            y += Integer.parseInt(vals[3]);
            xSum += Double.parseDouble(vals[0]);
            ySum += Double.parseDouble(vals[2]);
        }
        Double xAverage = xSum/x;
        Double yAverage = ySum/y;
        Centroid oldCenter = new Centroid(key.toString());
        Centroid newCenter = new Centroid(xAverage, yAverage);

        context.write(new Text(newCenter.toString()), null);
    }

}


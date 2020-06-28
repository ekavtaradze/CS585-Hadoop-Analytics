package kMeans;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class KMeansReducer extends Reducer<Object, Text, Object, Text> {
    public void reduce(Object key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        context.write(new Text("elene,"), null);
    }

}


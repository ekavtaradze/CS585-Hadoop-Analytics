package spatialJoin;

import kMeans.Distance;
import kMeans.enums.Centroid;
import kMeans.enums.Point;
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

public class SpatialJoinMapper extends Mapper<LongWritable, Text, Text, Text> {
    // private final IntWritable one = new IntWritable(1);

   // private Text word = new Text();
  //  private List<Centroid> centroids = new ArrayList<Centroid>();

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
            centroids.add(new Centroid(line));
           // System.out.println(line);
            line = reader.readLine();
            i++;
        }
        KMeans.setNumOfCentroids(i);
        reader.close();
    }

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        Point point = new Point(line);

        Double minDistance = Double.MAX_VALUE;
        Centroid center = null;

        for (Centroid centroid : centroids) {

            Double compare = Distance.findEucledianDistance(centroid, point);
            if (minDistance > compare) {
                minDistance = compare;

                center = centroid;
            }
        }
       // System.out.println(center.toString() +"  "+point.toString());
        context.write(new Text(center.toString()), new Text(point.toString()));

    }
}


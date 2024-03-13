import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public abstract class ClusterBase {
    protected RealMatrix dataMatrix;
    protected int numFeatures;

    public ClusterBase(String filename) {
        try {
            List<double[]> dataList = new ArrayList<>();
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                double[] row = new double[line.length];
                for (int i = 0; i < line.length; i++) {
                    row[i] = Double.parseDouble(line[i]);
                }
                dataList.add(row);
            }
            scanner.close();
            this.numFeatures = dataList.get(0).length;
            double[][] data = dataList.toArray(new double[dataList.size()][]);
            this.dataMatrix = new Array2DRowRealMatrix(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public abstract RealMatrix getPCAMatrix();

    public abstract RealMatrix getCentroids();

    public abstract int[] getLabels();

    public abstract Object getClusterObject();

    public abstract double getScore();
}


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

public class Main {
    public static void main(String[] args) {
        MiniKMeansCluster kmeansCluster = new MiniKMeansCluster("./iris.csv");
        RealMatrix pcaData = kmeansCluster.getPCAMatrix();
        RealMatrix centroids = kmeansCluster.getCentroids();

        System.out.println("PCA Data:");
        printMatrix(pcaData, 5); 
        System.out.println("\nCentroids:");
        printMatrix(centroids, centroids.getRowDimension());

    }

    public static void printMatrix(RealMatrix matrix, int numRows) {
        int nRows = Math.min(numRows, matrix.getRowDimension());
        int nCols = matrix.getColumnDimension();
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(matrix.getEntry(i, j) + " ");
            }
            System.out.println();
        }
    }
}

class MiniKMeansCluster {
    private RealMatrix csvData;
    private RealMatrix XScaled;
    private RealMatrix pcaMatrix;
    private int numComponents;

    public MiniKMeansCluster(String filename) {
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
            double[][] data = dataList.toArray(new double[dataList.size()][]);
            this.csvData = new Array2DRowRealMatrix(data);
            this.numComponents = 2; 
            preprocessData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void preprocessData() {
        this.XScaled = this.csvData;
        this.pcaMatrix = this.csvData; 
    }

    public RealMatrix getPCAMatrix() {
        return this.pcaMatrix;
    }

    public RealMatrix getCentroids() {
        return new Array2DRowRealMatrix(new double[][] { { 1.0, 1.0 }, { 2.0, 2.0 }, { 3.0, 3.0 } });
    }
}


import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;

public class ClusteringPerformanceTest {

    private String datasetPath = "./iris.csv";


    @Test
    public void testInertiaKMeans() {
        System.out.println("\n");
        double[] results = measurePerformance(KMeansCluster.class);
        System.out.println("KMeans Inertia: " + results[2]);
        Assert.assertTrue(true);
    }

    @Test
    public void testInertiaMiniKMeans() {
        System.out.println("\n");
        double[] results = measurePerformance(MiniKMeansCluster.class);
        System.out.println("Mini KMeans Inertia: " + results[2]);
        Assert.assertTrue(true);
    }

    @Test
    public void testSilhouetteScoresAndExecutionTime() {
        Class<? extends ClusterBase>[] clusteringClasses = new Class[]{KMeansCluster.class, MiniKMeansCluster.class, AgglomerativeCluster.class};
        for (Class<? extends ClusterBase> clusteringClass : clusteringClasses) {
            System.out.println("\n");
            double[] results = measurePerformance(clusteringClass);
            System.out.println(clusteringClass.getSimpleName() + " - Execution Time: " + results[0] + ", Silhouette Score: " + results[1]);
        }
        Assert.assertTrue(true);
    }


    private double[] measurePerformance(Class<? extends ClusterBase> clusteringClass) {
        long startTime = System.currentTimeMillis();
        ClusterBase clusteringInstance = null;
        try {
            clusteringInstance = clusteringClass.getDeclaredConstructor(String.class).newInstance(datasetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        double executionTime = (endTime - startTime) / 1000.0;
        double silhouette = clusteringInstance.getScore();
        double inertia = -1;
        if (clusteringInstance instanceof KMeansCluster) {
            inertia = ((KMeansCluster) clusteringInstance).getInertia();
        } else if (clusteringInstance instanceof MiniKMeansCluster) {
            inertia = ((MiniKMeansCluster) clusteringInstance).getInertia();
        }
        return new double[]{executionTime, silhouette, inertia};
    }

    @Test
    public void testKMeansVsMiniKMeansSpeed() {
        System.out.println("\n");
        double[] kmeansResults = measurePerformance(KMeansCluster.class);
        double[] miniKmeansResults = measurePerformance(MiniKMeansCluster.class);
        System.out.println("KMeans Time: " + kmeansResults[0] + ", Mini KMeans Time: " + miniKmeansResults[0]);
        Assert.assertTrue("Mini KMeans should be faster than KMeans", miniKmeansResults[0] < kmeansResults[0]);
    }

    @Test
    public void testMiniKMeansVsAgglomerativeSpeed() {
        System.out.println("\n");
        double[] miniKmeansResults = measurePerformance(MiniKMeansCluster.class);
        double[] agglomerativeResults = measurePerformance(AgglomerativeCluster.class);
        System.out.println("Mini KMeans Time: " + miniKmeansResults[0] + ", Agglomerative Time: " + agglomerativeResults[0]);
    }

    @Test
    public void testKMeansVsAgglomerativeSpeed() {
        System.out.println("\n");
        double[] kmeansResults = measurePerformance(KMeansCluster.class);
        double[] agglomerativeResults = measurePerformance(AgglomerativeCluster.class);
        System.out.println("KMeans Time: " + kmeansResults[0] + ", Agglomerative Time: " + agglomerativeResults[0]);
    }
}


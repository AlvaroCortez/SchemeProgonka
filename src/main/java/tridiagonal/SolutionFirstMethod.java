package tridiagonal;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created by Den on 11.05.16.
 */
public class SolutionFirstMethod extends Scheme {

    private double[] a;
    private double[] b;
    private double[] c;
    private double[] f;
    double[] alpha;
    double[] beta;
    private double e = 1;
    private double u = 1;
    private double T = 100;
    private double Z = 100;
    private XYSeries series = new XYSeries("Решение");
    XYSeriesCollection xy = new XYSeriesCollection();

    private double[][] gridValues;

    public SolutionFirstMethod(){
        xy.addSeries(series);
    }

    @Override
    public double[][] scheme(double hz, double ht) {
        gridValues = new double[(int) (T / ht ) + 1][(int) ((Z / hz) ) + 1];
        int K = gridValues[0].length - 1;
        int N = gridValues.length - 1;
        a = new double[K + 1];
        b = new double[K + 1];
        c = new double[K + 1];
        f = new double[K + 1];
        alpha = new double[K + 1];
        beta = new double[K + 1];

        double gamma = (Math.pow(ht/hz, 2))*(1/(e*u));

        for (int k = 0; k <= K  /*i < K*/; k++) {
            gridValues[0][k] = Math.sin(hz * k);//PSI(hz * k) - U0;
        }

        for (int n = 1; n <= N; n++) {
            a[0] = -gamma;
            b[0] = 1 + 2*gamma;
            c[0] = -gamma;
            f[0] = gridValues[n-1][0] - (ht/hz/e)*(1 + 1);
            alpha[0] = a[0]/b[0];//gamma/(1 + 2*ALPHA*ht/(C*R) + 2*gamma);
            beta[0] = f[0]/b[0];//(gridValues[k - 1][0] + (ht/C)*PHI(0))/(1 + 2* ALPHA*ht/(C*R) + 2*gamma);
            for (int k = 1; k <= K; k++) {
                a[k] = a[0];
                b[k] = b[0];
                c[k] = -gamma;
                f[k] = gridValues[n - 1][k] - (ht/hz/e)*(1 + 1);
                alpha[k] = - a[k] / (b[k] + c[k] * alpha[k - 1]);
                beta[k] = (f[k] - c[k] * beta[k - 1]) / (b[k] + c[k] * alpha[k - 1]);
            }

            //либо посчитать на последнем элементе
            //gridValues[n][K] = (gridValues[n - 1][K] - a[K]* beta[K - 1] - c[K]* beta[K - 1] + (ht/this.C)* PHI(hz * K))/(a[K]* alpha[K - 1] + b[K] + c[K]* alpha[K - 1]);
            //либо сделать последний элемент бета
            gridValues[n][K] = beta[K];
            for (int k = K; k > 0; k--) {
                gridValues[n][k - 1] = gridValues[n][k]* alpha[k - 1] + beta[k - 1];
            }
        }

        return gridValues;
    }


    public double getT() {
        return T;
    }

    public void setT(double t) {
        T = t;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double z) {
        Z = z;
    }

    public XYSeries getSeries() {
        return series;
    }

    public void setSeries(XYSeries series) {
        this.series = series;
    }

    public XYSeriesCollection getXy() {
        return xy;
    }

    public void setXy(XYSeriesCollection xy) {
        this.xy = xy;
    }
}

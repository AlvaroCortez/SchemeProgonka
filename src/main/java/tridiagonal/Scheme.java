package tridiagonal;

import org.jfree.data.xy.XYSeries;

/**
 * Created by Den on 11.05.16.
 */
public abstract class Scheme {
    public abstract double[][] scheme(double hx, double ht);

    public XYSeries explicitScheme(double ht, double hz, double t, XYSeries xySeries) {
        double[][] scheme = scheme(hz, ht);
        double x = 0;
        int level = (int) (t / ht);
        for (int i = 0; i < scheme[0].length; i++) {
            xySeries.add(x, scheme[level][i]);
            x += hz;
        }
//        xySeriesCollection.addSeries(xySeries);
//        JFreeChart pl = ChartFactory.createXYLineChart("", "x, при t = " + t + ", ht = " + ht + ", hx = " + hx, "u(x)",
//                xySeriesCollection, PlotOrientation.VERTICAL, true, true, false);
//        JFrame jFrame = new JFrame();
//        ChartPanel chartPanel = new ChartPanel(pl);
//        jFrame.add(chartPanel);
//        chartPanel.setVisible(true);
//        jFrame.setVisible(true);
//        jFrame.setSize(800, 600);
//        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return xySeries;
    }
}

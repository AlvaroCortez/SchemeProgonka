import exceptions.MatrixIndexOutOfBoundsException;
import model.SquareMatrix;
import model.VectorMatrix;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Den on 16.05.16.
 */
public class MainWithMatrix extends JFrame {

    double c = 2.99792458e8;
    //N - deltaT
    double deltaT = 1.0 / 40.0 / c;
    double tt = 1;
    //K - deltaZ
    double deltaZ = 1.0 / 20.0;
    double ee = 8.8541878174e-12;
    double u = 1.2566370614e-6;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private ChartPanel chartPanel;
    private JPanel paramsPanel;
    private JLabel schemeLabel;
    private JLabel schemeLabelParamT;
    private JLabel schemeLabelParamK;
    private JLabel schemeLabelParamN;
    private JLabel schemeLabelParamX;
    private JTextField schemeFieldParamT;
    private JTextField schemeFieldParamN;
    private JTextField schemeFieldParamK;
    private JTextField schemeFieldParamX;
    private JButton schemeButton;
    private JButton progonkaButton;
    private JButton ftftButton;
    XYSeries seriesProgonka = new XYSeries("Progonka");
    XYSeries seriesZaidel = new XYSeries("Saidel");
    //private SolutionFirstMethod solutionFirstMethod;

    public void init() {
        //solutionFirstMethod = new SolutionFirstMethod();
        chartPanel = new ChartPanel(null);
        paramsPanel = new JPanel(new MigLayout("", "[left,200]"));
        schemeButton = new JButton("Построить методом Зейделя");
        progonkaButton = new JButton("Построить методом прогонки");
        ftftButton = new JButton("Построить FTDT");
        ftftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveFTDT();
            }
        });
        schemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schemeButtonClicked();
            }
        });
        progonkaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildProgonka();
            }
        });
        schemeLabel = new JLabel("Построение схем");
        schemeLabelParamT = new JLabel("Введите параметр t");
        schemeLabelParamN = new JLabel("Кол-во инт-ов по N");
        schemeLabelParamK = new JLabel("Кол-во инт-ов по K");
        schemeFieldParamK = new JTextField();
        schemeFieldParamK.setPreferredSize(new Dimension(100, 2));
        schemeFieldParamT = new JTextField();
        schemeFieldParamT.setPreferredSize(new Dimension(100, 2));
        schemeFieldParamN = new JTextField();
        schemeFieldParamN.setPreferredSize(new Dimension(100, 2));

        MigLayout mwLayout = new MigLayout("", "[][]", "[top][]");
        getContentPane().setLayout(mwLayout);
        getContentPane().add(chartPanel, "grow 50,push,span");
        paramsPanel.add(schemeLabel, "wrap");
//        paramsPanel.add(schemeLabelParamT, "split");
//        paramsPanel.add(schemeFieldParamT, "cell 1 0 5 1, wrap");
//        paramsPanel.add(schemeLabelParamK, "split 2");
//        paramsPanel.add(schemeFieldParamK, "cell 1 0 5 1, wrap");
//        paramsPanel.add(schemeLabelParamN, "split 2");
//        paramsPanel.add(schemeFieldParamN, "cell 1 0 5 1, wrap");
        paramsPanel.add(schemeButton, "wrap");
        paramsPanel.add(progonkaButton, "wrap");
        paramsPanel.add(ftftButton, "wrap");
        getContentPane().add(paramsPanel, "dock west");
    }

    private void buildProgonka() {

        XYSeriesCollection xy = new XYSeriesCollection();
        xy.addSeries(seriesProgonka);
        VectorMatrix Ex = solve(false);
        try {
            seriesProgonka.clear();
            seriesProgonka = buildSeries(Ex, deltaZ, seriesProgonka);

        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        JFreeChart pl = ChartFactory.createXYLineChart("", "x", "u(x)",
                xy, PlotOrientation.VERTICAL, true, true, false);
        chartPanel.setChart(pl);
    }

    private void schemeButtonClicked() {
        //double deltaZ = 1.0/20.0;
//        double t, ht, hz;
//        try {
//            t = Double.parseDouble(schemeFieldParamT.getText());
//        } catch (NumberFormatException e) {
//            t = 10;
//        }
//        try {
//            hz = solutionFirstMethod.getZ()/Double.parseDouble(schemeFieldParamK.getText());
//        } catch (NumberFormatException e) {
//            hz = 0.01;
//        }
//        try {
//            ht = solutionFirstMethod.getT()/Double.parseDouble(schemeFieldParamN.getText());
//        } catch (NumberFormatException e) {
//            ht = 0.0005;
//        }

        XYSeriesCollection xy = new XYSeriesCollection();
        xy.addSeries(seriesZaidel);
        VectorMatrix Ex = solve(true);
        try {
            seriesZaidel.clear();
            seriesZaidel = buildSeries(Ex, deltaZ, seriesZaidel);

        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        JFreeChart pl = ChartFactory.createXYLineChart("", "x", "u(x)",
                xy, PlotOrientation.VERTICAL, true, true, false);
        chartPanel.setChart(pl);
    }

    public static void main(String[] args) {

        MainWithMatrix frame = new MainWithMatrix();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.init();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static XYSeries buildSeries(VectorMatrix Ex, double hz, XYSeries xySeries) throws MatrixIndexOutOfBoundsException {
        double x = 0;
        for (int i = 0; i < Ex.size(); i++) {
            xySeries.add(x, Ex.getElement(i));
            x += hz;
        }

        return xySeries;
    }

    private VectorMatrix solve(boolean isZaidel) {
        int K = 1000;
        int N = 512;
        int L = 3;
        VectorMatrix Ex = new VectorMatrix(K);
        VectorMatrix Hy = new VectorMatrix(K);
        try {
            SquareMatrix matrix = new SquareMatrix(K);

            double gamma = Math.pow(deltaT / (2 * deltaZ), 2) / (ee * u);
            double gammaU = deltaT / (2 * u * deltaZ);
            double gammaE = deltaT / (2 * ee * deltaZ);

            Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * 0));
            Hy.setElement(0, Ex.getElement(0) * deltaT / (2 * u * deltaT));

            for (int i = 0; i < matrix.getSize(); i++) {
                matrix.setElement(i, i, 1 + 2 * gamma);
                if (i - 1 >= 0) {
                    matrix.setElement(i, i - 1, -gamma);
                }
                if (i + 1 < matrix.getSize()) {
                    matrix.setElement(i, i + 1, -gamma);
                }
            }

            SquareMatrix.setE(0.00000000000001);
            System.out.println("----Исходная матрица----");
            matrix.printMatrix();

            VectorMatrix b = new VectorMatrix(K);

//            for (int k = 1; k < L; k++) {
//                b.setElement(k, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
//            }
//            System.out.println("----Вектор B----");
//            b.printMatrix();
//
//            if (isZaidel) {
//                Ex = matrix.solveZaidel(b);
//            } else {
//                Ex = matrix.progonka(b);
//            }
//
//            System.out.println("----Ответ----");
//            Ex.printMatrix();
//            //System.out.println("----Количество итераций----");
//            //System.out.println(matrix.getCountOfIteration());
//            for (int k = 0; k < K - 1; k++) {
//                Hy.setElement(k, 0 - deltaT / (2 * u * deltaZ) * (Ex.getElement(k + 1) - Ex.getElement(k)));
//            }

            VectorMatrix answer;
            //до этого момента получается что и Ex и Hy равны 0
            for (int t = 1; t < N; t++) {

                Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));
                for (int k = 1; k < K; k++) {
                    Ex.setElement(k, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
                }
                for (int k = 0; k < K - 1; k++) {
                    Hy.setElement(k, Hy.getElement(k) - gammaU * (Ex.getElement(k + 1) - Ex.getElement(k)));
                }
                Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));


                //старое заполнение массива б
//                b.setElement(0, Ex.getElement(0) - gammaE * (Hy.getElement(0)));
//                b.setElement(0, Ex.getElement(0) - gammaE * (Hy.getElement(0)) - Ex.getElement(0));
//                for (int k = 1; k < K; k++) {
//                    b.setElement(k, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
//                }

                //заполняем б для Ех от 1 до К - 1
//                for (int k = 0; k < K - 2; k++) {
//                    b.setElement(k, Ex.getElement(k + 1) - gammaE*(Hy.getElement(k + 1) - Hy.getElement(k)));
//                }
//                b.setElement(0, b.getElement(0) - gamma*Math.sin(2 * Math.PI * c * deltaT * t));

                //альтернативное заполление массива б
                b.setElement(0, Ex.getElement(0) - gammaE * (Hy.getElement(0)));
                for (int k = 1; k < K - 1; k++) {
                    b.setElement(k, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
                }
                b.setElement(K - 1, Ex.getElement(K - 1) - gammaE * ( - Hy.getElement(K - 1)));


                if (isZaidel) {
                    Ex = matrix.solveZaidel(b);
//                    answer = matrix.solveZaidel(b);
//                    Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));
//                    for (int k = 1; k < K - 1; k++) {
//                        Ex.setElement(k, answer.getElement(k - 1));
//                    }
//                    Ex.setElement(K - 1, 0);
                } else {
                    Ex = matrix.progonka(b);
//                    answer = matrix.progonka(b);
//                    Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));
//                    for (int k = 1; k < K - 1; k++) {
//                        Ex.setElement(k, answer.getElement(k - 1));
//                    }
//                    Ex.setElement(K - 1, 0);
                }
                //явная схема
//                Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));
//                for (int k = 1; k < K; k++) {
//                    Ex.setElement(k, Ex.getElement(k) - gammaE*(Hy.getElement(k) - Hy.getElement(k - 1)));
//                }

                Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));
                //Ex.setElement(K - 1, 0);
                for (int k = 0; k < K - 1; k++) {
                    Hy.setElement(k, Hy.getElement(k) - gammaU * (Ex.getElement(k + 1) - Ex.getElement(k)));
                }


                //немного другое решение неявной схемы
//                for (int k = 1; k < K; k++) {
//                    Ex.setElement(k, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
//                }
//                for (int k = 0; k < K - 1; k++) {
//                    Hy.setElement(k, Hy.getElement(k) - gammaU * (Ex.getElement(k + 1) - Ex.getElement(k)));
//                }
//                for (int k = 1; k < K - 1; k++) {
//                    b.setElement(0, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
//                    b.setElement(1, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
//                    b.setElement(2, Ex.getElement(k) - gammaE * (Hy.getElement(k) - Hy.getElement(k - 1)));
//                    if (isZaidel) {
//                        answer = matrix.solveZaidel(b);
//                        Ex.setElement(k - 1, answer.getElement(0));
//                        Ex.setElement(k, answer.getElement(1));
//                        Ex.setElement(k + 1, answer.getElement(2));
//                    } else {
//                        answer = matrix.progonka(b);
//                        Ex.setElement(k - 1, answer.getElement(0));
//                        Ex.setElement(k, answer.getElement(1));
//                        Ex.setElement(k + 1, answer.getElement(2));
//                    }
//                }
//                for (int k = 0; k < K - 1; k++) {
//                    Hy.setElement(k, Hy.getElement(k) - gammaU * (Ex.getElement(k + 1) - Ex.getElement(k)));
//                }
//                Ex.setElement(0, Math.sin(2 * Math.PI * c * deltaT * t));
            }

            System.out.println("----Ответ----");
            Ex.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return Ex;
    }

    private VectorMatrix solveFTDT(){
        int Nz = 1000;
        int Nt = 512;
        VectorMatrix Ex = new VectorMatrix(Nz);
        VectorMatrix Hy = new VectorMatrix(Nz);
        double lv,EPSo,MUo,pi;
        double hz,ht;
        double c1,c4,c5;
        lv=2.99792458e8;
        EPSo=8.8541878174e-12;
        MUo=1.2566370614e-6;
        hz = 1.0/20.0; ht = 1.0/40.0/lv;
        c1=ht/hz/MUo; c4=ht/hz/EPSo; c5=2.0*Math.PI*ht*lv;
        try {
            for (int i = 0; i < Nt; i++) {
                for (int j = 0; j < Nz - 1; j++) {
                    Hy.setElement(j, Hy.getElement(j) - c1 *(Ex.getElement(j + 1) - Ex.getElement(j)));
                }

                for (int j = 1; j < Nz - 1; j++) {
                    Ex.setElement(j , Ex.getElement(j) - c4*(Hy.getElement(j) - Hy.getElement(j - 1)));
                }
                Ex.setElement(0, Math.sin(2 * Math.PI * lv * ht * i));
            }

            Ex.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

        XYSeries series = new XYSeries("MySeries");
        XYSeriesCollection xy = new XYSeriesCollection();
        xy.addSeries(series);
        try {
            series.clear();
            series = buildSeries(Ex, deltaZ, series);

        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        JFreeChart pl = ChartFactory.createXYLineChart("", "x", "u(x)",
                xy, PlotOrientation.VERTICAL, true, true, false);
        chartPanel.setChart(pl);

        return Ex;
    }

}

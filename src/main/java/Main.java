import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import tridiagonal.SolutionFirstMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Den on 11.05.16.
 */
public class Main extends JFrame{

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
    private SolutionFirstMethod solutionFirstMethod;

    public void init(){
        solutionFirstMethod = new SolutionFirstMethod();
        chartPanel = new ChartPanel(null);
        paramsPanel = new JPanel(new MigLayout("", "[left,200]"));
        schemeButton = new JButton("Построить схему");
        schemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schemeButtonClicked();
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
        paramsPanel.add(schemeLabelParamT, "split");
        paramsPanel.add(schemeFieldParamT, "cell 1 0 5 1, wrap");
        paramsPanel.add(schemeLabelParamK, "split 2");
        paramsPanel.add(schemeFieldParamK, "cell 1 0 5 1, wrap");
        paramsPanel.add(schemeLabelParamN, "split 2");
        paramsPanel.add(schemeFieldParamN, "cell 1 0 5 1, wrap");
        paramsPanel.add(schemeButton, "wrap");
        getContentPane().add(paramsPanel, "dock west");
    }

    private void schemeButtonClicked(){
        double t, ht, hz;
        try {
            t = Double.parseDouble(schemeFieldParamT.getText());
        } catch (NumberFormatException e) {
            t = 10;
        }
        try {
            hz = solutionFirstMethod.getZ()/Double.parseDouble(schemeFieldParamK.getText());
        } catch (NumberFormatException e) {
            hz = 0.01;
        }
        try {
            ht = solutionFirstMethod.getT()/Double.parseDouble(schemeFieldParamN.getText());
        } catch (NumberFormatException e) {
            ht = 0.0005;
        }
        XYSeries series = solutionFirstMethod.getSeries();
        series.clear();
        series = solutionFirstMethod.explicitScheme(ht, hz, t, series);
        JFreeChart pl = ChartFactory.createXYLineChart("", "x, hz = " + hz + ", ht = " + ht, "u(x), при z = " + solutionFirstMethod.getZ(),
                solutionFirstMethod.getXy(), PlotOrientation.VERTICAL, true, true, false);
        chartPanel.setChart(pl);
    }

    public static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.init();
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

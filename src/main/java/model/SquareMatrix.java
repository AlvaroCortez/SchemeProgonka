package model;

import exceptions.MatrixIndexOutOfBoundsException;
import exceptions.WrongSizeOfMatrixException;

import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * Created by Den on 15.05.16.
 */
public class SquareMatrix extends Matrix {

    private int N;
    private static double e = 0.01;
    private int countOfIteration = 0;

    public SquareMatrix(int n) {
        super(n, n);
        N = n;
    }

    public double[] zaidel2(Matrix b) throws MatrixIndexOutOfBoundsException {
        if (diagPreobl()) {
            double[] p = new double[N];
            double[] x1 = new double[N];
            for (int i = 0; i < N; i++) {
                x1[i] = 1;
            }
            do {
                for (int i = 0; i < N; i++) {
                    p[i] = x1[i];
                }

                for (int i = 0; i < N; i++) {
                    double var = 0;
                    for (int j = 0; j < i; j++) {
                        var += (getElement(i, j) * x1[j]);
                    }
                    for (int j = i + 1; j < N; j++) {
                        var += (getElement(i, j) * p[j]);
                    }
                    x1[i] = (b.getElement(i,0) - var) / getElement(i, i);
                }
            }
            while (!converge(x1, p));
            return x1;
        }
        else {
            throw new IllegalArgumentException("Метод Гаусса - Зейделя не сходится, нет диагонального преобладания");
        }
    }

    private boolean diagPreobl() throws MatrixIndexOutOfBoundsException {
        for (int i = 0; i < N; i++) {
            double s = 0;
            for (int j = 0; j < N; j++) {
                s+=this.getElement(i,j);
            }
            s-=this.getElement(i,i);
            if (Math.abs(this.getElement(i,i))<=Math.abs(s)) {
                return false;
            }
        }
        return true;
    }

    private boolean converge(double[] xk, double[] xkp) {
        double norm = 0;
        for (int i = 0; i < N; i++)
        {
            norm += (xk[i] - xkp[i])*(xk[i] - xkp[i]);
        }
        if(Math.sqrt(norm) >= e) {
            return false;
        }
        return true;
    }

    public VectorMatrix solveZaidel(VectorMatrix b) throws MatrixIndexOutOfBoundsException {
        VectorMatrix Xnew = new VectorMatrix(N);
        VectorMatrix X = new VectorMatrix(N);
        for(int i = 0; i < N; i++){
            X.setElement(i, 0);
            Xnew.setElement(i, 0);
        }
        while (true){
            for (int i = 0; i < N; i++) {
                double var = b.getElement(i);
                for (int j = 0; j < i; j++) {
                    var -= (getElement(i, j) * Xnew.getElement(j));
                }
                for (int j = i + 1; j < N; j++) {
                    var -= (getElement(i, j) * X.getElement(j));
                }
                Xnew.setElement(i, var / getElement(i, i));
            }
            countOfIteration++;
            if(converged(Xnew, X)){
                break;
            }
            X = Xnew.getClone();
        }
        //while (!converged(Xnew, X));
        return X;
    }

    private boolean converged(VectorMatrix Xnew, VectorMatrix X) throws MatrixIndexOutOfBoundsException {
        double norm = 0;
        for (int i = 0; i < N; i++)
        {
            //norm += (Xnew.getElement(i) - X.getElement(i))*(Xnew.getElement(i) - X.getElement(i));
            if(Math.abs(Xnew.getElement(i) - X.getElement(i)) > e){
                return false;
            }
        }
//        if(Math.sqrt(norm) >= e) {
//            return false;
//        }
        return true;
    }

    public static SquareMatrix getMatrix(String matr) throws IOException, MatrixIndexOutOfBoundsException, WrongSizeOfMatrixException {
        /*BufferedReader bufferedReader = new BufferedReader(new FileReader(mat));
        double matrix[][] = null;
        String[] line = bufferedReader.readLine().split("\t");
        int rows = Integer.parseInt(line[0]);
        int columns = Integer.parseInt(line[1]);
        matrix = new double[rows][columns];
        String s[][] = new String[rows][columns];
        for(int i = 0;i<rows;i++){
            s[i] = bufferedReader.readLine().split("\t");
            for(int j = 0;j<columns;j++){
                matrix[i][j] = Integer.parseInt(s[i][j]);
            }
        }
        return matrix;*/
        FileReader fileReader = new FileReader(matr);
        StreamTokenizer sin = new StreamTokenizer(fileReader);
        if ((sin.nextToken() != StreamTokenizer.TT_NUMBER) || (sin.nval == 0)) {
            throw new WrongSizeOfMatrixException();
        }
        int a = (int) sin.nval;
//        if ((sin.nextToken() != StreamTokenizer.TT_NUMBER) || (sin.nval == 0)) {
//            throw new WrongSizeOfMatrixException();
//        }
//        int b = (int) sin.nval;
        SquareMatrix mat = new SquareMatrix(a);
        int i = 0, j = 0;
        while (i < a) {
            if (sin.nextToken() != StreamTokenizer.TT_NUMBER) {
                throw new WrongSizeOfMatrixException();
            }
            mat.setElement(i,j, sin.nval);
            j++;
            if (j == a) {
                i++;
                j = 0;
            }
        }
        return mat;
    }

    public int getCountOfIteration() {
        return countOfIteration;
    }

    public void setCountOfIteration(int countOfIteration) {
        this.countOfIteration = countOfIteration;
    }

    public static double getE() {
        return e;
    }

    public static void setE(double e) {
        SquareMatrix.e = e;
    }
}

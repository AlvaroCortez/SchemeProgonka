import exceptions.MatrixIndexOutOfBoundsException;
import model.SquareMatrix;
import model.VectorMatrix;

/**
 * Created by Den on 16.05.16.
 */
public class MainWithMatrix {
    public static void main(String[] args) {
        int Z = 3;
        int N = 3;
        SquareMatrix matrix = new SquareMatrix(Z);
        double deltaT = 0.1;
        double t = 1;
        double deltaZ = 0.1;
        double ee = 1;
        double u = 1;
        double gamma = Math.pow(deltaT/(2 * deltaZ), 2) / (ee * u);
        double[][] Ex = new double[Z][N];
        double[][] Hy = new double[Z][N];
        Ex[0][0] = Math.sin(2* Math.PI*300000000*deltaT*t);
        Hy[0][0] = Ex[0][0]*deltaT/(2*u*deltaZ);
//        try {
//            matrix = SquareMatrix.getMatrix("matrix.txt");
//        } catch (IOException | MatrixIndexOutOfBoundsException | WrongSizeOfMatrixException e) {
//            System.out.println(e.getMessage());
//        }

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                try {
//                    matrix.setElement(i, j, Math.random() + 10);
//                } catch (MatrixIndexOutOfBoundsException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
        for (int i = 0; i < Z; i++) {
            try {
                matrix.setElement(i, i, 1 + 2* gamma);
                if(i - 1 >= 0){
                    matrix.setElement(i, i - 1, -gamma);
                }
                if(i + 1 < Z){
                    matrix.setElement(i, i + 1, -gamma);
                }
            } catch (MatrixIndexOutOfBoundsException e1) {
                System.out.println(e1.getMessage() + ", Это был элемент (" + i + ")");
            }
        }

        SquareMatrix.setE(0.00000000001);
        System.out.println("----Исходная матрица----");
        try {
            matrix.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        VectorMatrix b = new VectorMatrix(Z);
//        try {
//            b.setElement(0, 7);
//            b.setElement(1, 4);
//            b.setElement(2, 6);
//        } catch (MatrixIndexOutOfBoundsException e) {
//            System.out.println(e.getMessage());
//        }
        for (int i = 0; i < Z; i++) {

        }
        for (int i = 0; i < Z; i++) {
            //b.setElement(i, );
        }
        System.out.println("----Вектор B----");
        try {
            b.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        VectorMatrix answer = new VectorMatrix(0);
        try {
             answer = matrix.solveZaidel(b);
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("----Ответ----");
        try {
            answer.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("----Количество итераций----");
        System.out.println(matrix.getCountOfIteration());
    }

}

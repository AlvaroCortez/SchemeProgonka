import exceptions.MatrixIndexOutOfBoundsException;
import exceptions.WrongSizeOfMatrixException;
import model.SquareMatrix;
import model.VectorMatrix;

import java.io.IOException;

/**
 * Created by anka_47 on 17.05.16.
 */
public class MainProg {
    public static void main(String[] args) {
        int N = 3;
        SquareMatrix matrix = new SquareMatrix(N);
        try {
            matrix = SquareMatrix.getMatrix("matrix.txt");
        } catch (IOException | MatrixIndexOutOfBoundsException | WrongSizeOfMatrixException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("----Исходная матрица----");
        try {
            matrix.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        VectorMatrix b = new VectorMatrix(N);
        try {
            b.setElement(0, 7);
            b.setElement(1, 4);
            b.setElement(2, 6);
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("----Вектор B----");
        try {
            b.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        VectorMatrix answer = new VectorMatrix(0);
        try {
            answer = matrix.progonka(b);
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("----Ответ----");
        try {
            answer.printMatrix();
        } catch (MatrixIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}

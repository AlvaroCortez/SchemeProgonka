package model;

import exceptions.MatrixIndexOutOfBoundsException;

/**
 * Created by Den on 15.05.16.
 */
public class Matrix {

    private int rows;
    private int columns;
    private double[][] matrix;

    public Matrix(int n, int m){
        rows = n;
        columns = m;
        matrix = new double[n][m];
    }

    public int getRows(){
        return rows;
    }

    public int getColumns(){
        return columns;
    }

    public double getElement(int n, int m) throws MatrixIndexOutOfBoundsException {
        if (n >= this.getRows() || m >= this.getColumns()){
            throw new MatrixIndexOutOfBoundsException("Такого элемента не существует");
        }
        return matrix[n][m];
    }

    public void setElement(int n, int m, double value) throws MatrixIndexOutOfBoundsException {
        if (n >= this.getRows() || m >= this.getColumns()){
            throw new MatrixIndexOutOfBoundsException("Такого элемента не существует");
        }
        matrix[n][m] = value;
    }

    public void printMatrix() throws MatrixIndexOutOfBoundsException {
        for(int i = 0;i<getRows();i++){
            for (int j = 0; j<getColumns();j++){
                System.out.print(getElement(i, j) + " ");
            }
            System.out.println();
        }
    }

}

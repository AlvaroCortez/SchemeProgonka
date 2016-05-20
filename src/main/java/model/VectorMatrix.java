package model;

import exceptions.MatrixIndexOutOfBoundsException;

/**
 * Created by Den on 15.05.16.
 */
public class VectorMatrix extends Matrix {

    private int N;

    public VectorMatrix(int n) {
        super(n, 1);
        N = n;
    }

    public double getElement(int n) throws MatrixIndexOutOfBoundsException {
        return super.getElement(n, 0);
    }

    public void setElement(int n, double value) throws MatrixIndexOutOfBoundsException {
        super.setElement(n, 0, value);
    }

    protected VectorMatrix getClone() throws MatrixIndexOutOfBoundsException {
        VectorMatrix matrix = new VectorMatrix(N);
        for(int i = 0; i < N; i++){
            matrix.setElement(i, this.getElement(i));
        }
        return matrix;
    }

    public int size() {
        return N;
    }
}

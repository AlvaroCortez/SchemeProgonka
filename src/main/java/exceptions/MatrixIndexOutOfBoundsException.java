package exceptions;

/**
 * Created by Den on 08/09/15.
 */
public class MatrixIndexOutOfBoundsException extends Exception{
    public MatrixIndexOutOfBoundsException(){

    }

    public MatrixIndexOutOfBoundsException(String msg){
        super(msg);
    }
}

package exceptions;

/**
 * Created by Den on 08/09/15.
 */
public class IncompatibleSizeOfMatrixException extends Exception {
    public IncompatibleSizeOfMatrixException(){

    }

    public IncompatibleSizeOfMatrixException(String msg){
        super(msg);
    }
}

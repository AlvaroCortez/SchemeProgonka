package exceptions;

/**
 * Created by Den on 08/09/15.
 */
public class WrongSizeOfMatrixException extends Exception {
    public WrongSizeOfMatrixException(){

    }

    public WrongSizeOfMatrixException(String msg){
        super(msg);
    }
}

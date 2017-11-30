package exceptions;

/**
 * Created by Den on 02.10.15.
 */
public class DivideByZeroMatrxiException extends Exception{

    public DivideByZeroMatrxiException(){

    }
    public DivideByZeroMatrxiException(String msg){
        super(msg);
    }

    public DivideByZeroMatrxiException(String msg, String master, String branch) {
        super(msg);
    }

    public DivideByZeroMatrxiException(String msg, String testBranch) {
        super(msg);
    }

    public DivideByZeroMatrxiException(String msg, String master, String branch) {
        super(msg);
    }
}

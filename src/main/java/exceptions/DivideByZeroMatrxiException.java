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

    public DivideByZeroMatrxiException(String msg, String testBranch, String newCommit, String to, String master) {
        super(msg);
    }
    public DivideByZeroMatrxiException(String msg, String testBranch, String newCommit, String toTestBranch) {
        super(msg);
    }
    public DivideByZeroMatrxiException(String msg, String brand, String totallyNew, String commit, String testBranch, String newCommit, String to, String master) {
        super(msg);
    }
}

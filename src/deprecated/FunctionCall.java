package deprecated;

public class FunctionCall extends Instruction {
    String called;
    Instruction argument;
    private FunctionCall(final String s,final Instruction n){
        type = Type.FUNCTION_CALL;
        called = s;
        argument = n;
    }
    public static FunctionCall init(final String s, final Instruction n){
        return new FunctionCall(s,n);
    }
    @Override
    public String toString(){
        return type +"( called: " + called + ", argument: " + argument + " )";
    }
}

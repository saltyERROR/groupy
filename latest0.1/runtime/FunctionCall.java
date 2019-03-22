package runtime;

public class FunctionCall extends Node{
    String called;
    Node argument;
    private FunctionCall(final String s,final Node n){
        type = Type.FUNCTION_CALL;
        called = s;
        argument = n;
    }
    public static FunctionCall init(final String s, final Node n){
        return new FunctionCall(s,n);
    }
    @Override
    public String toString(){
        return type +"( called: " + called + ", argument: " + argument + " )";
    }
}

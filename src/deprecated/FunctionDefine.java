package deprecated;

import java.util.function.Consumer;

public class FunctionDefine extends Instruction {
    String defined;
    Consumer value;
    private FunctionDefine(final String s,final Consumer c){
        type = Type.FUNCTION_DEFINE;
        defined = s;
        value = c;
    }
    public static FunctionDefine init(final String s,final Consumer c){
        return new FunctionDefine(s,c);
    }
}

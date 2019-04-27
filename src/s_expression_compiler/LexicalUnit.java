package s_expression_compiler;

public class LexicalUnit {
    LexicalUnit(final Token t, final String s){
        token = t;
        value = s;
    }
    final Token token;
    final String value;
    @Override
    public String toString(){
        return token + "  " + value;
    }
}

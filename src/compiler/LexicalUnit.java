package compiler;

public class LexicalUnit {
    Token token;
    String value;
    LexicalUnit(final Token t,final String s){
        token = t;
        value = s;
    }
    @Override
    public String toString(){
        return token + "  " + value;
    }
}

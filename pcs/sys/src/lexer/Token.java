package lexer;

public class Token {
    protected Token(final Type type, final String value){
        this.type = type;
        this.value = value;
    }
    public Type type;
    public  String value;
    @Override
    public String toString(){ return type + " \""+value + "\"";
    }
}

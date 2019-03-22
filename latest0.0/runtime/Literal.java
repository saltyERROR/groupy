package runtime;

public class Literal extends Node{
    Float value;
    private Literal(final Float f){
        super();
        type = Type.LITERAL;
        value = f;
    }
    public static Node init(final String s){
        return new Literal(Float.valueOf(s));
    }
    public static Node init(final Float f){
        return new Literal(Float.valueOf(f));
    }
}

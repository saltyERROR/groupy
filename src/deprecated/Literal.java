package deprecated;

public class Literal extends Instruction {
    Float value;
    private Literal(final Float f){
        super();
        type = Type.LITERAL;
        value = f;
    }
    public static Instruction init(final String s){
        return new Literal(Float.valueOf(s));
    }
    public static Instruction init(final Float f){
        return new Literal(f);
    }
}

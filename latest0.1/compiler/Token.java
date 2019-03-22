package compiler;

public enum Token{
    CALL(true,"[a-zA-Z_]*"),
    NUMBER(true,"([1-9][0-9]*)|0|(([1-9][0-9]*)|0)(\\.[0-9][0-9]*)?"),
    LITERAL(true,"^\"[^\"]*\"?"),
    FUNCTION(false,"function"),
    MEMBER(false,"member"),
    END(false,"end"),
    ADD(false,"+"),
    SUBTRACT(false,"-"),
    MULTIPLY(false,"*"),
    DIVIDE(false,"/"),
    LEFT_BRACKET(false,"("),
    RIGHT_BRACKET(false,")"),
    LINE_NEXT(false,"\n");
    private final boolean isRegex;
    private final String pattern;
    Token(final boolean b,final String s){
        isRegex = b;
        pattern = s;
    }
    public boolean matches(final String s) {
        if (isRegex) return s.matches(pattern);
        return s.equals(pattern);
    }
}

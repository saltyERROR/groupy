package compiler;

public enum Token {
    // String
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    EQUAL("="),
    LEFT_BRACKET("("),
    RIGHT_BRACKET(")"),
    LINE_NEXT("\n"),

    // RegExp
    FUNCTION("[a-zA-Z_]+"),
    // todo float value
    NUMBER("([1-9][0-9]*)|0"),
    LITERAL("\"[^\"]*\"?"),
    WHITE("[\\s]");
    final String pattern;
    Token(final String s) {
        pattern = s;
    }
}

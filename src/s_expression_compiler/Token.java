package s_expression_compiler;

public enum Token {
    // String
    // TRUE("true"),
    // FALSE("false"),
    END(";"),
    ADD("+"),
    // SUBTRACT("-"),
    MULTIPLY("*"),
    // DIVIDE("/"),
    // EQUAL("="),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    // LEFT_BRACKET("["),
    // RIGHT_BRACKET("]"),
    LINE_NEXT("\n"),
    // RegExp
    FUNCTION("[a-zA-Z_]+"),
    VARIABLE("\\$[a-zA-Z_]*"),
    // todo float value
    NUMBER("([1-9][0-9]*)|0"),
    LITERAL("\"[^\"]*\"?"),
    WHITE("[\\s]"),
    // only System
    REFERENCE("");
    Token(final String s) {
        pattern = s;
    }
    final String pattern;
}

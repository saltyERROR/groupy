package s_expression_compiler;

public enum ParserStatus {
    accepted,
    rejected,
    handling_error,
    reducing,
    shifting,
    waiting_input,
    nil;
    // increment/decrement parser.status
    public void increment(){}
    public void decrement(){}
}

package compiler;

import java.util.Arrays;
import java.util.LinkedList;

import system.Error;
import runtime.FunctionCall;
import runtime.Literal;
import runtime.Node;

public class Parser {
    LinkedList<Node> result;
    public Parser() {
        result = new LinkedList<>();
    }
    public LinkedList<Node> parse(final String s){
        result.clear();
        input(s.split("[\\s]"));
        return result;
    }
    private void input(final String[] s) {
        if (Token.END.matches(s[s.length - 1])) {
            node(Arrays.copyOfRange(s, 0, s.length - 1));
        } else {
            Error.exit("lexical error in input()");
        }
    }

    private void node(final String[] s) {
        //LEFT_BRACKET expression() RIGHT_BRACKET
        if (Token.LEFT_BRACKET.matches(s[0])
                && Token.RIGHT_BRACKET.matches(s[s.length - 1])) {
            expression(Arrays.copyOfRange(s, 1, s.length - 1));
        } else {
            Error.exit("lexical error in node()");
        }
    }

    private void expression(final String[] s) {
        //CALL argument()
        if (Token.CALL.matches(s[0])) {
            result.push(FunctionCall.init(s[0],
                    arguments(Arrays.copyOfRange(s, 1, s.length))));
        } else {
            Error.exit("lexical error in expression()");
        }
    }
    //todo
    private Node arguments(final String[] s) {
        //NUMBER
        if (s.length == 1) {
            if (Token.NUMBER.matches(s[0])) {
                return Literal.init(s[0]);
            }
            Error.exit("lexical error in arguments()");
            //arguments() NUMBER
        } else if (Token.NUMBER.matches(s[s.length - 1])) {
            Literal.init(s[s.length - 1]);
            arguments(Arrays.copyOfRange(s, 0, s.length - 2));
        } else {
            Error.exit("lexical error in arguments()");
        }
        return null;
    }
}
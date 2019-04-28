/*
summary{
    LL(1): Left-to-right Leftmost derivation (1 lookahead letter)
}
*/

package s_expression_compiler;

import server.ServerProtocol;
import system.*;
import system.Error;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import static java.lang.Integer.*;

public class LLParser implements Parser {
    public LLParser() {
        this.variable_map = new HashMap<>();
        this.required = new ArrayList<>();
        this.required.add(Token.LEFT_PARENTHESIS);
        this.required.add(Token.END);
        this.server = new ServerProtocol();
        this.function_stack = new Stack<>();
        this.status = ParserStatus.nil;
    }
    private final Map<String,Integer> variable_map;
    private final List<Token> required;
    private final ServerProtocol server;
    private final Stack<LexicalUnit> function_stack;
    private ParserStatus status;

    // lexical analyzer
    private LexicalUnit analyze(final String s){
        boolean matches;
        for (Token token : Token.values()) {
            try {
                // RegExp todo implement LINE_NEXT("\n")
                Matcher matcher = Pattern.compile("(" + token.pattern + ").*").matcher(s);
                matches = matcher.matches();
                if(matches) return new LexicalUnit(token,matcher.group(1));
            } catch (IllegalStateException | PatternSyntaxException e) {
                // String
                matches =  s.startsWith(token.pattern);
                if(matches) return new LexicalUnit(token,token.pattern);
            }
        }
        Error.exit(Error.LEXICAL,"\"" + s + "\" is not a token");
        return null;
    }
    @Override // parser
    public String use(String input) {
        LexicalUnit[] analyzed = new LexicalUnit[128];
        int i = 0;
        while (!input.isEmpty()) {
            LexicalUnit unit = analyze(input);
            input = input.substring(unit.value.length());
            if (unit.token != Token.WHITE) {
                analyzed[i++] = unit;
            }
        }
        return parse(Arrays.copyOfRange(analyzed, 0, i));
    }
    private String parse(final LexicalUnit[] analyzed){
        final StringBuilder builder = new StringBuilder();
        int variables = 0;
        for (LexicalUnit unit : analyzed) {
            status = ParserStatus.waiting_input;
            if (required.contains(unit.token)) {
                switch (unit.token) {
                    case LEFT_PARENTHESIS:
                        require(new Token[]{Token.ADD,Token.MULTIPLY,Token.FUNCTION});
                        break;
                    case ADD:
                    case MULTIPLY:
                    case FUNCTION:
                        function_stack.push(unit);
                        require(new Token[]{Token.NUMBER,Token.LITERAL,Token.LEFT_PARENTHESIS,Token.RIGHT_PARENTHESIS});
                        break;
                    case NUMBER:
                        builder.append(normalize(toHexString(parseInt(unit.value)),6))
                               .append("10\n");
                        require(new Token[]{Token.NUMBER,Token.LITERAL,Token.LEFT_PARENTHESIS,Token.RIGHT_PARENTHESIS});
                        break;
                    case LITERAL:
                        if (variable_map.containsKey(unit.value)) {
                            builder.append(normalize(toHexString(variable_map.get(unit.value)),6))
                                    .append("10\n");
                        } else {
                            variable_map.put(unit.value,variable_map.size());
                            builder.append(normalize(toHexString(variables++), 6))
                                    .append("10\n");
                        }
                        break;
                    case RIGHT_PARENTHESIS:
                        switch (function_stack.pop().value) {
                            case "+":
                                builder.append("00000011\n");
                                break;
                            case "*":
                                builder.append("00000012\n");
                                break;
                            case "print":
                                builder.append("00000013\n");
                                break;
                            case "max":
                                builder.append("00000014\n");
                                break;
                            case "min":
                                builder.append("00000015\n");
                                break;
                            case "let":
                                builder.append("00000016\n");
                                break;
                            case "call":
                                builder.append("00000017\n");
                                break;
                        }
                        if (function_stack.isEmpty()) require(new Token[]{Token.LEFT_PARENTHESIS,Token.END});
                        else require(new Token[]{Token.NUMBER,Token.LITERAL,Token.RIGHT_PARENTHESIS});
                        break;
                    case END:
                        status = ParserStatus.accepted;
                        return builder.toString();
                    default:
                        Error.exit(Error.SYNTAX,"illegal syntax");
                }
            }
        }
        Error.exit(Error.LEXICAL,"expected \";\"");
        return null;
    }
    private Queue<LexicalUnit> arguments(final LexicalUnit[] input) {
        Queue<LexicalUnit> q = new ArrayDeque<>();
        for (LexicalUnit unit : input) {
            switch (unit.token) {
                case NUMBER:
                case LITERAL:
                case VARIABLE:
                    q.add(unit);
                    break;
                case FUNCTION:
                    if (variable_map.containsKey(unit.value)) {
                        q.add(new LexicalUnit(Token.REFERENCE,unit.value));
                    } else {
                        Error.exit(Error.NOT_FOUND,"function, " + unit.value + " is not found");
                    }
                    break;
                case LEFT_PARENTHESIS:
                    // parse(Arrays.copyOfRange(input,i,input.length));
                    break;
                case RIGHT_PARENTHESIS:
                    return q;
                default:
                    Error.exit(Error.SYNTAX,"illegal arguments");
            }
        }
        return null;
    }
    private String normalize(final String original,final int offset){
        final int length = original.length();
        final StringBuilder builder = new StringBuilder(offset - length);
        if (length > offset) {
            Error.exit(Error.VM, "illegal address");
        }
        for (int k = 0; k < offset - length; k++) {
            builder.append('0');
        }
        return builder.append(original).toString();
    }
    private String[] split(String input){
        String[] result = new String[input.length()/3 + 1];
        int i = 0;
        while (input.length() >= 3){
            result[i++] = input.substring(0,3);
            input = input.substring(3);
        }
        if (!input.isEmpty()) {
            result[i++] = input;
        }
        return Arrays.copyOfRange(result,0,i);
    }
    private void require(Token[] tokens){
        required.clear();
        required.addAll(Arrays.asList(tokens));
    }
}

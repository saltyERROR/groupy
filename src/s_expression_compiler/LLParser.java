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
        this.variables = new HashMap<>();
        this.required = new ArrayList<>();
        this.required.add(Token.LEFT_BRACKET);
        this.required.add(Token.END);
        this.server = new ServerProtocol();
        this.status = ParserStatus.nil;
    }
    private Map<String,Integer> variables;
    private List<Token> required;
    private final ServerProtocol server;
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
        String result = "";
        int locals = 0;
        int starting_address;
        for (int j = 0; j < analyzed.length; j++){
            status = ParserStatus.waiting_input;
            LexicalUnit unit = analyzed[j];
            if (required.contains(unit.token)) {
                switch (unit.token){
                    case LEFT_BRACKET:
                        require(new Token[]{Token.FUNCTION});
                        break;
                    case FUNCTION:
                        Queue<LexicalUnit> q = arguments(Arrays.copyOfRange(analyzed,j + 1,analyzed.length));
                        switch (unit.value){
                            case "print":
                                if (q.size() == 1){
                                    LexicalUnit peek = q.poll();
                                    switch (peek.token){
                                        case LITERAL:
                                            String printed = peek.value.substring(1, peek.value.length() - 1);
                                            // set address TODO remove
                                            starting_address = locals;
                                            result += normalize(toHexString(starting_address),6) + "03\n";
                                            // put
                                            result += normalize(toHexString(printed.length()),2)
                                                    + normalize(toHexString(starting_address + 1),3) + "321\n";
                                            // put byte
                                            String[] strings = split(printed,3);
                                            String argument;
                                            for (int k = 0; k < strings.length; k++) {
                                                argument = "";
                                                for (Character c : strings[k].toCharArray()) {
                                                    argument = toHexString(c) + argument;
                                                    locals++;
                                                }
                                                result += normalize(argument, 6) + "20\n";
                                            }
                                            // print get
                                            result += normalize(toHexString(starting_address),6) + "02\n";
                                            break;
                                        case NUMBER:
                                            // set address TODO remove
                                            starting_address = locals;
                                            result += normalize(toHexString(starting_address),6) + "03\n";
                                            // put
                                            result += normalize(toHexString(parseInt(peek.value)),5) + "021\n";
                                            locals++;
                                            // print get
                                            result += normalize(toHexString(starting_address),6) + "02\n";
                                            break;
                                        case REFERENCE:
                                            // call reference
                                            result += normalize(toHexString(variables.get(peek.value)),6) + "31\n";
                                            break;
                                        default:
                                            Error.exit(Error.SYNTAX,"illegal typed arguments");
                                    }
                                } else {
                                    Error.exit(Error.SYNTAX,"too many arguments");
                                }
                                break;
                            case "let":
                                LexicalUnit peek = q.poll();
                                final String variable_name = peek.value.substring(1);
                                if (variables.containsKey(variable_name)) {
                                    // set address TODO remove
                                    starting_address = variables.get(variable_name);
                                    result += normalize(toHexString(starting_address), 6) + "03\n";
                                } else {
                                    // set address TODO remove
                                    starting_address = locals;
                                    result += normalize(toHexString(starting_address), 6) + "03\n";
                                }
                                // put
                                variables.put(variable_name, starting_address);
                                peek = q.poll();
                                switch (peek.token) {
                                    case NUMBER:
                                        result += normalize(toHexString(parseInt(peek.value)), 6) + "30\n";
                                        break;
                                    case REFERENCE:
                                        result += normalize(toHexString(variables.get(peek.value)), 6) + "32\n";
                                        break;
                                }
                                locals++;
                                break;
                            case "array": // TODO implement node which has value to return
                                // set address TODO remove
                                starting_address = locals;
                                result += normalize(toHexString(starting_address), 6) + "03\n";
                                // put
                                result += normalize(toHexString(q.size()), 2)
                                        + normalize(toHexString(starting_address + 1), 3) + "121\n";
                                for (LexicalUnit argument : q) {
                                    switch (argument.token) {
                                        case NUMBER:
                                            // put
                                            result += normalize(toHexString(parseInt(argument.value)),6) + "21\n";
                                            locals++;
                                            break;
                                        default:
                                            Error.exit(Error.SYNTAX,"illegal typed arguments");
                                    }
                                }
                                break;
                            default:
                                status = ParserStatus.handling_error;
                                server.handle(status);
                                status = ParserStatus.rejected;
                                Error.exit(Error.NOT_FOUND, "function, " + unit.value + " is not found");
                        }
                        require(new Token[]{Token.LINE_NEXT,Token.LEFT_BRACKET,Token.END});
                        break;
                    case LINE_NEXT:
                        require(new Token[]{Token.LEFT_BRACKET});
                        break;
                    case END:
                        status = ParserStatus.accepted;
                        return result;
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
        for (int i = 0; i <  input.length; i++) {
            switch (input[i].token) {
                case NUMBER:
                case LITERAL:
                case VARIABLE:
                    q.add(input[i]);
                    break;
                case FUNCTION:
                    if (variables.containsKey(input[i].value)) {
                        q.add(new LexicalUnit(Token.REFERENCE,input[i].value));
                    } else {
                        Error.exit(Error.NOT_FOUND,"function, " + input[i].value + " is not found");
                    }
                    break;
                case LEFT_BRACKET:
                    // parse(Arrays.copyOfRange(input,i,input.length));
                    break;
                case RIGHT_BRACKET:
                    return q;
                default:
                    Error.exit(Error.SYNTAX,"illegal arguments");
            }
        }
        return null;
    }
    private String normalize(String original,final int offset){
        final int length = original.length();
        if (length > offset) {
            Error.exit(Error.VM, "illegal address");
        }
        for (int k = 0; k < offset - length; k++) {
            original = "0" + original;
        }
        return original;
    }
    private String[] split(String input,final int offset){
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

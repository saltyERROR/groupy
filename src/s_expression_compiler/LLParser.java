/*
summary{
    todo implement
    LL(1): Left-to-right Leftmost derivation (1 lookahead letter)
}
*/

package s_expression_compiler;

import system.*;
import system.Error;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LLParser implements Parser {
    public LLParser() {}
    // lexical analyzer
    private LexicalUnit analyze(final String s){
        boolean matches;
        for (Token token : Token.values()) {
            try {
                // RegExp
                Matcher matcher = Pattern.compile("(" + token.pattern + ").*").matcher(s);
                matches = matcher.matches();
                String first = matcher.group(1);
                if(matches) return new LexicalUnit(token,first);
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
    public void parse(String file_name) throws IOException {
        String input = Eam.use(file_name,(Reader<Eam, char[], IOException>) eam -> eam.read());
        LexicalUnit[] analyzed = new LexicalUnit[128];
        int i = 0;
        while (!input.isEmpty()) {
            LexicalUnit unit = analyze(input);
            input = input.substring(unit.value.length());
            if (unit.token != Token.WHITE){
                System.out.println(unit);
                analyzed[i++] = unit;
            }
        }

        analyzed = Arrays.copyOfRange(analyzed,0,i);
        Token required = Token.LEFT_BRACKET;
        for (int j = 0; j < analyzed.length; j++){
            LexicalUnit unit = analyzed[j];
            if (unit.token == required) {
                switch (unit.token){
                    case LEFT_BRACKET:
                        required = Token.FUNCTION;
                        break;
                    case FUNCTION:
                        Queue<Integer> q =
                                arguments(Arrays.copyOfRange(analyzed,j + 1,analyzed.length));
                        // todo writer doesn't work
                        Eam.use("Instructions.txt",
                                (Writer<Eam, IOException>) eam -> eam.write("0000102100000002"));
                        return;
                    default:
                        System.out.println("error input");
                }
            }
        }
    }
    private Queue<Integer> arguments(final LexicalUnit[] input) {
        Queue<Integer> q = new PriorityQueue<>();
        for (LexicalUnit unit : input){
            if (unit.token == Token.NUMBER) {
                q.add(Integer.parseInt(unit.value));
            } else if (unit.token == Token.RIGHT_BRACKET) {
                return q;
            } else {
                System.out.println("error arguments");
            }
        }
        return null;
    }
    @Override
    public void send(){
        // todo implement
    }
}

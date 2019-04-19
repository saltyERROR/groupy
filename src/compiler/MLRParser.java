/*
summary{
    todo implement
    LR(1): Left-to-right Rightmost derivation (1 lookahead letter)
    -> MLR(1): Merged LR(1)

    Source Code => AST: Abstract Syntax Tree
}
*/

package compiler;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import system.Error;
import deprecated.Instruction;
import system.Parser;

public class MLRParser implements Parser {
    private LinkedList<Instruction> result;

    public MLRParser() {
        result = new LinkedList<>();
    }

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
    public void /* LinkedList<Instruction> */ parse(String s){
        LexicalUnit[] analyzed = new LexicalUnit[128];
        int i = 0;
        while (!s.isEmpty()) {
            LexicalUnit unit = analyze(s);
            s = s.substring(unit.value.length());
            if (unit.token != Token.WHITE){
                System.out.println(unit);
                analyzed[i++] = unit;
            }
        }
        System.out.println();
        result.clear();
        //input(Arrays.copyOfRange(analyzed,0,i));
        return /* result */;
    }
    @Override
    public void send(){

    }
}
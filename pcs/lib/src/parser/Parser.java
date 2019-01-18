package parser;

import lexer.Token;
import lexer.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser{
    private static List<String> types = Arrays.asList("int","string");
    private static List<String> variables = Arrays.asList("ans1");
    private static List<String> functions = Arrays.asList("disp","log");

    public static String parse(final Token t){
        if(t.type == Type.variable) {
            if (types.contains(t.value)) return "type";
            else if(variables.contains(t.value)) return "variable";
            else if(functions.contains(t.value)) return "function";
        }else if(t.type == Type.digit){
            return "digit";
        }else if(t.type == Type.operator){
            return "operator";
        }
        return "other";
    }
    public static List<String> use(List<Token> list){
        System.out.println("\n--parse()");
        return list.stream()
                   .map(Parser::parse)
                   .collect(Collectors.toList());
    }
}
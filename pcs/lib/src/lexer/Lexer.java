package lexer;

import java.util.*;
import java.util.stream.Collectors;

public class Lexer {
    private static Stack<Token> stack = new Stack<>();
    private Lexer(){}

    private static List<Character> brackets = Arrays.asList('(',')','{','}','[',']');
    private static List<Character> operators = Arrays.asList('=','+','-','*','/');

    private static Type getType(final Character c){
        if(Character.isLetter(c)) return Type.variable;
        else if(Character.isDigit(c)) return Type.digit;
        else if(Character.isWhitespace(c)) return Type.space;
        else if(operators.contains(c)) return Type.operator;
        else if(brackets.contains(c)) return Type.bracket;
        return Type.other;
    }
    private static List<Character> toList(final char[] chars){
        List<Character> list = new ArrayList<>();
        for(Character aChar : chars){
            list.add(aChar);
        }
        return list;
    }
    private static Token analyze(final Character aChar){
        final Type cType = getType(aChar);
        if(stack.empty()){
            if(cType == Type.variable) {
                stack.push(new Token(cType, Character.toString(aChar)));
            }else{
                System.out.println("Cannot start with \"" + aChar +"\"");
                System.exit(0);
            }
        }else{
            final Token prev = stack.pop();
            final String pValue = prev.value;
            final Type pType = prev.type;
            if(cType == pType){
                stack.push(new Token(pType,pValue + aChar));
            }
            else if(pType == Type.variable && cType == Type.digit) {
                stack.push(new Token(Type.variable, pValue + aChar));
            }else if(pType == Type.digit && cType == Type.operator){
                stack.push(new Token(Type.function,pValue + aChar));
            }else{
                Token t = new Token(pType,pValue);
                stack.push(t);
                stack.push(new Token(cType,Character.toString(aChar)));
                return t;
            }
        }
        return null;
    }
    public static List<Token> use(final char[] chars){
        System.out.println("\n--analyze()");
        return toList(chars).stream()
                            .map(Lexer::analyze)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
    }
}
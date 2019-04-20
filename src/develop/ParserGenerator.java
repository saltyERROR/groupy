/*
summary{
    todo implement
    LR(1): Left-to-right Rightmost derivation (1 lookahead letter)
    -> MLR(1): Merged LR(1)

    Source Code => AST: Abstract Syntax Tree
}
*/

package develop;

import compiler.MLRParser;
import io.Eam;
import io.Reader;

import java.io.IOException;
import java.util.*;

public class ParserGenerator {
    private Map<NonTerminal,Expression[]> grammar_map;
    private List<Expression> resolvable_list;
    public ParserGenerator(){
        grammar_map = new HashMap<>();
        resolvable_list = new ArrayList<>();
    }
    public MLRParser generate(final String file_name) throws IOException {
        // clear all
        resolvable_list.clear();
        grammar_map.clear();
        // get definition of grammar
        String grammar = new String(Eam.use(file_name,
                (Reader<Eam, char[], IOException>) eam -> eam.read()));
        System.out.println(grammar);
        this.grammar_map = toMap(grammar);

        return null;
    }
    private Map<NonTerminal,Expression[]> toMap(final String bnf) {
        HashMap hash = new HashMap<>();
        String[] s = bnf.split("[:\\s]");
        Stack<String> t = new Stack<>();
        /*
        for(int i = 0; i < s.length; i++) {
            if (i % 2 == 0) t.push(s[i]);
            else hash.put(new NonTerminal(t.pop()),)
        }
        */
        return hash;
    }
    // todo optimize resolvable system
    private class Expression {
        boolean resolvable(final Expression lefthand_side){
            // memoize
            if (resolvable_list.contains(lefthand_side)) return true;

            if (lefthand_side instanceof NonTerminal) {
                if (grammar_map.get(lefthand_side) == null) {
                    System.out.println("undefined " + lefthand_side);
                    System.exit(1);
                }
                for (Expression expression : grammar_map.get(lefthand_side)) {
                    if (!resolvable(expression)){
                        System.out.println("not resolvable " + lefthand_side);
                        System.exit(1);
                    }
                }
            }
            resolvable_list.add(lefthand_side);
            return true;
        }
    }
    private class NonTerminal extends Expression{
        private NonTerminal(final Expression[] righthand_side){
            grammar_map.put(this,righthand_side);
            resolvable(this);
        }
    }
    private class Terminal extends Expression{
        String value;
        private Terminal(final String value){
            this.value = value;
            resolvable_list.add(this);
            resolvable(this);
        }
        @Override
        public String toString() {
            return value;
        }
    }





    void shift(){}
    void reduce(){}
    void get(final Integer i,final Character c){

    }
    String[] build(){
        StringBuffer s = new StringBuffer("E+B");
        String[] t = new String[s.length()];
        for (int i = 0;i < s.length();i++){
            t[i] = s.insert(i,"?").toString();
            System.out.println(t[i]);
        }
        return t;
    }

    public static void main(String[] args) {
        ParserGenerator lr = new ParserGenerator("1+1$");
        /*
        System.out.println(lr.input);
        System.out.println(lr.output);
        System.out.println(lr.conditions);
        */
        lr.build();
        while (true) {
            lr.get(lr.conditions.peek(),lr.input.peek());
            break;
        }
    }
    Stack<Character> input;
    Stack<Character> output;
    Stack<Integer> conditions;
    //action
    //goto
    //non terminal
    //terminal
    //item
    //items
    //closure
    //grammar
    private ParserGenerator(final String s){
        input = new Stack<>(){
            @Override
            public String toString(){
                String super_to_string = super.toString();
                return "[" + new StringBuffer(super_to_string.substring
                        (1,super_to_string.length()-1)).reverse().toString() + "]";
            }
        };
        output = new Stack<>(){
            @Override
            public String toString(){
                String super_to_string = super.toString();
                return "[" + new StringBuffer(super_to_string.substring
                        (1,super_to_string.length()-1)).reverse().toString() + "]";
            }
        };
        conditions = new Stack<>();
        StringBuffer input_buffer = new StringBuffer(s);
        for (Character c : input_buffer.reverse().toString().toCharArray()){
            input.push(c);
        }
        conditions.push(0);
    }
}

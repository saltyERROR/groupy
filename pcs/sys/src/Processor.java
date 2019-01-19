import interpreter.Interpreter;
import io.EAM;
import lexer.Lexer;
import lexer.Token;
import parser.Parser;

import java.io.IOException;
import java.util.List;

public class Processor{
    private Processor(){}
    public static void main(String[] args) throws IOException{
        char[] chars = EAM.use(args[0], eam -> eam.read());
        /*  log  */ for(char aChar : chars) System.out.print(aChar);
        /*  log  */ System.out.println();
        List<Token> tokens = Lexer.use(chars);
        /*  log  */ System.out.println(tokens);
        List<String> tree = Parser.use(tokens);
        /*  log  */ System.out.println(tree);
        Interpreter.use(tree);
    }
}
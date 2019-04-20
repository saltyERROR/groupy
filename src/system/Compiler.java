package system;

import compiler.MLRParser;
import s_expression_compiler.LLParser;

public class Compiler implements LanguageApp{
    private final Parser parser;
    public Compiler(){
        parser = new MLRParser();
    }
    public Compiler(final String parser_name){
        switch (parser_name) {
            case "LLParser":
                parser = new LLParser();
                break;
            default:
                Error.exit(Error.NOT_FOUND,"not found : parser");
                parser = null;
                break;
        }
    }
    public String use(final String input){
        return parser.use(input);
    }
}

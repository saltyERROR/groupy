package server;

import s_expression_compiler.LexicalUnit;
import s_expression_compiler.ParserStatus;
import system.Error;
import system.LanguageServerProtocol;
import system.Parser;

public class ServerProtocol implements LanguageServerProtocol {
    public void handle(final ParserStatus status){}
    // alert error
    // highlight
    // rename
    // command line
    /*
    void callback(final Parser parser){
        if (parser.status == ParserStatus.handling_error) {
            // todo implement handling_error
            // print Error information
            // Token dummy = ;
            // parser.input.push(dummy);
        } else {
            Error.exit(Error.SYNTAX,"");
        }
    }
    */
    // increment()
    // decrement()
}

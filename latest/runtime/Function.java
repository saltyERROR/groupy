package runtime;

import java.util.Optional;
import java.util.function.Consumer;

public class Function extends Node{
    String name;
    Consumer argument;
    private Function(final String s, final Consumer c){
        super();
        if(VirtualMachine.functions.stream()
                                   .anyMatch(x -> x.name == s)){
            System.out.println("ERROR: \"" + s + "\" is already defined");
            System.exit(1);
        }
        name = s;
        argument = c;
    }
    public static void call(final String s,final Node n){
        Optional<Function> called =
                VirtualMachine.functions.stream()
                                        .filter(x -> x.name == s)
                                        .findFirst();
        if (called.isPresent()) {
            if(n.type != Type.LITERAL){
                System.out.println("ERROR: illegal node");
                System.exit(1);
            }
            called.get().argument.accept(((Literal)n).value);
        } else {
            System.out.println("ERROR: \"" + s + "\" is undefined");
            System.exit(1);
        }
    }
    public static void init(final String s,final Consumer c){
        Function defined = new Function(s,c);
        VirtualMachine.functions.add(defined);
    }
}

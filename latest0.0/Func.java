import java.util.Optional;
import java.util.function.Function;

public class Func {
    private String name;
    private Function<Float,Float> value;

    private Func(final String s,final Function<Float,Float> f){
        if(VM.funcs
             .stream()
             .anyMatch(x -> x.name == s)){
            System.out.println("ERROR: \"" + s + "\" is already defined");
            System.exit(1);
        }
        name = s;
        value = f;
    }
    static void define(final String s,Function<Float,Float> f){
        Func defined = new Func(s,f);
        VM.funcs.add(defined);
    }
    static Float call(final String s,Float f) {
        Optional<Func> called = VM.funcs
                                  .stream()
                                  .filter(x -> x.name == s)
                                  .findFirst();
        if (called.isPresent()) {
            return called.get().value.apply(f);
        } else {
            System.out.println("ERROR: \"" + s + "\" is undefined");
            System.exit(1);
        }
        return null;
    }
}

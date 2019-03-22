package library;

import runtime.Function;

public class Standerd {
    public static void use() {
        Function.init("print", java.lang.System.out::println);
    }
}
package system;

public enum Error {
    LEXICAL,
    SYNTAX,
    INSTRUCTION,
    VM,
    NOT_FOUND,
    UNKNOWN;
    public static void exit(final Error type,final String detail){
        System.err.println("error : " + type + "\n" + detail);
        System.exit(1);
    }
}

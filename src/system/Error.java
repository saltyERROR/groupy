package system;

public enum Error {
    LEXICAL,
    SYNTAX,
    INSTRUCTION,
    VM,
    NOT_FOUND,
    NONE,
    UNKNOWN;
    public static void exit(final Error e,final String t){
        System.err.println("error : " + e + "\n" + t);
        System.exit(1);
    }
}

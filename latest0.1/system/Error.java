package system;

public class Error {
    public static void exit(final String s){
        System.out.println("ERROR: " + s);
        System.exit(1);
    }
}

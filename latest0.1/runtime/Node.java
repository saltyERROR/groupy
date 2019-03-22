package runtime;

public class Node {
    Type type;
    @Override
    public String toString(){
        return type.toString();
    }
}

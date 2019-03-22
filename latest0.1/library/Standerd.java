package library;

import runtime.FunctionDefine;
import runtime.Node;

import java.util.LinkedList;

public class Standerd {
    public static LinkedList<Node> use() {
        LinkedList<Node> list = new LinkedList<>();
        list.add(FunctionDefine.init("print", java.lang.System.out::print));
        return list;
    }
}
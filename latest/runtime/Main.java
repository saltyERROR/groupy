package runtime;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = VirtualMachine.init();

        //test
        LinkedList<Node> list = new LinkedList<>();
        list.add(FunctionCall.init("print",Literal.init("1.0F")));
        list.add(FunctionCall.init("print",Literal.init("2.0F")));
        list.add(FunctionCall.init("print",Literal.init("3.0F")));
        list.add(MemberCall.init("a"));
        vm.loop(list);
    }
}
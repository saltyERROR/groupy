package runtime;

import compiler.Parser;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        //test
        VirtualMachine vm = VirtualMachine.init();
        vm.using();
        LinkedList<Node> block = new Parser().parse("( print 1.0 ) end");
        vm.loop(block);
    }
}
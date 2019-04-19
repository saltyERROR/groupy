package system;

import runtime.VirtualMachine;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException{
        new Compiler("LLParser").compile("HelloWorld.txt");
        new VirtualMachine(0)
                .distribute("main",VirtualMachine.split("Instructions.txt"));
    }
}

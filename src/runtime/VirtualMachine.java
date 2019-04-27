/*
summary{
    VM: Virtual Machine
    stack machine: execute instructions with stack
    CISC: Complex Instructions Set Computer

    todo implement
    GC: Garbage Collector
    multi-thread machine: execute instructions with threads
}
*/

package runtime;

import system.Error;
import system.LanguageApp;

import java.util.*;

public class VirtualMachine implements LanguageApp {
    public VirtualMachine(final int threads_number){
        this.stack_area = new int[256];
        this.heap_area = new int[256];
        this.garbage_collector = new GarbageCollector(heap_area);
        this.main_thread = new Thread(heap_area,garbage_collector);
        if (threads_number > 0) this.thread_manager = new MultiThreadManager(threads_number);
    }
    private int[] stack_area;//static todo implement stack_area
    private int[] heap_area;//dynamic
    private GarbageCollector garbage_collector; //todo implement GC
    private Thread main_thread;
    private MultiThreadManager thread_manager;// todo implement multi thread manager

    public static int[] split(String input){
        // replace space
        input = input.replaceAll("[\\s]","");

        // split by 8 letters
        int[] result = new int[input.length()/8 + 1];
        int i = 0;
        while (input.length() >= 8){
            result[i++] = Integer.decode("0x" + input.substring(0,8));
            input = input.substring(8);
        }
        if (input.length() != 0) Error.exit(Error.INSTRUCTION,"Instructions[] is too short");
        return Arrays.copyOfRange(result,0,i);
    }
    @Override
    public String use(final String input){
        main_thread.run(0,split(input));
        return "finished";
    }
}

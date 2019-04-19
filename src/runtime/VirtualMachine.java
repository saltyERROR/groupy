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

import system.Eam;
import system.Error;
import system.LanguageApp;
import system.Reader;

import java.io.IOException;
import java.util.*;

public class VirtualMachine implements LanguageApp {
    private int[] stack_area;//static todo implement stack_area
    private int[] heap_area;//dynamic
    private GarbageCollector garbage_collector; //todo implement GC
    private Thread main_thread;
    private MultiThreadManager thread_manager;// todo implement multi thread manager
    public VirtualMachine(final int threads_number){
        this.stack_area = new int[256];
        this.heap_area = new int[256];
        this.garbage_collector = new GarbageCollector(heap_area);
        this.main_thread = new Thread(heap_area,garbage_collector);
        if (threads_number > 0) this.thread_manager = new MultiThreadManager(threads_number);
    }
    public static int[] split(String file_name) throws IOException {
        // read file
        String input = Eam.use(file_name,(Reader<Eam, char[], IOException>) eam -> eam.read());
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
    public void distribute(final String thread_name,final int[] instructions){
        if (thread_name.equals("main")) {
            main_thread.run(0,instructions);
        }
    }
    @Override
    public void send(){
        // todo implement
    }
}

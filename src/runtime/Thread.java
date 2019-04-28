package runtime;

import java.util.EmptyStackException;
import java.util.Stack;
import static java.lang.Math.*;

public class Thread {
    Thread(final int[] heap_area,final GarbageCollector garbage_collector){
        // share with VirtualMachine
        this.heap_area = heap_area;
        this.garbage_collector = garbage_collector;
        this.memory_manager = new MemoryManager();
        this.memory_manager.write(1);
        this.runtime_stack = new Stack<>();
    }
    private int[] heap_area;
    private final GarbageCollector garbage_collector;
    private final MemoryManager memory_manager;
    private int program_counter_resister;
    private int address_resister; // todo implement starting address
    private Stack<Integer> runtime_stack; // stack frame + local variable

    void run(final int starting_address,final int[] instructions) throws EmptyStackException {
        address_resister = starting_address;
        for (program_counter_resister = 0;
             program_counter_resister < instructions.length;
             program_counter_resister++) {
            runtime_stack.push(/* (instruction) fetch */ instructions[program_counter_resister]);
            switch (runtime_stack.peek() & 0xff) {
                case 0x00: // exit add
                    System.exit(args(0) + args(1));
                    break;
                case 0x01: // jump if
                    if (args(0) != 0b0) program_counter_resister = args(1);
                    break;
                case 0x02: // print get
                    runtime_stack.push(heap_area[runtime_stack.peek() >>> 8]);
                    switch (values(0)) {
                        case 0x0: // int
                            System.out.print(runtime_stack.peek() >>> 4);
                            break;
                        case 0x1: // int[]
                            StringBuilder int_array = new StringBuilder();
                            for (int i = values(1); i < values(1) + values(2); i++) {
                                int_array.append(heap_area[i]);
                            }
                            System.out.print(int_array.toString());
                            break;
                        case 0x2: // char
                            System.out.print((char) (runtime_stack.peek() >>> 4));
                            break;
                        case 0x3: // char[]
                            StringBuilder char_array = new StringBuilder();
                            for (int i = values(1); i < values(1) + values(2); i++) {
                                char_array.append((char)heap_area[i]);
                            }
                            System.out.print(char_array.toString());
                            break;
                        default:
                            System.exit(1);
                            break;
                    }
                    break;
                case 0x03: // set address
                    runtime_stack.pop();
                    address_resister = runtime_stack.pop() >>> 8;
                    break;
                case 0x10: // push
                    runtime_stack.push(runtime_stack.pop() >>> 8);
                    break;
                case 0x11: // add
                    runtime_stack.pop();
                    runtime_stack.push(runtime_stack.pop() + runtime_stack.pop());
                    break;
                case 0x12: // multiply
                    runtime_stack.pop();
                    runtime_stack.push(runtime_stack.pop() * runtime_stack.pop());
                    break;
                case 0x13: // print
                    runtime_stack.pop();
                    System.out.print(runtime_stack.pop());
                    break;
                case 0x14: // max
                    runtime_stack.pop();
                    runtime_stack.push(max(runtime_stack.pop(),runtime_stack.pop()));
                    break;
                case 0x15: // min
                    runtime_stack.pop();
                    runtime_stack.push(min(runtime_stack.pop(),runtime_stack.pop()));
                    break;
                case 0x16: // let
                    runtime_stack.pop();
                    heap_area[runtime_stack.pop()] = runtime_stack.pop();
                    break;
                case 0x17: // call
                    runtime_stack.pop();
                    runtime_stack.push(heap_area[runtime_stack.pop()]);
                    break;
                case 0x20: // put byte
                    for (int i = 0; i < 3; i++) {
                        if (args(i) > 0x00) heap_area[address_resister++] = args(i);
                    }
                    break;
                case 0x21: // put
                    heap_area[address_resister++] = runtime_stack.pop() >>> 8;
                    break;
                case 0x30: // put primitive
                    if (heap_area[address_resister] != 0) {
                        memory_manager.rewrite(heap_area[address_resister],runtime_stack.pop() >>> 8);
                    } else {
                        heap_area[address_resister] = memory_manager.write(runtime_stack.pop() >>> 8);
                    }
                    address_resister++;
                    break;
                case 0x31: // call primitive
                    System.out.print(memory_manager.read(heap_area[runtime_stack.pop() >>> 8]));
                    break;
                case 0x32: // put reference
                    heap_area[address_resister++] = heap_area[runtime_stack.pop() >>> 8];
                default:
                    break;
            }
            garbage_collector.collect();
        }
    }
    private int args(final int counter){
        if (counter == 0) return (runtime_stack.peek() >>> 8) & 0xff;
        else if (counter == 1) return (runtime_stack.peek() >>> 16) & 0xff;
        else if (counter == 2) return runtime_stack.peek() >>> 24;
        else System.exit(1);
        return -1;
    }
    private int args2(final int counter){
        if (counter == 0) return (runtime_stack.peek() >>> 8) & 0xfff;
        else if (counter == 1) return (runtime_stack.peek() >>> 20) & 0xfff;
        else System.exit(1);
        return -1;
    }
    private int values(final int counter){
        if (counter == 0) return runtime_stack.peek() & 0xf;
        else if (counter == 1) return (runtime_stack.peek() >>> 4) & 0xfff;
        else if (counter == 2) return runtime_stack.peek() >>> 16;
        else System.exit(1);
        return -1;
    }
}

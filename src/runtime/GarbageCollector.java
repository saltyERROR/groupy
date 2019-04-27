package runtime;

public class GarbageCollector {
    GarbageCollector(final int[] heap_area){
        // share with VirtualMachine
        this.heap_area = heap_area;
    }
    private int[] heap_area;
    void collecting(){
        // System.out.println("collecting");
    }
    void collect(){
        int using_memory_size = 0;
        for (int address = 0; address < heap_area.length; address++) {
            if (heap_area[address] != 0) using_memory_size++;
        }
        if (using_memory_size > 10) {
            collecting();
        }
    }
}

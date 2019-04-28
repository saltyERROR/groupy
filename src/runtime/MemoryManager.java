package runtime;

public class MemoryManager {
    public MemoryManager(){
        this.stack_area = new int[256];
    }
    private int[] stack_area;
    private int malloc(final int size){
        for (int i = 0; i < stack_area.length; i++) {
            if (stack_area[i] == 0) {
                boolean continue_or_not = false;
                for (int j = 0;j < size - 1;j++){
                    if (stack_area[i + 1 + j] != 0){
                        i += 1 + j;
                        continue_or_not = true;
                        break;
                    }
                }
                if (continue_or_not) continue;
                return i;
            }
        }
        System.err.println("out of memory!");
        return 0;
    }
    public int write(final int[] a){
        final int starting_address = malloc(a.length);
        for (int i = 0; i < a.length; i++) {
            stack_area[starting_address + i] = a[i];
        }
        return starting_address;
    }
    public int write(final int a){
        final int address = malloc(1);
        stack_area[address] = a;
        return address;
    }
    public int read(final int address){
        return stack_area[address];
    }
    public void rewrite(final int address,final int a){
        stack_area[address] = a;
    }
}
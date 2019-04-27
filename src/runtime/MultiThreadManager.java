package runtime;

import java.util.Arrays;

public class MultiThreadManager {
    private Thread[] threads;
    MultiThreadManager(final int thread_number){
        this.threads = new Thread[thread_number];
    }
    public void distribute(final int[] instructions){
    }
}

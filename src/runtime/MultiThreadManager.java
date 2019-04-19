package runtime;

public class MultiThreadManager {
    private Thread[] threads;
    MultiThreadManager(final int thread_number){
        threads = new Thread[thread_number];
    }
}

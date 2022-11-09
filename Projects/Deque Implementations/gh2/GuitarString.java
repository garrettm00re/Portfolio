package gh2;
import deque.ArrayDeque;
import deque.Deque;
//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;
    private int capacity;
    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        capacity = (int) Math.round(SR/frequency);
        double zero = (double) 0;
        deque.ArrayDeque<Double> use = new ArrayDeque<>();
        for (int i = 0; i < capacity; i++) {
            use.addFirst(zero);
        }
        buffer = use;
    }
    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < capacity; i++) {
            buffer.removeFirst();
            double plucked = Math.random() - 0.5;
            buffer.addLast(plucked);
        }
    }
    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double sample = buffer.removeFirst();
        double newLast = (buffer.get(0) + sample) * DECAY / 2;
        buffer.addLast(newLast);
    }
    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}

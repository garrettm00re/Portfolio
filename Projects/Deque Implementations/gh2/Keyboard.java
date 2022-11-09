package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class Keyboard {
    private static final GuitarString[] keyboard = new GuitarString[37];
    private static final String keys = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        for (int i = 0; i < 37; i++) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
            keyboard[i] = new GuitarString(frequency);
        }
        /* create two guitar strings, for concert A and C */
        while (true) {
                /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keys.indexOf(key) >= 0 && keys.indexOf(key) < 37) { //change back to 37
                    keyboard[keys.indexOf(key)].pluck();
                }
            }
        /* compute the superposition of samples */
        double sample = 0.0;
        for (int i = 0; i < 37; i++) {
            sample += keyboard[i].sample();
        }
        /* play the sample on standard audio */
        StdAudio.play(sample);
        /* advance the simulation of each guitar string by one step */
        for (int i = 0; i < 37; i++) {
            keyboard[i].tic();
        }
        }
    }
}

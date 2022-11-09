package DataStructuring;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class hGraphBuilder {
    hGraph hypo;
    HashMap<String, List<Integer>> wordCode; // may not even be necessary

    public hGraphBuilder(String synsets, String hyponyms) {
        In ss = new In(synsets);
        In hn = new In(hyponyms);
        hypo = new hGraph();
        wordCode = new HashMap<>();
        while (ss.hasNextLine()) {
            String line = ss.readLine();
            String[] split = line.split(",");
            String[] removeSpace = split[1].split(" ");// what happens if no space
            String[] allWordForCode = new String[removeSpace.length];// see green squiggly below
            int i = 0;
            for (String s : removeSpace) {
                List<Integer> l = new ArrayList<>();
                if (wordCode.containsKey(s)) {
                    l = wordCode.get(s);
                }
                l.add(Integer.valueOf(split[0]));
                wordCode.put(s, l); //should overwrite existing list
                allWordForCode[i] = s;
                i++;
            }
            hypo.newVert(Integer.valueOf(split[0]), allWordForCode); //split s by underscores and concatenate result?
        }
        while (hn.hasNextLine()) {
            String line = hn.readLine();
            String[] split = line.split(",");
            for (int i = 1; i < split.length; i++) {
                hypo.addEdge(Integer.valueOf(split[0]), Integer.valueOf(split[i]));
                /** OLD IMPLEMENTATION
                 *
                 * String[] Parent = (String[]) wordCode.get(Integer.valueOf(split[0])); //why are there casts!!
                String[] Child = (String[]) wordCode.get(Integer.valueOf(split[i])); */
                        //String[] sP = Parent.split(" ");
                        //String[] sC = Child.split(" ");
                /**for (String x : Parent) {
                    for (String y : Child) {
                        //y.replace("_", " ");
                        //x.replace("_", " ");
                        hypo.addEdge(x, y);
                    }
                } */
            }
        }
    }
    public hGraph gHyponyms() {
        return hypo;
    }
    public HashMap<String, List<Integer>> getWC() {
        return wordCode;
    }
}

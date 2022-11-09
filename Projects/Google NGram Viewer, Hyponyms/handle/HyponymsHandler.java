package handle;

import DataStructuring.hGraph;
import DataStructuring.hGraphBuilder;
import profhugbrowser.NgordnetQuery;
import profhugbrowser.NgordnetQueryHandler;
import DataStructuring.NGramMap;
import DataStructuring.TimeSeries;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Collections.min;

public class HyponymsHandler extends NgordnetQueryHandler {
    hGraph hgr;
    NGramMap ngm;
    HashMap wordCode;

    public HyponymsHandler(String words, String counts, String synsets, String hyponyms) {
        super();
        hGraphBuilder hGB = new hGraphBuilder(synsets, hyponyms);
        hgr = hGB.gHyponyms();
        wordCode = hGB.getWC();
        ngm = new NGramMap(words, counts);
    }
    @Override
    public String handle(NgordnetQuery q) {
        HashMap<Integer, HashSet<Integer>> allWordSets = new HashMap<>();
        int index = 0;
        for (String s : q.words()) {
            HashSet<Integer> sub = hgr.subSet((List<Integer>) wordCode.get(s)); //why is cast to int neccessary
            allWordSets.put(index, sub);
            index++;
        }
        //HashMap<Integer, HashSet<String>> everyWord = flatten(allWordSets);
        //HashSet<String> mergedStrings = mergeWords(everyWord);
        HashSet<String> mergedStrings = flatTenMerge(allWordSets);
        return sorter(mergedStrings, q);
    }
    private HashSet<String> flatTenMerge(HashMap<Integer, HashSet<Integer>> h) {
        HashSet<String> fm = new HashSet<>();
        if (h.size() >= 1) {
            fm = stringIt(h.get(0)); // fixed flatten
        }
        for (int i = 1; i < h.size(); i++) {
            HashSet<String> r = stringIt(h.get(i));
            HashSet<String> removals = new HashSet<>();
            if (fm == null || r == null) {
                return null;
            }
            for (String y : fm) {
                if (!r.contains(y)) {
                    removals.add(y);
                }
            }
            fm.removeAll(removals);
        }
        return fm;
    }
    private HashSet<String> stringIt(HashSet<Integer> h)  {
        int i = 0;
        String qqq = ""; //debugging
        HashSet<String> flat = new HashSet<>();
        if (h == null) {
            return null;
        }
        for (int y : h) {
            String[] synW = hgr.getWords(y);
            for (String s : synW) {
                qqq = s;
                flat.add(s);
            }
        }
        return flat;
    }
    private HashMap<Double, String> arrByFreq(Collection<String> s, NgordnetQuery p) {  //handling for k > 0
        HashMap<Double, String> arranged = new HashMap<>();
        for (String y : s) {
            double yearlyTotal = 0;
            TimeSeries ts = ngm.countHistory(y, p.startYear(), p.endYear()); // causing error, investigate
            for (int f : ts.keySet()) {
                yearlyTotal += ts.get(f);
            }
            if (yearlyTotal > 0) {
                if (arranged.size() == p.k() && yearlyTotal > min(arranged.keySet())) {
                    arranged.put(yearlyTotal, y);
                    arranged.remove(min(arranged.keySet()));
                } else if (arranged.size() < p.k()) { //)
                    arranged.put(yearlyTotal, y);
                }
            }
        }
        return arranged;
    }
    private String sorter(Collection<String> words, NgordnetQuery f) {
        String finalString = "[";
        List<String> x = new ArrayList<>();
        if (f.k() == 0 && words != null) {
            x = words.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        } else if (f.k() > 0 && words != null) {
            HashMap<Double, String> l = arrByFreq(words, f);
            x = l.keySet().stream().map(v -> l.get(v)).sorted().collect(Collectors.toList());
            /**Set<Double> y = l.keySet(); //.stream().sorted().collect(Collectors.toSet());
            //map(v -> l.get(v)).collect(Collectors.toList());
            for (int i = 0; i < f.k(); i++) { //for loop implements order by descending popularity
                                            // stream implementation works better imo
                double d = max(y);
                x.add(i, l.get(d));
                y.remove(d);
            }
             */
        }
        for (int i = 0; i < x.size(); i++) {
            if (i != x.size() - 1) {
                finalString += x.get(i) + ", ";
            } else {
                finalString += x.get(i);
            }
        }
        return finalString + "]";
    }


    /** DEPRECATED METHODS BELOW// USEFUL/INTERESTING BITS OF CODE? */

    /**private HashMap<Integer, HashSet<String>> flatten(HashMap<Integer, HashSet<Integer>> h) {
        HashMap<Integer, HashSet<String>> finalHash = new HashMap<>();
        int i = 0;
        for (HashSet<Integer> x : h.values()) {
            HashSet<String> flat = new HashSet<>();
            for (int y : x) {
                String[] synW = hgr.getWords(y);
                for (String s : synW) {
                    flat.add(s);
                }
            }
            finalHash.put(i, flat);
            i++;
        }
        return finalHash;
    }

    private HashSet<String> mergeWords(HashMap<Integer, HashSet<String>> h) {
        HashSet<String> allStrings = new HashSet<>();
        if (h != null) {
            for (int i = 0; i < h.size(); i++) { //HashSet<Integer> x : h) {
                for (String x : h.get(i)) {
                    String merge = x; //adding in case removing from the hash set takes away mapping of x->
                    // likely unnecessary
                    boolean b = true;
                    for (int j = 0; i < h.size() && b; i++) {
                        if (h.get(j) == null || !h.get(j).contains(x)) {
                            b = false;
                        }
                        //h.get(j).remove(x); Concurrent modification exception
                        // very sad, was a pretty good idea IMO
                    }
                    if (b) {
                        allStrings.add(x);
                    }
                }
            }
        }
        return allStrings;
    }
    private HashSet<String> intToString(HashSet<Integer> merged) {
        HashSet<String> strings = new HashSet<>(); // could be better data structure for this
        for (int x : merged) {
            String[] wordArr = hgr.getWords(x);
            for (String y : wordArr) {
                strings.add(y);
            }
        }
        return strings;
    }
     */

    /**String[] finalWords = new String[q.k()]; //probably better to delete
     Object[] sortedDoubles = summedTotal.keySet().stream().sorted(Comparator.naturalOrder()).toArray();
     //removing toArray could improve efficiency
     int i = 0; */
    /**
     for (Double x : (Double[]) sortedDoubles) {
     if (i < q.k() - 1) {
     finalString += summedTotal.get(x) + ", ";
     i++;
     } else if (i == q.k() - 1) {
     finalString += summedTotal.get(x);
     }
     }
     finalString += "]";
     return finalString;
     } */

    /**List<String> aWord = new ArrayList<>();
     for (int x : sub) {
     for (String y : hgr.getWords(x)) {
     totalWords.add(y);

     //two ways--either add all words then refine, or add all for the first
     //and ensure for each subsequent addition that the word is contained in all sublists with index
     // from 0 to i - 1
     }
     }
     }
     */

    /**now just figure out the best data structure for line 36 and how to get the greatest k double values and
     * you should be done (except for the fact you've tested nothing)
     */
}

package DataStructuring;
import edu.princeton.cs.algs4.In;

import java.util.*;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    private TimeSeries wordTime; //may need to change all of these to TimeSeries
    private In wfn; //do I need wfn and cfn to be instance variables accessible to me (even privately?)
    private In cfn;
    private TreeMap<String, TimeSeries> wordMaps;
    public NGramMap(String wordsFilename, String countsFilename) {
        wordTime = new TimeSeries();
        wfn = new In(wordsFilename);
        cfn = new In(countsFilename);
        wordMaps = new TreeMap<>();
        String s = null;
        String q = null;
        TimeSeries wordData = new TimeSeries();
        int i = 0;
        while (!wfn.isEmpty()) {
            if (i == 0) {
                s = wfn.readString();
                q = s;
            }
            while (!wfn.isEmpty() && s.equals(q)) { //was read next line
                int r1 = wfn.readInt();
                double r2 = Double.valueOf(wfn.readInt());
                wordData.put(r1, r2);
                int ignore = wfn.readInt(); // skips fourth column's integer
                if (wfn.isEmpty()) {
                    break; //{new strat could be to read one line at a time}
                }
                s = wfn.readString();
            }
            i++;
            wordMaps.put(q, new TimeSeries(wordData, wordData.firstKey(), wordData.lastKey()));
            wordData.clear();
            q = s;
        }
        while (cfn.hasNextLine()) {
            char[] line = cfn.readLine().toCharArray();
            String s1 = "";
            String s2 = "";
            boolean b = false;
            for (char x : line) {
                if (x != ',' && !b) {
                    s1 += x;
                } else if (x == ',') {
                    if (b) {
                        break; //line == null could work here??
                    }
                    b = true; //break or continue here?
                } else if (b) {
                    s2 += x;
                }
            }
            wordTime.put(Integer.valueOf(s1), Double.valueOf(s2));
        }
    }
    /* Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        return new TimeSeries(wordMaps.get(word), wordMaps.get(word).firstKey(), wordMaps.get(word).lastKey());
    }
    /**private TimeSeries copyMachine(TimeSeries ts) {
        return new TimeSeries(ts, ts.firstKey(), ts.lastKey());
    } */

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        return new TimeSeries(wordMaps.get(word), startYear, endYear);
    }


    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(wordTime, wordTime.firstKey(), wordTime.lastKey());
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        return wordMaps.get(word).dividedBy(wordTime);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return countHistory(word, startYear, endYear).dividedBy(wordTime);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        //List<TimeSeries> wh = new ArrayList<>();
        int[] arr = {wordTime.firstKey(), wordTime.lastKey()};
        return summer(words, arr);
    }
    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        int[] arr = {startYear, endYear};
        return summer(words, arr);
    }
    private TimeSeries summer(Collection<String> words, int[] arr) {
        //could condense this by saying if args is null,
        // args[0] == first key and args[1] == last key
        //Iterator<String> w = words.iterator();
        TimeSeries ts = new TimeSeries();
        for (String s : words) {
            ts = ts.plus(weightHistory(s, arr[0], arr[1]));
        }
        return ts;
        /**
        int i = 0;
        while (w.hasNext()) {
            if (args.length == 0 && i == 0) { // checking args.length is not zero as opposed to checking if its null
                ts = wordMaps.get(w.next());
            } else if (args.length == 0) {
                ts = ts.plus(wordMaps.get(w.next()));
            } else if (args.length != 0 && i == 0) {
                ts = countHistory(w.next(), args[0], args[1]);
            } else {
                ts = ts.plus(countHistory(w.next(), args[0], args[1]));
            }
            i++;
        }
        ts = ts.dividedBy(wordTime);
        return ts;
    } */
    }
}

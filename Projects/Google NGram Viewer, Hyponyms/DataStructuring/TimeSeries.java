package DataStructuring;
import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> { //maybe remove integer/ double and use generic types?
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        /** Instances in which I can see countHistory(word, startYear, endYear).years() returning an empty list:
         * when word is not in the crv file,
         * start year > endYear
         * or there are no keys defined on the range between startYear and endYear. */
        Integer lastUsed = startYear;
        for (int f = 0; lastUsed != null && lastUsed <= endYear; f++) { //<= or < ???
            if (ts.containsKey(lastUsed)) {
                this.put(lastUsed, ts.get(lastUsed));
            }
            lastUsed = ts.higherKey(lastUsed);
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        List<Integer> l = new ArrayList<>();
        Integer lastKey = null;
        for (int i = 0; i < this.size(); i++) {
            if (i == 0) {
                lastKey = this.firstKey();
                l.add(i, lastKey);
            } else {
                l.add(i, this.higherKey(lastKey));
                lastKey = this.higherKey(lastKey);
            }
        }
        return l;
    }
    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        List<Integer> y = this.years();
        List<Double> d = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            d.add(i, this.get(y.get(i)));
        }
        return d;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries t = new TimeSeries();
        Double d = null;
        Double ds = null;
        for (int i : mergeKeys(ts)) {
            d = this.get(i);
            ds = ts.get(i);
            t.put(i, converter(d) + converter(ds));
        }
        return t;
    }
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries t = new TimeSeries();
        Double d = null;
        Double ds = null;
        for (int i : this.keySet()) {
            d = this.get(i);
            ds = ts.get(i);
            if (d == null) {
                continue;
            }
            if (ds == null && d != null) {
                throw new IllegalArgumentException();
            }
            t.put(i, d / ds);
        }
        return t;
    }
    private double converter(Double A) {
        if (A == null) {
            return 0;
        }
        return A;
    }
    private ArrayList<Integer> mergeKeys(TimeSeries ts) {
        ArrayList<Integer> a = new ArrayList<>();
        a.addAll(this.keySet());
        a.addAll(ts.keySet());
        ArrayList<Integer> b = new ArrayList<>();
        int i = 0;
        for (int y : a) {
            if (!b.contains(y)) {
                b.add(y);
            }
        }
        return b;
    }
}

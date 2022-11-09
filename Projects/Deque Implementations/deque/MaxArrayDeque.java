package deque;
import java.util.Comparator;
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> cP;
    public MaxArrayDeque(Comparator<T> c) {
        cP = c;
    }
    public T max() {
        return helper(cP);
    }
    public T max(Comparator<T> c) {
        return helper(c);
    }
    private T helper(Comparator<T> c) {
        int index = 0;
        T item = null;
        Comparator<T> cP3 = c;
        for (int i = 0; i < size(); i++) {
            if (cP3.compare(get(i), get(index)) > 0) {
                index = i;
            }
            if (i == size() - 1) {
                item = get(index);
            }
        }
        return item;
    }
}

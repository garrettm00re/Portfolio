package DataStructuring;
import java.util.*;
public class hGraph {
    private int v;
    private int e;
    private HashMap<Integer, Node> connections; //<Integer, List>
    private class Node {
        String[] allW;
        List<Integer> l;
        Node(String[] s) {
            allW = s;
            l = null;
        }
    }
    public hGraph() { //Create empty graph with v vertices
       v = 0;
       e = 0;
       connections = new HashMap(); //change to mappings?
    }
    protected void addEdge(int p, int c) { //adds a directed pointer from q to w
        Node eTo = connections.get(p);
        if (eTo.l != null) {
            if (!eTo.l.contains(c)) {
                eTo.l.add(c);
                e++;
            }
        } else {
            eTo.l = new ArrayList();
            eTo.l.add(c); //want to generalize for efficiency --> minimize key access calls by using ... args???
            //connections.put(s, eTo);
            e++;
        }
    }
    private int V() { // number of vertices that have connections
        return v;
    }                       //    number of vertices
    private int E() { // number of connections
        return e;
    }
    protected void newVert(int sc, String[] wG) {
        connections.put(sc, new Node(wG));
        v++;
    }
    public HashSet<Integer> subSet(List<Integer> s) {
        HashMap<Integer, Boolean> marked = new HashMap<>();
        HashSet<Integer> queue = new HashSet<>();
        if (s == null) {
            return null;
        }
        for (int x : s) {
            queue.add(x);//fringe.enqueue(s);
            marked.put(x, true); //could change to HashSet
        }
        while (!queue.isEmpty()) {
            Iterator<Integer> queued = queue.iterator();
            int v = queued.next(); //does this actually remove from the set (no, i think)
            queue.remove(v);
            Node eTo = connections.get(v);
            if (eTo != null && eTo.l != null) {
                for (int w : eTo.l) {
                    if (marked != null && marked.get(w) == null) {
                        queue.add(w);
                        marked.put(w, true);
                    }
                }
            }
        }
        HashSet<Integer> r = new HashSet<>();
        r.addAll(marked.keySet());
        return r; //could just return collection<integer> instead and avoid this bs
    }

        /**public HashSet<Integer> subSet(int s) {
        HashSet h = new HashSet();
        return helper(s, h);
    }
    private HashSet<Integer> helper(int y, HashSet<Integer> toList) { //probably wrong, look at traversals lecture + pseudocode
        Node eRecurs = connections.get(y);
        toList.add(y);
        if (eRecurs != null && eRecurs.l != null) {
            for (int x : eRecurs.l) {
                toList.add(x);
                helper(x, toList);
            }
        }
        return toList;
    }*/
    public String[] getWords(int i) {
        return connections.get(i).allW;
    }

    /**private void addList(Node n, List children) {
        n.l = children; // need better functionality
    }*/
}

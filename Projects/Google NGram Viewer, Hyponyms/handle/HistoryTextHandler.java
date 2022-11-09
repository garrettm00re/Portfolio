package handle;
import DataStructuring.NGramMap;
import profhugbrowser.NgordnetQuery;
import profhugbrowser.NgordnetQueryHandler;


import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private DataStructuring.NGramMap mapped;
    public HistoryTextHandler(NGramMap map) {
        mapped = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        for (String s : words) {
            response += s + ':' + " " + mapped.weightHistory(s, startYear, endYear).toString() + '\n';
        }
        return response;
    }
}

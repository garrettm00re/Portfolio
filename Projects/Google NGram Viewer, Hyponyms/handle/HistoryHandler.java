package handle;
import profhugbrowser.NgordnetQuery;
import profhugbrowser.NgordnetQueryHandler;
import DataStructuring.NGramMap;
import DataStructuring.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap mapped;
    public HistoryHandler(NGramMap map) {
        mapped = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<TimeSeries> data = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String s : words) {
            data.add(mapped.weightHistory(s, startYear, endYear));
            labels.add(s);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, data);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}


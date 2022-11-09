package handle;

import profhugbrowser.HugNgordnetServer;
import DataStructuring.NGramMap;
import profhugbrowser.NgordnetQueryHandler;

public class Main {
    public static void main(String[] args) {
        HugNgordnetServer hns = new HugNgordnetServer();
        String wordFile = "./data/ngrams/top_49887_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";

        NGramMap ngm = new NGramMap(wordFile, countFile);
        NgordnetQueryHandler hypH = new HyponymsHandler(wordFile, countFile, synsetFile, hyponymFile);
        NgordnetQueryHandler histH = new HistoryHandler(ngm);
        NgordnetQueryHandler histT = new HistoryTextHandler(ngm);
        hns.startUp();
        hns.register("hyponyms", hypH);
        hns.register("history", histH);
        hns.register("historytext", histT);
    }
}

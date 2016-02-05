package helpers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * Simple command-line based search demo.
 */
public class SearchFiles {

    public static void main(String[] args) throws ParseException, IOException {
        
        Date start = new Date();
        System.out.println(queryIndex("java"));
        Date end = new Date();
        System.out.println("#############################################################");
        System.out.println("Total Time Taken: " + (end.getTime() - start.getTime()) + "ms");
        System.out.println("#############################################################");
    }

    public static List<SearchDoc>  queryIndex(String line) throws ParseException, IOException {

        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(IndexFiles.INDEX_PATH)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();

            String field = "name";
            QueryParser parser = new QueryParser(field, analyzer);
            Query query = parser.parse(line.trim());

            System.out.println("Searching for: " + query.toString(field));

            return doPagingSearch(searcher, query);
        }
    }

    public static List<SearchDoc> doPagingSearch(IndexSearcher searcher, Query query) throws IOException {
        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 50); // get atmost 50 results
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");
        int start = 0;
        int end = Math.min(numTotalHits, 100);
        
        List<SearchDoc> docsList = new ArrayList<>(end);
        for (int i = start; i < end; i++) {
            Document doc = searcher.doc(hits[i].doc);
            docsList.add(new SearchDoc(doc.get("name"), doc.get("path")));
        }
        return docsList;
    }
}

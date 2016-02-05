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
public class FilesSearcher {
    
    

    public static List<SearchDoc>  queryIndex(String line) throws ParseException, IOException {

        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(SearchDoc.DEFAULT_INDEX_PATH)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();

            QueryParser parser = new QueryParser(SearchDoc.FIELD_STR_FILE_NAME, analyzer);
            Query query = parser.parse(line.trim());

            System.out.println("Searching for: " + query.toString(SearchDoc.FIELD_STR_FILE_NAME));

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
            docsList.add(new SearchDoc(
                    doc.get(SearchDoc.FIELD_STR_FILE_NAME), 
                    doc.get(SearchDoc.FIELD_STR_FILE_PATH), 
                    Long.parseLong(doc.get(SearchDoc.FIELD_LONG_FILE_MODIFIED))));
        }
        return docsList;
    }
}

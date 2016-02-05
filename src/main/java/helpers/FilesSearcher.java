package helpers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
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
    
    private IndexReader reader;
    private IndexSearcher indexSearcher;
    private Analyzer analyzer;
    private QueryParser queryParser;

    public FilesSearcher() {
        initVars();
    }
    
    private void initVars() {
        try {
            reader = DirectoryReader.open(FSDirectory.open(Paths.get(SearchDoc.DEFAULT_INDEX_PATH)));
            indexSearcher = new IndexSearcher(reader);
            analyzer = new StandardAnalyzer();
            queryParser = new QueryParser(SearchDoc.FIELD_STR_FILE_NAME, analyzer);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
    
    public List<SearchDoc> queryIndex(String line) {
        try {
            Query query = queryParser.parse(line.trim());
            System.out.println("Searching for: " + query.toString(SearchDoc.FIELD_STR_FILE_NAME));
            return doPagingSearch(indexSearcher, query);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return null;
    }

    public List<SearchDoc> doPagingSearch(IndexSearcher searcher, Query query) throws IOException {
        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 100); // get atmost 100 results
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

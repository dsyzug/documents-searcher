package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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
        queryIndex("2014");
        Date end = new Date();
        System.out.println("#############################################################");
        System.out.println("Total Time Taken: " + (end.getTime() - start.getTime()) + "ms");
        System.out.println("#############################################################");
    }

    public static void queryIndex(String line) throws ParseException, IOException {

        try (IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(IndexFiles.INDEX_PATH)))) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();

            String field = "name";
            QueryParser parser = new QueryParser(field, analyzer);
            Query query = parser.parse(line.trim());

            System.out.println("Searching for: " + query.toString(field));
            searcher.search(query, 100);

            doPagingSearch(searcher, query);
        }
    }

    public static void doPagingSearch(IndexSearcher searcher, Query query) throws IOException {
        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 50); // get atmost 50 results
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");
        int start = 0;
        int end = Math.min(numTotalHits, 10);
        for (int i = start; i < end; i++) {
            System.out.println("===============================================");
            Document doc = searcher.doc(hits[i].doc);
            System.out.println("Name: " + doc.get("name"));
            System.out.println("Path: " + doc.get("path"));
            System.out.println("Modified: " + doc.get("modified"));
        }

    }
}

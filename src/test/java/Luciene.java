
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.QueryBuilder;

public class Luciene {
	public static void main(String[] args) throws Exception {
		// Specify the analyzer for tokenizing text.
		// The same analyzer should be used for indexing and searching
		Analyzer analyzer = new StandardAnalyzer();

		// Create the index
		Directory index = new RAMDirectory();
		// Directory index = new SimpleFSDirectory(Paths.get("c://lucene"));

		IndexWriterConfig config = new IndexWriterConfig(analyzer);

		IndexWriter w = new IndexWriter(index, config);
		addDoc(w, "Lucene in action", "a", "193398817");
		addDoc(w, "Lucene for dummies Action", "a", "55320055Z");
		addDoc(w, "Managing Gigabytes ", "Mirosław Szajowski", "55063554A");
		addDoc(w, "The Art of Computer Science ", "Mirosław Szajowski", "9900333X");
		addDoc(w, "FAS/123/2078 The Art of Computer Science Europe/Berlin GSX-R1000", "Mirosław Szajowski",
				"9900333X");
		w.close();
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		// Query

		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.

		BoostQuery tq = new BoostQuery(new TermQuery(new Term("name", "mirosław")), 3f);
		BooleanClause booleanClause = new BooleanClause(tq, BooleanClause.Occur.SHOULD);

		BoostQuery title = new BoostQuery(new TermQuery(new Term("title", "action")), 4f);
		BooleanClause titleClause = new BooleanClause(title, BooleanClause.Occur.SHOULD);

		;
		TopDocs search = searcher.search(new BooleanQuery.Builder().add(booleanClause).add(titleClause).build(), 3);

		ScoreDoc[] scoreDocs = search.scoreDocs;
		System.out.println("Found1 " + scoreDocs.length + " hits.");
		for (int i = 0; i < scoreDocs.length; ++i) {
			int docId = scoreDocs[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
		}

		Query q = new QueryBuilder(analyzer).createBooleanQuery("title", "GSX-R");

		// Search
		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		// Display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
		}

		// reader can only be closed when there
		// is no need to access the documents any more.
		reader.close();
	}

	private static void addDoc(IndexWriter w, String title, String name, String isbn) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("name", name, Field.Store.YES));

		// Use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		w.addDocument(doc);
	}
}

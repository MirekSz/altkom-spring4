package pl.altkom.shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.search.backend.AddLuceneWork;
import org.hibernate.search.backend.DeleteLuceneWork;
import org.hibernate.search.backend.IndexingMonitor;
import org.hibernate.search.backend.LuceneWork;
import org.hibernate.search.backend.UpdateLuceneWork;
import org.hibernate.search.backend.spi.BackendQueueProcessor;
import org.hibernate.search.indexes.spi.IndexManager;
import org.hibernate.search.spi.WorkerBuildContext;

public class SolrIntegration implements BackendQueueProcessor {
	private static final String ID_FIELD_NAME = "id";

	private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static final ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

	private ConcurrentUpdateSolrClient solrServer;

	@Override
	public void close() {
	}

	@Override
	public void applyWork(List<LuceneWork> luceneWorks, IndexingMonitor indexingMonitor) {
		List<SolrInputDocument> solrWorks = new ArrayList<>(luceneWorks.size());
		List<String> documentsForDeletion = new ArrayList<>();

		for (LuceneWork work : luceneWorks) {
			SolrInputDocument solrWork = new SolrInputDocument();
			if (work instanceof AddLuceneWork) {
				handleAddLuceneWork((AddLuceneWork) work, solrWork);
			} else if (work instanceof UpdateLuceneWork) {
				handleUpdateLuceneWork((UpdateLuceneWork) work, solrWork);
			} else if (work instanceof DeleteLuceneWork) {
				documentsForDeletion.add(((DeleteLuceneWork) work).getIdInString());
			} else {
				throw new RuntimeException("Encountered unsupported lucene work " + work);
			}
			solrWorks.add(solrWork);
		}
		try {
			deleteDocs(documentsForDeletion);
			solrServer.add(solrWorks);
			softCommit();
		} catch (SolrServerException | IOException e) {
			throw new RuntimeException("Failed to update solr", e);
		}

	}

	@Override
	public void applyStreamWork(LuceneWork luceneWork, IndexingMonitor indexingMonitor) {
		throw new RuntimeException("HibernateSearchSolrWorkerBackend.applyStreamWork isn't implemented");
	}

	private void deleteDocs(Collection<String> collection) throws IOException, SolrServerException {
		if (collection.size() > 0) {
			StringBuilder stringBuilder = new StringBuilder(collection.size() * 10);
			stringBuilder.append(ID_FIELD_NAME).append(":(");
			boolean first = true;
			for (String id : collection) {
				if (!first) {
					stringBuilder.append(',');
				} else {
					first = false;
				}
				stringBuilder.append(id);
			}
			stringBuilder.append(')');
			solrServer.deleteByQuery(stringBuilder.toString());
		}
	}

	private void copyFields(Document document, SolrInputDocument solrInputDocument) {
		boolean addedId = false;
		for (IndexableField fieldable : document.getFields()) {
			if (fieldable.name().equals(ID_FIELD_NAME)) {
				if (addedId)
					continue;
				else
					addedId = true;
			}
			solrInputDocument.addField(fieldable.name(), fieldable.stringValue());
		}
	}

	private void handleAddLuceneWork(AddLuceneWork luceneWork, SolrInputDocument solrWork) {
		copyFields(luceneWork.getDocument(), solrWork);
	}

	private void handleUpdateLuceneWork(UpdateLuceneWork luceneWork, SolrInputDocument solrWork) {
		copyFields(luceneWork.getDocument(), solrWork);
	}

	private void softCommit() throws IOException, SolrServerException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.setParam("soft-commit", "true");
		updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		updateRequest.process(solrServer);
	}

	@Override
	public void initialize(Properties props, WorkerBuildContext context, IndexManager indexManager) {
		solrServer = new ConcurrentUpdateSolrClient("http://localhost:8983/solr", 20, 4);

	}

	public static void main(String[] args) throws Exception {
		String urlString = "http://localhost:8983/solr/products";
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "552192");
		document.addField("name", "Gouda cheese wheel");
		document.addField("price", "49.99");
		solr.add(document);

		// Remember to commit your changes!

		solr.commit();
	}

}

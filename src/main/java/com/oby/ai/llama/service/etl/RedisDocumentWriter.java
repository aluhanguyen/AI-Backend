package com.oby.ai.llama.service.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentWriter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author OBY.Mike
 * Created on 28/11/24.
 */
@Component
public class RedisDocumentWriter implements DocumentWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDocumentWriter.class);

    private final VectorStore vectorStore;

    @Autowired
    public RedisDocumentWriter(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void accept(List<Document> documents) {
        vectorStore.add(documents);
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query("ETL")
                .withTopK(5).withSimilarityThreshold(0.5));
        LOGGER.info("Similarity Document: {}", results.size());
    }
}

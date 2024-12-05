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
import java.util.Map;

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
        List <Document> tmpDocuments = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
        documents.addAll(tmpDocuments);
        vectorStore.add(documents);
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query("ETL")
                .withTopK(5).withSimilarityThreshold(0.5));
        LOGGER.info("Similarity Document: {}", results.size());
    }
}

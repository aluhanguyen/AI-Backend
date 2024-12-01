package com.oby.ai.llama.service;

import com.oby.ai.llama.service.etl.PdfDocumentReader;
import com.oby.ai.llama.service.etl.RedisDocumentWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author OBY.Mike
 * Created on 27/11/24.
 */
@Service
public class ETLService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ETLService.class);

    private final PdfDocumentReader documentReader;
    private final TextSplitter textSplitter;
    private final RedisDocumentWriter redisDocumentWriter;

    public ETLService(PdfDocumentReader documentReader,
                      TextSplitter textSplitter, RedisDocumentWriter redisDocumentWriter) {
        this.documentReader = documentReader;
        this.textSplitter = textSplitter;
        this.redisDocumentWriter = redisDocumentWriter;
    }

    public void executePipeline() {
        LOGGER.info("RunIngestion() started");
        redisDocumentWriter.accept(textSplitter.apply(documentReader.get()));    // ETL Pipeline
        LOGGER.info("RunIngestion() finished");

    }
}

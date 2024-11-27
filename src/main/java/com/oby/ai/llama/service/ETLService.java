package com.oby.ai.llama.service;

import com.oby.ai.llama.service.etl.MyTokenTextSplitter;
import com.oby.ai.llama.service.etl.PdfDocumentReader;
import com.oby.ai.llama.service.etl.RedisDocumentWriter;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author OBY.Mike
 * Created on 27/11/24.
 */
@Service
public class ETLService {
    private final PdfDocumentReader documentReader;
    private final MyTokenTextSplitter textSplitterTransformer;
    private final RedisDocumentWriter documentWriter;

    public ETLService(PdfDocumentReader documentReader,
                              MyTokenTextSplitter textSplitterTransformer,
                              RedisDocumentWriter documentWriter) {
        this.documentReader = documentReader;
        this.textSplitterTransformer = textSplitterTransformer;
        this.documentWriter = documentWriter;
    }

    public void executePipeline() {
        // Bước 1: Extract - Đọc PDF
        List<Document> documents = documentReader.getDocsFromPdf();

        // Bước 2: Transform - Chia nhỏ nội dung
        List<Document> transformedDocuments = textSplitterTransformer.splitDocuments(documents);

        // Bước 3: Load - Lưu vào Redis Vector Store
        documentWriter.write(transformedDocuments);
    }
}

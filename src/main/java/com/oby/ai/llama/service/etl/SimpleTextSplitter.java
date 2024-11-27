package com.oby.ai.llama.service.etl;

/**
 * @author OBY.Mike
 * Created on 28/11/24.
 */
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleTextSplitter implements DocumentTransformer {
    private static final int CHUNK_SIZE = 1000; // Kích thước mỗi đoạn

    @Override
    public List<Document> apply(List<Document> documents) {
        List<Document> splitDocuments = new ArrayList<>();
        for (Document doc : documents) {
            String content = doc.getContent();
            int length = content.length();
            for (int start = 0; start < length; start += CHUNK_SIZE) {
                int end = Math.min(start + CHUNK_SIZE, length);
                String chunk = content.substring(start, end);
                Document chunkDoc = new Document(chunk, doc.getMetadata());
                splitDocuments.add(chunkDoc);
            }
        }
        return splitDocuments;
    }
}

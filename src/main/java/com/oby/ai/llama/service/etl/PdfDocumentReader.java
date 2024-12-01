package com.oby.ai.llama.service.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author OBY.Mike
 * Created on 28/11/24.
 */
@Component
public class PdfDocumentReader implements DocumentReader {

    private static final Logger log = LoggerFactory.getLogger(PdfDocumentReader.class);

    @Override
    public List<Document> get() {
        List<Document> documentList = new ArrayList<>();
        try {
            Files.newDirectoryStream(Path.of("/Users/aluha/WORK/projects/AI/AI-Backend/src/main/resources/data/"), "*.{pdf}").forEach(path -> {
                List<Document> documents = null;
                try {
                    documents = new TikaDocumentReader(new ByteArrayResource(Files.readAllBytes(path))).get()
                            .stream().peek(document -> {
                                document.getMetadata().put("source", path.getFileName());
                                log.info("Reading new document :: {}", path.getFileName());
                            }).toList();
                } catch (IOException e) {
                    throw new RuntimeException("Error while reading the file : " + path.toUri() + "::" + e);
                }
                documentList.addAll(documents);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return documentList;
    }
//    public List<Document> getDocsFromPdf() {
//        // Khởi tạo PagePdfDocumentReader với file PDF và cấu hình tùy chỉnh
//        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
//                "/Users/aluha/WORK/projects/AI/AI-Backend/src/main/resources/sample1.pdf", // Đường dẫn tới tệp PDF
//                PdfDocumentReaderConfig.builder()
//                        .withPageTopMargin(0) // Không bỏ lề trên
//                        .withPageExtractedTextFormatter(
//                                ExtractedTextFormatter.builder()
//                                        .withNumberOfTopTextLinesToDelete(0) // Không xóa dòng trên cùng
//                                        .build())
//                        .withPagesPerDocument(1) // Mỗi tài liệu là 1 trang PDF
//                        .build()
//        );
//
//        // Đọc nội dung từ PDF và trả về danh sách các Document
//        return pdfReader.read();
//    }
}

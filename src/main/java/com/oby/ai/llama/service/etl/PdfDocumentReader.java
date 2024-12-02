package com.oby.ai.llama.service.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
            // Sử dụng PathMatchingResourcePatternResolver để tìm tất cả các tệp trong thư mục data/
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:data/*.pdf");
            for (Resource resource : resources) {
                log.info("Processing file: {}", resource.getFilename());
                List<Document> documents = new TikaDocumentReader(resource).get();
                documentList.addAll(documents);
            }
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

package com.oby.ai.llama.service.etl;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author OBY.Mike
 * Created on 28/11/24.
 */
@Component
public class PdfDocumentReader {
    public List<Document> getDocsFromPdf() {
        // Khởi tạo PagePdfDocumentReader với file PDF và cấu hình tùy chỉnh
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                "/Users/aluha/WORK/projects/AI/AI-Backend/src/main/resources/sample1.pdf", // Đường dẫn tới tệp PDF
                PdfDocumentReaderConfig.builder()
                        .withPageTopMargin(0) // Không bỏ lề trên
                        .withPageExtractedTextFormatter(
                                ExtractedTextFormatter.builder()
                                        .withNumberOfTopTextLinesToDelete(0) // Không xóa dòng trên cùng
                                        .build())
                        .withPagesPerDocument(1) // Mỗi tài liệu là 1 trang PDF
                        .build()
        );

        // Đọc nội dung từ PDF và trả về danh sách các Document
        return pdfReader.read();
    }
}

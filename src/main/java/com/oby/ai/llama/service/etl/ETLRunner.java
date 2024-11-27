package com.oby.ai.llama.service.etl;

/**
 * @author OBY.Mike
 * Created on 28/11/24.
 */
import com.oby.ai.llama.service.ETLService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ETLRunner implements CommandLineRunner {

    private final ETLService etlPipelineService;

    public ETLRunner(ETLService etlPipelineService) {
        this.etlPipelineService = etlPipelineService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting ETL Pipeline...");
        etlPipelineService.executePipeline();
        System.out.println("ETL Pipeline completed.");
    }
}


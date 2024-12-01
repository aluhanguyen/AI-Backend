package com.oby.ai.llama.config;

import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author OBY.Mike
 * Created on 24/10/24.
 */
@Configuration
public class ChatConfig {

    @Bean
    TextSplitter textSplitter() {
        return new TokenTextSplitter();
    }

}

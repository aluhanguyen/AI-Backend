package com.oby.ai.llama.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author OBY.Mike
 * Created on 24/10/24.
 */
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final OllamaChatModel chatModel;
    private final VectorStore vectorStore;


    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping(value = "/ai/generateStream")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(message)
                .withTopK(5).withSimilarityThreshold(0.5));
        System.out.println("Retrieved " + similarDocuments.size() + " similar docs.");
        String context = similarDocuments.stream().map(Document::getContent).collect(Collectors.joining("\n"));

        String systemPrompt = "Context: " + context ;
        String userPrompt = "Question: " + message + "\n\nAnswer:";
        Prompt prompt = new Prompt(new SystemMessage(systemPrompt), new UserMessage(userPrompt));
        // Trả về Flux, từng phần của câu sẽ được gửi liên tiếp tới frontend
        return chatModel.stream(prompt)
                .map(response -> response.getResult().getOutput().getContent())
                .concatWith(Flux.just("[DONE]")); // Trích xuất nội dung từng phần

    }

    @GetMapping("/ai/test")
    public Flux<String> generateStringStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Flux.fromIterable(Arrays.asList("Message 1", "Message 2", "Message 3"));
    }

}

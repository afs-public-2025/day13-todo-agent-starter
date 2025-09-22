package com.afs.restapi.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
public class SemanticQueryController {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public SemanticQueryController(ChatClient.Builder chatClientBuilder, EmbeddingModel embeddingModel) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        initializeVectorStore();
    }

    private void initializeVectorStore() {
        Document product1 = new Document(
            "The iPhone 15 Pro features a titanium design, A17 Pro chip, and advanced camera system with 3x telephoto lens. Price starts at $999.",
            Map.of(
                "category", "smartphone",
                "brand", "Apple",
                "price", "999"
            )
        );

        Document product2 = new Document(
            "Samsung Galaxy S24 Ultra offers S Pen functionality, 200MP camera, and 6.8-inch Dynamic AMOLED display. Available from $1199.",
            Map.of(
                "category", "smartphone",
                "brand", "Samsung",
                "price", "1199"
            )
        );

        Document policy1 = new Document(
            "Free shipping is available for orders over $50. Standard delivery takes 3-5 business days. Express shipping available for $15.",
            Map.of(
                "type", "shipping",
                "department", "logistics"
            )
        );

        Document support1 = new Document(
            "To return a product, contact customer service within 30 days. Items must be in original packaging and unused condition.",
            Map.of(
                "type", "return_policy",
                "department", "customer_service"
            )
        );

        vectorStore.add(List.of(product1, product2, policy1, support1));
    }

    @GetMapping(value = "/query", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> query(@RequestParam String userText) {
        return this.chatClient.prompt()
            .advisors(new QuestionAnswerAdvisor(vectorStore))
            .user(userText)
            .stream()
            .content();
    }
}

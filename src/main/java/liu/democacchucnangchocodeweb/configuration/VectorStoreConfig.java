package liu.democacchucnangchocodeweb.configuration;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pinecone.PineconeVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Value("${spring.ai.vectorstore.pinecone.apiKey}")
    private String apiKey;

    @Value("${spring.ai.vectorstore.pinecone.index-name}")
    private String index;


    @Bean
    public VectorStore pineconeVectorStore(EmbeddingModel embeddingModel) {
        return PineconeVectorStore.builder(embeddingModel)
            .apiKey(apiKey)
            .indexName(index)
            .build();
    }

}

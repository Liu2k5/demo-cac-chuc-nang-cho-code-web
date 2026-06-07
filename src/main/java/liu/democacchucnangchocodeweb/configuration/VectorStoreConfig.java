package liu.democacchucnangchocodeweb.configuration;

import io.pinecone.clients.Index;
import io.pinecone.configs.PineconeConnection;
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

    @Value("${pinecone.index-host}")
    private String indexHost;


    @Bean
    public VectorStore pineconeVectorStore(EmbeddingModel embeddingModel) {
        return PineconeVectorStore.builder(embeddingModel)
            .apiKey(apiKey)
            .indexName(index)
            .build();
    }

    // @Bean
    // public Index pineconeIndex() {
    //     io.pinecone.configs.PineconeConfig config = new io.pinecone.configs.PineconeConfig(apiKey);
    //     config.setHost(indexHost);
    //     return new Index(config, new PineconeConnection(config), index);
    // }

}

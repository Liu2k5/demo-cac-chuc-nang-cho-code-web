//package liu.democacchucnangchocodeweb.configuration;
//
//import io.pinecone.clients.Index;
//import io.pinecone.configs.PineconeConnection;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class PineconeConf {
////    @Value("${spring.ai.vectorstore.pinecone.apiKey}")
////    private String pineconeApiKey;
////    @Value("${spring.ai.vectorstore.pinecone.index-name}")
////    private String pineconeIndexName;
////    @Value("${pinecone.index-host}")
////    private String pineconeIndexHost;
////
////    @Bean
////    public Index pineconeIndex() {
////        io.pinecone.configs.PineconeConfig config = new io.pinecone.configs.PineconeConfig(pineconeApiKey);
////        config.setHost(pineconeIndexHost);
////        return new Index(config, new PineconeConnection(config), pineconeIndexName);
////    }
//
//    // các phụ thuộc thiết lập Pinecone Vector Store trong Spring Boot,
//    // bao gồm cả thư viện khách hàng Pinecone và Spring AI Starter cho Pinecone Vector Store
//
////            <dependency>
////            <groupId>org.springframework.boot</groupId>
////            <artifactId>spring-boot-starter-webmvc</artifactId>
////        </dependency>
////        <dependency>
////            <groupId>org.springframework.ai</groupId>
////            <artifactId>spring-ai-starter-vector-store-pinecone</artifactId>
////        </dependency>
////        <dependency>
////            <groupId>io.pinecone</groupId>
////            <artifactId>pinecone-client</artifactId>
////            <version>5.1.0</version>
////        </dependency>
//}

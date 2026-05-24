package liu.democacchucnangchocodeweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;


import io.pinecone.clients.Index;
import liu.democacchucnangchocodeweb.record.AiMessage;
import liu.democacchucnangchocodeweb.service.impl.CustomerService;

@Service
@SuppressWarnings("unused")
public class AiService {

    private final CustomerService customerService;
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final ChatMemory chatMemory = 
        MessageWindowChatMemory.builder()
            .maxMessages(36)
            .build();
    private final Index pineconeIndex;
    private final ChatModel chatModel;

    public AiService(
                    ChatModel chatModel,
                    VectorStore vectorStore,
                    CustomerService customerService) {
        this.customerService = customerService;
        this.vectorStore = vectorStore;
        this.pineconeIndex = null;
        this.chatModel = chatModel;

        this.chatClient = ChatClient.builder(chatModel)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        
    }

    private final static String systemPrompt = """
            tưởng tượng bạn là một a.i chatbot của một hệ thống quản lí khách hàng của một công ti. 
            công việc của bạn đơn giản là đếm số lượng tài khoản trên hệ thống.
            """;

    public List<AiMessage> ask(String conversationId, String question, List<AiMessage> conversation) {
        if (question == null || question.isBlank()) {
            throw new RuntimeException("Cau hoi khong duoc de trong");
        } else if (question.length() > 255) {
            throw new RuntimeException("Cau hoi qua dai");
        }
        if (conversation == null) {
            conversation = new ArrayList<>();
        }
        String answer = chatClient
                            .prompt(question)
                            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                            .call()
                            .content();
        conversation.add(new AiMessage("user", question));
        conversation.add(new AiMessage("assistant", answer));
        return conversation;

    }
}

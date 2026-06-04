package liu.democacchucnangchocodeweb.record;

import java.io.Serializable;

public record AiMessage(String author, String content) implements Serializable {
    
}
package coffee.ssafy.ssafee.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocketVO {
    private String userName;
    private String content;
    private String contentTime;
}

package coffee.ssafy.ssafee.domain.chat.entity;

import coffee.ssafy.ssafee.domain.room.entity.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
@Getter
@NotNull
public class Chat {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", updatable = false, nullable = false)
    @MapsId
    private Room room;

    @Column(name = "chat_content", updatable = false, nullable = false)
    private String content;

    @Column(name = "chat_created_at", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdAt;

}

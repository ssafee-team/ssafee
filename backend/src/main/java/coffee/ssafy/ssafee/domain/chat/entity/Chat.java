package coffee.ssafy.ssafee.domain.chat.entity;

import coffee.ssafy.ssafee.domain.party.entity.Party;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false, updatable = false)
    private Long id;  // 채팅방 ID

    @CreatedDate
    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;  // 채팅방 생성 시간

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", nullable = false, updatable = false)
    private Party party;  // 채팅방이 속한 party_id

    @NotNull
    @Column(name = "name", nullable = false, updatable = false)
    private String userName;  // 해당 채팅방의 유저 이름

    @NotNull
    @Column(nullable = false, updatable = false)
    private String content;  // 유저가 입력한 채팅 내용

    @NotNull
    @Column(nullable = false, updatable = false)
    private String contentTime;  // 유저가 입력한 채팅 시간

    public void prepareCreation(Party party, String userName, String content, String contentTime) {
        this.party = party;
        this.userName = userName;
        this.content = content;
        this.contentTime = contentTime;
    }

    public void updateChat(String userName, String content, String contentTime) {
        this.userName = userName;
        this.content = content;
        this.contentTime = contentTime;
    }
}

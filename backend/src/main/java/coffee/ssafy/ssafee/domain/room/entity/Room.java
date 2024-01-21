package coffee.ssafy.ssafee.domain.room.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Getter
@NotNull
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String name;

    @Column(name = "room_generation", nullable = false)
    private Integer generation;

    @Column(name = "room_classroom", nullable = false)
    private Integer classroom;

    @Column(name = "room_deadline_at", nullable = false)
    private LocalDateTime deadlineAt;

    @CreatedDate
    @Column(name = "room_created_at", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "room_access_code", updatable = false, nullable = false)
    private String accessCode;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", updatable = false, nullable = false)
    private Shop shop;

}

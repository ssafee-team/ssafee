package coffee.ssafy.ssafee.domain.room.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "`room`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Room extends BaseTimeEntity {

    @Id
    @Column(length = 10, updatable = false)
    private String id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer generation;

    @NotNull
    @Column(nullable = false)
    private Integer classroom;

    @NotNull
    @Column(nullable = false)
    private Instant lastOrderTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY, optional = false)
    private Creator creator;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<ChoiceMenu> choiceMenus;

}

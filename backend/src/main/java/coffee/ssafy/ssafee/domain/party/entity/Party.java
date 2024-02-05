package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.party.dto.request.CreatorRequest;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import coffee.ssafy.ssafee.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parties", indexes = {
        @Index(name = "idx_access_code", columnList = "access_code", unique = true)
})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Party extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true, updatable = false)
    private String accessCode;

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
    private LocalDateTime lastOrderTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "party", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Creator creator;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY)
    private List<Participant> participants;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

    public void prepareCreation(String accessCode, Shop shop, User user, CreatorRequest creatorRequest) {
        this.accessCode = accessCode;
        this.shop = shop;
        this.user = user;
        this.creator = Creator.builder()
                .name(creatorRequest.name())
                .email(creatorRequest.email())
                .bank(creatorRequest.bank())
                .account(creatorRequest.account())
                .party(this)
                .build();
    }

    public void updateLastOrderTime(LocalDateTime now) {
        this.lastOrderTime = now;
    }

}

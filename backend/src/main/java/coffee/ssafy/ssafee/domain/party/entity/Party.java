package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parties", indexes = {
        @Index(name = "idx_party_access_code", columnList = "party_access_code", unique = true)
})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Party extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "party_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "party_generation", nullable = false)
    private Integer generation;

    @NotNull
    @Column(name = "party_classroom", nullable = false)
    private Integer classroom;

    @NotNull
    @Column(name = "party_last_order_time", nullable = false)
    private LocalDateTime lastOrderTime;

    @NotNull
    @Column(name = "party_access_code", updatable = false, nullable = false)
    private String accessCode;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", updatable = false, nullable = false)
    private Shop shop;

    @OneToOne(mappedBy = "party", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Creator creator;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY)
    private List<Participant> participants;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

    @OneToOne(mappedBy = "party", fetch = FetchType.LAZY)
    private OrderResult orderResult;

}

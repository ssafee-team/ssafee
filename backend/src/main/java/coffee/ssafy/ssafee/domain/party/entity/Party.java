package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parties", indexes = {
        @Index(name = "idx_party_access_code", columnList = "party_access_code", unique = true)
})
@Getter
@NotNull
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id", nullable = false)
    private Long id;

    @Column(name = "party_name", nullable = false)
    private String name;

    @Column(name = "party_generation", nullable = false)
    private Integer generation;

    @Column(name = "party_classroom", nullable = false)
    private Integer classroom;

    @Column(name = "party_last_order_time", nullable = false)
    private LocalDateTime lastOrderTime;

    @Column(name = "party_access_code", updatable = false, nullable = false)
    private String accessCode;

    @CreatedDate
    @Column(name = "party_created_time", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", updatable = false, nullable = false)
    private Shop shop;

    @Null
    @OneToOne(mappedBy = "party", fetch = FetchType.LAZY)
    private Creator creator;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY)
    private List<Participant> participants;

    @OneToMany(mappedBy = "party", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

    @Null
    @OneToOne(mappedBy = "party", fetch = FetchType.LAZY)
    private OrderResult orderResult;

}

package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "parties")
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

    @CreatedDate
    @Column(name = "party_created_time", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "party_access_code", updatable = false, nullable = false)
    private String accessCode;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", updatable = false, nullable = false)
    private Shop shop;

}

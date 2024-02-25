package coffee.ssafy.ssafee.domain.order.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.room.entity.Room;
import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "`order`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private Instant confirmedAt;

    @Column
    private Instant rejectedAt;

    @Column
    private Instant madeAt;

    @Column
    private Instant deliveredAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false, updatable = false)
    private Room room;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

    public void updateConfirm() {
        this.confirmedAt = Instant.now();
    }

    public void updateReject() {
        this.rejectedAt = Instant.now();
    }

    public void updateMake() {
        this.madeAt = Instant.now();
    }

    public void updateDeliver() {
        this.deliveredAt = Instant.now();
    }

}

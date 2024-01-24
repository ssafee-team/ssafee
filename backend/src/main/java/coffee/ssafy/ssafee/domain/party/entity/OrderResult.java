package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_results")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OrderResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_result_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "order_result_delivery_fee", nullable = false)
    private Integer deliveryFee;

    @Column(name = "order_result_delivery_expected_time")
    private LocalDateTime deliveryExpectedTime;

    @Column(name = "order_result_delivery_arrived_time")
    private LocalDateTime deliveryArrivedTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", updatable = false, nullable = false)
    private Party party;

}

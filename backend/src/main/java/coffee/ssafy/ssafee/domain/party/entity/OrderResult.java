package coffee.ssafy.ssafee.domain.party.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_results")
@Getter
@NotNull
public class OrderResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_result_id", nullable = false)
    private Long id;

    @Column(name = "order_result_delivery_fee", nullable = false)
    private Integer deliveryFee;

    @Null
    @Column(name = "order_result_delivery_expected_time")
    private LocalDateTime deliveryExpectedTime;

    @Null
    @Column(name = "order_result_delivery_arrived_time")
    private LocalDateTime deliveryArrivedTime;

    @CreatedDate
    @Column(name = "order_result_created_time", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", updatable = false, nullable = false)
    private Party party;

}

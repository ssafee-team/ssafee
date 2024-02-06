package coffee.ssafy.ssafee.domain.party.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Boolean confirmed;

    @NotNull
    @Column(nullable = false)
    private Boolean rejected;

    @NotNull
    @Column(nullable = false)
    private Boolean maked;

    @NotNull
    @Column(nullable = false)
    private Boolean delivered;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", nullable = false, updatable = false)
    private Party party;

}

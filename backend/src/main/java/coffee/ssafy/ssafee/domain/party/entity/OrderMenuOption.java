package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Option;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_menu_options")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class OrderMenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_option_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_menu_option_category_id", nullable = false, updatable = false)
    private OrderMenuOptionCategory orderMenuOptionCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false, updatable = false)
    private Option option;

}

package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "order_menu_option_categories")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OrderMenuOptionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_option_category_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_menu_id", nullable = false, updatable = false)
    private OrderMenu orderMenu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_category_id", nullable = false, updatable = false)
    private OptionCategory optionCategory;

    @Setter
    @OneToMany(mappedBy = "orderMenuOptionCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderMenuOption> orderMenuOptions;

}

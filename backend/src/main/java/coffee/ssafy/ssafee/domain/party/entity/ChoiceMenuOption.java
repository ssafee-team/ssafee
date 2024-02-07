package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Option;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "choice_menu_options")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ChoiceMenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_menu_option_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_menu_option_category_id", nullable = false, updatable = false)
    private ChoiceMenuOptionCategory choiceMenuOptionCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false, updatable = false)
    private Option option;

}

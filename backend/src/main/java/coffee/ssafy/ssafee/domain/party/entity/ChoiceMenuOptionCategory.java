package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "choice_menu_option_categories")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ChoiceMenuOptionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_menu_option_category_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_menu_id", nullable = false, updatable = false)
    private ChoiceMenu choiceMenu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_category_id", nullable = false, updatable = false)
    private OptionCategory optionCategory;

    @OneToMany(mappedBy = "choiceMenuOptionCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChoiceMenuOption> choiceMenuOptions;

}

package coffee.ssafy.ssafee.domain.room.entity;

import coffee.ssafy.ssafee.domain.shop.entity.OptionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "`choice_option_category`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ChoiceOptionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_menu_id", nullable = false)
    private ChoiceMenu choiceMenu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_category_id", nullable = false)
    private OptionCategory optionCategory;

    @OneToMany(mappedBy = "choiceOptionCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChoiceOption> choiceOptions;

}

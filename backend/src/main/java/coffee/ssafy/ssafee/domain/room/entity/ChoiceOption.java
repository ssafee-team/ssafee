package coffee.ssafy.ssafee.domain.room.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Option;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`choice_option`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class ChoiceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false, updatable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_option_category_id", nullable = false, updatable = false)
    private ChoiceOptionCategory choiceOptionCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_id", nullable = false, updatable = false)
    private Option option;

}

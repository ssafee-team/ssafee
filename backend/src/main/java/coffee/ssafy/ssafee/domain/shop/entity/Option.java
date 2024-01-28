package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "options")
@SQLDelete(sql = "UPDATE options SET deleted = true WHERE option_id = ?")
@SQLRestriction("deleted = false")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String name;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Integer price;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_category_id", nullable = false)
    private OptionCategory optionCategory;

}

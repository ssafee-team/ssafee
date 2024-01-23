package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "option_categories")
@SQLRestriction("option_category_deleted = false")
@Getter
@NotNull
public class OptionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_category_id", nullable = false)
    private Long id;

    @Column(name = "option_categroy_name", updatable = false, nullable = false)
    private String name;

    @Column(name = "option_category_deleted", nullable = false)
    private Boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "option_categories_options",
            joinColumns = @JoinColumn(name = "option_category_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Option> options;

}

package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "menus")
@SQLRestriction("menu_deleted = false")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "menu_name", updatable = false, nullable = false)
    private String name;

    @NotNull
    @Column(name = "menu_price", updatable = false, nullable = false)
    private Integer price;

    @Column(name = "menu_image")
    private String image;

    @Column(name = "menu_deleted", nullable = false)
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_category_id", nullable = false)
    private MenuCategory menuCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menus_option_categories",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "option_category_id")
    )
    private List<OptionCategory> optionCategories;

}

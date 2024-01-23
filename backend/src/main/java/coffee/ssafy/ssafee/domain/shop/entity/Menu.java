package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "menus")
@SQLRestriction("menu_deleted = false")
@Getter
@NotNull
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false)
    private Long id;

    @Column(name = "menu_name", updatable = false, nullable = false)
    private String name;

    @Column(name = "menu_price", updatable = false, nullable = false)
    private Integer price;

    @Null
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

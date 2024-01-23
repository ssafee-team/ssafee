package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "menu_categories")
@SQLRestriction("menu_category_deleted = false")
@Getter
@NotNull
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_category_id", nullable = false)
    private Long id;

    @Column(name = "menu_category_name", nullable = false)
    private String name;

    @Column(name = "menu_category_deleted", nullable = false)
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", updatable = false, nullable = false)
    private Shop shop;

    @OneToMany(mappedBy = "menuCategory")
    private List<Menu> Menus;

}

package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "menus")
@SQLDelete(sql = "UPDATE menus SET deleted = true WHERE menu_id = ?")
@SQLRestriction("deleted = false")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer price;

    @Column
    private String image;

    @Column
    private String description;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_category_id", nullable = false)
    @Setter
    private MenuCategory menuCategory;

    // Shop 엔티티에 대한 참조 추가
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    @Setter
    private Shop shop;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menus_option_categories",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "option_category_id")
    )
    private List<OptionCategory> optionCategories;

    public void updateMenu(MenuRequest menuRequest) {
        this.name = menuRequest.name();
        this.description = menuRequest.description();
        this.price = menuRequest.price();
        this.image = menuRequest.image();
    }

}

package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.shop.dto.request.MenuRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "`menu`")
@SQLDelete(sql = "UPDATE `menu` SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @NotNull
    @Column(nullable = false)
    private Integer price;

    @Column
    private String imageUrl;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean soldOut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_category_id", nullable = false)
    private MenuCategory menuCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_option_category",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "option_category_id")
    )
    private List<OptionCategory> optionCategories;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    public void update(MenuRequest menuRequest) {
        this.name = menuRequest.name();
        this.description = menuRequest.description();
        this.price = menuRequest.price();
        this.soldOut = menuRequest.soldOut();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

@Entity
@Table(name = "menus")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", updatable = false, nullable = false)
    private Shop shop;

    @Column(name = "menu_deleted", nullable = false)
    private Boolean deleted;

}

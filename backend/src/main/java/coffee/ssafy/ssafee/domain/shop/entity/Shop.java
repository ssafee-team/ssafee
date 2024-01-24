package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "shops")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@NotNull
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "shop_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "shop_address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "shop_phone")
    private String phone;

    @Column(name = "shop_image")
    private String image;

    @Column(name = "shop_deleted", nullable = false)
    private Boolean deleted;

    @NotNull
    @Column(name = "shop_enabled_auto_order", nullable = false)
    private Boolean enabledAutoOrder;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<MenuCategory> menuCategories;

}

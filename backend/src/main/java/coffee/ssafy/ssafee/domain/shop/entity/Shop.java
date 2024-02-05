package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.domain.manager.entity.Manager;
import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "shops")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    private String phone;

    @Column
    private String image;

    @NotNull
    @Column(nullable = false)
    private Boolean enabledAutoOrder;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<MenuCategory> menuCategories;

    @OneToOne(mappedBy = "shop", fetch = FetchType.LAZY)
    private Manager manager;

    public void update(ShopRequest shopRequest) {
        this.name = shopRequest.name();
        this.address = shopRequest.address();
        this.phone = shopRequest.phone();
        this.enabledAutoOrder = shopRequest.enabledAutoOrder();
    }

    public void updateImage(String image) {
        this.image = image;
    }

}

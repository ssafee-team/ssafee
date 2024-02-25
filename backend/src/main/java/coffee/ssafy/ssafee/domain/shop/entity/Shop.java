package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.shop.dto.request.ShopRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "`shop`")
@SQLDelete(sql = "UPDATE `shop` SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Shop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String phone;

    @NotNull
    @Column(nullable = false)
    private String address;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private Boolean enabledOrder;

    @NotNull
    @Column(nullable = false)
    private Integer minimumPrice;

    @NotNull
    @Column(nullable = false)
    private Boolean closed;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<MenuCategory> menuCategories;

    public void update(ShopRequest shopRequest) {
        this.name = shopRequest.name();
        this.address = shopRequest.address();
        this.phone = shopRequest.phone();
        this.enabledOrder = shopRequest.enabledOrder();
        this.minimumPrice = shopRequest.minimumPrice();
        this.closed = shopRequest.closed();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

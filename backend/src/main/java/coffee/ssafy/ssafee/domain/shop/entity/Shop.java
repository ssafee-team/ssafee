package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.domain.manager.entity.Manager;
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
@Setter // for ShopMapper.updateFromDto
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

}

package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

@Entity
@Table(name = "shops")
@Getter
@NotNull
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id", nullable = false)
    private Long id;

    @Column(name = "shop_name", nullable = false)
    private String name;

    @Column(name = "shop_address", nullable = false)
    private String address;

    @Null
    @Column(name = "shop_phone")
    private String phone;

    @Null
    @Column(name = "shop_image")
    private String image;

    @Column(name = "shop_deleted", nullable = false)
    private Boolean deleted;

}

package coffee.ssafy.ssafee.domain.manager.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Shop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "managers")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "manager_pw", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

}

package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "managers")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Managers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id", nullable = false)
    private String id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_pw", nullable = false)
    private String pw;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    private Shop shop;

}

package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "options")
@SQLDelete(sql = "UPDATE options SET deleted = true WHERE option_id = ?")
@SQLRestriction("deleted = false")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer price;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "option_category_id", nullable = false)
    @Setter
    private OptionCategory optionCategory;

    // Shop 엔티티에 대한 참조 추가
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    @Setter
    private Shop shop;

    public void updateOption(OptionRequest optionRequest) {
        this.name = optionRequest.name();
        this.price = optionRequest.price();
    }
}

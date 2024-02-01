package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "option_categories")
@SQLDelete(sql = "UPDATE option_categories SET deleted = true WHERE option_category_id = ?")
@SQLRestriction("deleted = false")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OptionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_category_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Boolean required;

    @NotNull
    @Column(nullable = false)
    private Integer maxCount;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    @OneToMany(mappedBy = "optionCategory")
    private List<Option> options;

    // Shop 엔티티에 대한 참조 추가
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    @Setter
    private Shop shop;

    public void updateOptionCategory(OptionCategoryRequest optionCategoryRequest) {
        this.name = optionCategoryRequest.name();
        this.required = optionCategoryRequest.required();
        this.maxCount = optionCategoryRequest.maxCount();
    }

}

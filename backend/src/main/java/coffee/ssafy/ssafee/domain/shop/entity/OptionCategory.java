package coffee.ssafy.ssafee.domain.shop.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import coffee.ssafy.ssafee.domain.shop.dto.request.OptionCategoryRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "`option_category`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE `option_category` SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@AllArgsConstructor
@Builder
@Getter
public class OptionCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @OneToMany(mappedBy = "optionCategory", fetch = FetchType.LAZY)
    private List<Option> options;

    @Column(insertable = false, nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;

    public void update(OptionCategoryRequest optionCategoryRequest) {
        this.name = optionCategoryRequest.name();
        this.required = optionCategoryRequest.required();
        this.maxCount = optionCategoryRequest.maxCount();
    }

}

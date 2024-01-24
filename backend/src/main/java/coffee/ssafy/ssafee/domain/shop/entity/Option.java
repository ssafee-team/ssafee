package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "options")
@SQLRestriction("option_deleted = false")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "option_name", updatable = false, nullable = false)
    private String name;

    @NotNull
    @Column(name = "option_price", updatable = false, nullable = false)
    private Integer price;

    @Column(name = "option_deleted", nullable = false)
    private Boolean deleted;

}

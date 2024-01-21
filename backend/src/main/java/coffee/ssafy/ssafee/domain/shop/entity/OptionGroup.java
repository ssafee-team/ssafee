package coffee.ssafy.ssafee.domain.shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "option_groups")
@Getter
@NotNull
public class OptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_group_id", nullable = false)
    private Long id;

    @Column(name = "option_group_name", updatable = false, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", updatable = false, nullable = false)
    private Menu menu;

    @Column(name = "option_group_deleted", nullable = false)
    private Boolean deleted;

}

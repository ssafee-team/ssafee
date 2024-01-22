package coffee.ssafy.ssafee.domain.room.entity;

import coffee.ssafy.ssafee.domain.shop.entity.Menu;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NotNull
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @CreatedDate
    @Column(name = "order_created_time", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "order_updated_time", insertable = false, updatable = false, nullable = false)
    private LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id", updatable = false, nullable = false)
    private Participant participant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", updatable = false, nullable = false)
    private Menu menu;

    // cascade = CascadeType.ALL
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_options",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private List<Option> options;

}

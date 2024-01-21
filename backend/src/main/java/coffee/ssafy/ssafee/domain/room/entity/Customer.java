package coffee.ssafy.ssafee.domain.room.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

@Entity
@Table(name = "customers")
@Getter
@NotNull
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @Null
    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_is_creator", nullable = false)
    private Boolean isCreator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", updatable = false, nullable = false)
    private Room room;

}

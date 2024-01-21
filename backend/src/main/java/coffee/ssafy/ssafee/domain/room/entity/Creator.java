package coffee.ssafy.ssafee.domain.room.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "creators")
@Getter
@NotNull
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id", nullable = false)
    private Long id;

    @Column(name = "creator_name", nullable = false)
    private String name;

    @Column(name = "creator_email", updatable = false, nullable = false)
    private String email;

    @Column(name = "creator_bank", nullable = false)
    private String bank;

    @Column(name = "creator_account", nullable = false)
    private String account;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", updatable = false, nullable = false)
    private Room room;

}

package coffee.ssafy.ssafee.domain.party.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "participants")
@Getter
@NotNull
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false)
    private Long id;

    @Null
    @Column(name = "participant_name")
    private String name;

    @Column(name = "participant_is_creator", nullable = false)
    private Boolean isCreator;

    @Column(name = "participant_is_carrier", nullable = false)
    private Boolean isCarrier;

    @Column(name = "participant_payed", nullable = false)
    private Boolean payed;

    @Column(name = "participant_pay_confirmed", nullable = false)
    private Boolean payConfirmed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", updatable = false, nullable = false)
    private Party party;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

}

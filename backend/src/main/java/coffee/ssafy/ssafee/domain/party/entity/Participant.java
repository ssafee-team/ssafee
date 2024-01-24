package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "participants")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Participant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false)
    private Long id;

    @NotNull
    @Null
    @Column(name = "participant_name")
    private String name;

    @NotNull
    @Column(name = "participant_is_creator", nullable = false)
    private Boolean isCreator;

    @NotNull
    @Column(name = "participant_is_carrier", nullable = false)
    private Boolean isCarrier;

    @NotNull
    @Column(name = "participant_payed", nullable = false)
    private Boolean payed;

    @NotNull
    @Column(name = "participant_pay_confirmed", nullable = false)
    private Boolean payConfirmed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", updatable = false, nullable = false)
    private Party party;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

}

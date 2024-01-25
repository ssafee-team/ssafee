package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "participant_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @NotNull
    @Column(nullable = false)
    private Boolean isCreator;

    @NotNull
    @Column(nullable = false)
    private Boolean isCarrier;

    @NotNull
    @Column(nullable = false)
    private Boolean payed;

    @NotNull
    @Column(nullable = false)
    private Boolean payConfirmed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", nullable = false, updatable = false)
    private Party party;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

}

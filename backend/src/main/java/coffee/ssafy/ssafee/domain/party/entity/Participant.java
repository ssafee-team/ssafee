package coffee.ssafy.ssafee.domain.party.entity;

import coffee.ssafy.ssafee.domain.party.dto.request.ParticipantUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Table(name = "participants")
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isCarrier;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean paid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", nullable = false, updatable = false)
    private Party party;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus;

    public void update(ParticipantUpdateRequest participantUpdateRequest) {
        this.isCarrier = participantUpdateRequest.isCarrier();
        this.paid = participantUpdateRequest.paid();
    }

}

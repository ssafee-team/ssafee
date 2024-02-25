package coffee.ssafy.ssafee.domain.room.entity;

import coffee.ssafy.ssafee.domain.room.dto.request.ParticipantUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "`participant`")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean pickedCarrier;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean paid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false, updatable = false)
    private Room room;

    public void updatePaid(ParticipantUpdateRequest participantUpdateRequest) {
        this.paid = participantUpdateRequest.paid();
    }

    public void updatePickedCarrier() {
        this.pickedCarrier = true;
    }

}

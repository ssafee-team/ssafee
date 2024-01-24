package coffee.ssafy.ssafee.domain.party.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "creators")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "creator_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "creator_name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "creator_email", updatable = false, nullable = false)
    private String email;

    @NotNull
    @Column(name = "creator_bank", nullable = false)
    private String bank;

    @NotNull
    @Column(name = "creator_account", nullable = false)
    private String account;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party_id", updatable = false, nullable = false)
    private Party party;

}

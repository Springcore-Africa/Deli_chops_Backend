package africa.springCore.delichopsbackend.core.portfolio.customer.domain.model;

import africa.springCore.delichopsbackend.core.base.domain.model.BaseEntity;
import africa.springCore.delichopsbackend.core.base.domain.model.BioData;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "instance", access = AccessLevel.PRIVATE)
public class Customer extends BaseEntity {
    @Serial
    private static final long serialVersionUID = -7466640123337613601L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BioData bioData;

}

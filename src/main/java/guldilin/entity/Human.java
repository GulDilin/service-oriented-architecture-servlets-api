package guldilin.entity;

import guldilin.dto.HumanDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name="human")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Human extends AbstractEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Override
    public HumanDTO mapToDTO() {
        return new HumanDTO(this);
    }
}

package guldilin.entity;

import guldilin.dto.CoordinatesDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Entity(name="coordinates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Coordinates extends AbstractEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "x")
    private Long x;

    @Column(name = "y")
    @Min(-667)
    private Integer y;

    @Override
    public Object mapToDTO() {
        return new CoordinatesDTO(this);
    }
}

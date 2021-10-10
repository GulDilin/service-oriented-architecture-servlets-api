package guldilin.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity(name="coordinates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Coordinates {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "x")
    private Long x;

    @Column(name = "y")
    @Min(-667)
    private Integer y;
}

package guldilin.dto;

import guldilin.entity.Coordinates;
import lombok.*;

import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDTO extends AbstractDTO {
    private Integer id;
    private Long x;
    private @Min(-667) Integer y;

    public CoordinatesDTO(Coordinates coordinates) {
        this.id = coordinates.getId();
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }
}
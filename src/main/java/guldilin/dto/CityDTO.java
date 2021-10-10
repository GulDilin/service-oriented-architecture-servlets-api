package guldilin.dto;

import guldilin.entity.Climate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private Integer id;
    private String name;
    private CoordinatesDTO coordinates;
    private Date creationDate;
    private Climate climate;
}

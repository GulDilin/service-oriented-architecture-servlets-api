package guldilin.dto;

import guldilin.entity.AbstractEntity;
import guldilin.entity.City;
import guldilin.entity.Climate;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO extends AbstractDTO {
    private Long id;
    private String name;
    private Long coordinates;
    private LocalDateTime creationDate;
    private Climate climate;
    private Integer area;
    private Integer population;
    private Float metersAboveSeaLevel;
    private Integer populationDensity;
    private Integer carCode;
    private Long governor;

    public CityDTO(City city) {
        this.id = Long.valueOf(city.getId());
        this.name = city.getName();
        this.creationDate = city.getCreationDate().toLocalDateTime();
        this.climate = city.getClimate();
        this.area = city.getArea();
        this.population = city.getPopulation();
        this.metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        this.populationDensity = city.getPopulationDensity();
        this.carCode = city.getCarCode();

        if (city.getCoordinates() != null) {
            this.coordinates = Long.valueOf(city.getCoordinates().getId());
        }
        if (city.getGovernor() != null) {
            this.governor = Long.valueOf(city.getGovernor().getId());
        }
    }
}

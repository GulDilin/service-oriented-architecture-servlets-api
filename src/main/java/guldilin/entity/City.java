package guldilin.entity;

import guldilin.dto.CityDTO;
import guldilin.utils.FilterActionType;
import guldilin.utils.FilterableField;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class City extends AbstractEntity {
    public static List<FilterableField> getFilterableFields() {
        return Arrays.asList(
                new FilterableField<>(Long.class, FilterActionType.COMPARABLE, "id", Long::parseLong),
                new FilterableField<>(String.class, FilterActionType.COMPARABLE, "name", s -> s)
        );
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = ValidationMessages.NOT_BLANK)
    private String name;

    @ManyToOne
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp creationDate;


    @Column(name = "area", nullable = false)
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Min(value = 0, message = ValidationMessages.MIN_0)
    private Integer area;

    @Column(name = "population", nullable = false)
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Min(value = 0, message = ValidationMessages.MIN_0)
    private Integer population;

    @Column(name = "meters_above_sea_level")
    private Float metersAboveSeaLevel;

    @Column(name = "population_density")
    @Min(value = 0, message = ValidationMessages.MIN_0)
    private Integer populationDensity;

    @Column(name = "car_code")
    @Min(value = 0, message = ValidationMessages.MIN_0)
    @Max(value = 1000, message = ValidationMessages.MAX_1000)
    private Integer carCode;

    @Column(name = "climate", nullable = false)
    @NotNull(message = ValidationMessages.NOT_NULL)
    @Enumerated(EnumType.STRING)
    private Climate climate;

    @ManyToOne
    @JoinColumn(name = "governor_id")
    private Human governor;

    @Override
    public CityDTO mapToDTO() {
        return new CityDTO(this);
    }
}

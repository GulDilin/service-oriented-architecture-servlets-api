package guldilin.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class City {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @Column(name = "created")
    @NotNull
    @CreationTimestamp
    private java.sql.Timestamp creationDate;

    @Column(name = "area")
    @NotNull
    @Min(0)
    private Integer area;

    @Column(name = "population")
    @NotNull
    @Min(0)
    private Integer population;

    @Column(name = "meters_above_sea_level")
    private Float metersAboveSeaLevel;

    @Column(name = "population_density")
    @Min(0)
    private Integer populationDensity;

    @Column(name = "car_code")
    @Min(0)
    @Max(1000)
    private Integer carCode;

    @Column(name = "climate")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Climate climate;

    @ManyToOne
    @JoinColumn(name = "governor_id")
    private Human governor;
}

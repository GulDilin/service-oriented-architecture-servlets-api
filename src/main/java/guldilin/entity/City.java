package guldilin.entity;

import guldilin.dto.CityDTO;
import guldilin.errors.ErrorCode;
import guldilin.errors.ErrorMessage;
import guldilin.errors.ValidationException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "city")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class City extends AbstractEntity {
    public static List<FilterableField<?>> getFilterableFields() {
        final String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return Arrays.asList(
                new FilterableField<>(Long.class, FilterActionType.COMPARABLE, "id", Long::parseLong),
                new FilterableField<>(String.class, FilterActionType.CONTAINS, "name", s -> s),
                new FilterableField<>(Date.class, FilterActionType.COMPARABLE, "creationDate",
                        s -> {
                            try {
                                return formatter.parse(s);
                            } catch (ParseException e) {
                                HashMap<String, String> errors = new HashMap<>();
                                errors.put("creationDate", ErrorCode.WRONG_DATE_FORMAT.name() + " : " + e.getMessage());
                                throw new IllegalArgumentException(new ValidationException(errors));
                            }
                        }),
                new FilterableField<>(Float.class, FilterActionType.COMPARABLE, "metersAboveSeaLevel", Float::parseFloat),
                new FilterableField<>(Integer.class, FilterActionType.COMPARABLE, "area", Integer::parseInt),
                new FilterableField<>(Integer.class, FilterActionType.COMPARABLE, "population", Integer::parseInt),
                new FilterableField<>(Integer.class, FilterActionType.COMPARABLE, "populationDensity", Integer::parseInt),
                new FilterableField<>(Integer.class, FilterActionType.COMPARABLE, "carCode", Integer::parseInt),
                new FilterableField<>(Integer.class, FilterActionType.COMPARABLE, "coordinates", Integer::parseInt),
                new FilterableField<>(Integer.class, FilterActionType.COMPARABLE, "governor", Integer::parseInt),
                new FilterableField<>(String.class, FilterActionType.EQUAL_ONLY, "climate", s -> s)
        );
    }

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = ErrorMessage.NOT_BLANK)
    private String name;

    @ManyToOne
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @Column(name = "created", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp creationDate;


    @Column(name = "area", nullable = false)
    @NotNull(message = ErrorMessage.NOT_NULL)
    @Min(value = 0, message = ErrorMessage.MIN_0)
    private Integer area;

    @Column(name = "population", nullable = false)
    @NotNull(message = ErrorMessage.NOT_NULL)
    @Min(value = 0, message = ErrorMessage.MIN_0)
    private Integer population;

    @Column(name = "meters_above_sea_level")
    private Float metersAboveSeaLevel;

    @Column(name = "population_density")
    @Min(value = 0, message = ErrorMessage.MIN_0)
    private Integer populationDensity;

    @Column(name = "car_code")
    @Min(value = 0, message = ErrorMessage.MIN_0)
    @Max(value = 1000, message = ErrorMessage.MAX_1000)
    private Integer carCode;

    @Column(name = "climate", nullable = false)
    @NotNull(message = ErrorMessage.NOT_NULL)
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

package guldilin.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @NotBlank(message = "")
    private String name;


    @ManyToOne
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates;

    @NotNull
    @Column(name="created")
    @CreationTimestamp
    private java.sql.Timestamp creationDate;

    @Enumerated(EnumType.STRING)
    private Climate climate;
}

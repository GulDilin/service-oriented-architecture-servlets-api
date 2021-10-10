package guldilin.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="human")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Human {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "birthday")
    private LocalDate x;
}

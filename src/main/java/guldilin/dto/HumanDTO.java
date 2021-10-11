package guldilin.dto;

import guldilin.entity.Human;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HumanDTO extends AbstractDTO {
    private Integer id;
    private LocalDate birthday;

    public HumanDTO(Human human) {
        this.id = human.getId();
        this.birthday = human.getBirthday();
    }
}

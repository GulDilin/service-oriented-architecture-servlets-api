package guldilin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity implements Mappable, Filterable {
    private List<String> filterableFields;
}

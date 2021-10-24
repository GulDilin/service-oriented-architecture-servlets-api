package guldilin.entity;

import guldilin.utils.FilterableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
public abstract class AbstractEntity implements Mappable, Filterable {
    public static List<FilterableField> getFilterableFields() {
        return Collections.emptyList();
    }
}

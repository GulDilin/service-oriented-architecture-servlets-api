package guldilin.utils;

import guldilin.utils.FilterActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterableField {
    private FilterActionType actionType;
    private String name;
}

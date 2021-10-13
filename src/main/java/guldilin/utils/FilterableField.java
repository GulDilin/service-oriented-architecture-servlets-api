package guldilin.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterableField<T> {
    private FilterActionType actionType;
    private String name;
    private Function<String, T> parser = s -> (T) s;
}

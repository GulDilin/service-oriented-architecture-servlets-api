package guldilin.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterType {
    LESS_THAN("lt"),
    GREATER_THAN("gt"),
    EQUALS("eq"),
    CONTAINS("in");

    private final String key;
}

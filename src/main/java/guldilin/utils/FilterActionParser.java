package guldilin.utils;

import guldilin.errors.FilterTypeNotFound;

import java.util.Arrays;

public class FilterActionParser {
    public static FilterAction parse(String actionString) throws FilterTypeNotFound {
        String [] parts = actionString.split(":", 2);
        FilterType filterType;
        if (parts.length < 2) {
            filterType = FilterType.EQUALS;
        } else {
            filterType = Arrays.stream(FilterType.values())
                    .filter(e -> e.getKey().equals(parts[0]))
                    .findFirst()
                    .orElseThrow(FilterTypeNotFound::new);
            // TODO: add check FilterActionType
        }
        return FilterAction.builder().filterType(filterType).build();
    }
}

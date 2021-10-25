package guldilin.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class ValidationException extends Exception{
    private HashMap<String, String> fieldsErrors;
}

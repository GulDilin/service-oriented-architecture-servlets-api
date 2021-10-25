package guldilin.errors;

public class UnsupportedContentType extends Exception {
    public UnsupportedContentType() {
        super("Method not supported");
    }
}

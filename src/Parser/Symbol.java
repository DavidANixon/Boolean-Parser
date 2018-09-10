package Parser;

public interface Symbol {
    public Type getType();

    BooleanList toList();
}

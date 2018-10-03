package Parser;

import java.util.Optional;

public interface Symbol {
    Type getType();

    BooleanList toList();

    long complexity();

    Symbol simplified();

    Optional<Symbol> subterm(Symbol symbol);
}

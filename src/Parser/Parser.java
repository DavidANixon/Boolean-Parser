package Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Parser{

    private static final List<Type> VARIABLE_PATTERN = Arrays.asList(Type.VARIABLE);
    private static final List<Type> EXPRESSION_PERENTHESIS_PATTERN = Arrays.asList(Type.OPEN, Type.EXPRESSION, Type.CLOSE);
    private static final List<Type> NOT_TERM_PATTERN = Arrays.asList(Type.NOT, Type.TERM);
    private static final List<Type> TERM_PATTERN = Arrays.asList(Type.TERM);
    private static final List<Type> EXPRESSION_AND_PATTERN = Arrays.asList(Type.EXPRESSION, Type.AND, Type.EXPRESSION);
    private static final List<Type> EXPRESSION_OR_PATTERN = Arrays.asList(Type.EXPRESSION, Type.OR, Type.EXPRESSION);


    public static final State parse(BooleanList input) {
        List<Symbol> workingList = new ArrayList<Symbol>();
        for (Symbol symbol : input) {
            workingList.add(symbol);

            if ()
        }
    }
}

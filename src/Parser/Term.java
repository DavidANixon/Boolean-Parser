package Parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public final class Term extends AbstractTreeSymbol{

    private static final HashSet<Type> EXPECTED_TERM_TYPES = new HashSet<>(List.of(Type.VARIABLE, Type.EXPRESSION));
    private final Symbol term;
    private final Type type;

    private Term (Symbol subexpression) {
        super(subexpression.getType());
        type = Type.TERM;
        term = subexpression;
    }

    final Symbol getTerm() {
        return term;
    }

    @Override
    public Type getType() {
        return type;
    }

    public static final Term build (Symbol subexpression) {
        validateSubexpression(subexpression,
                EXPECTED_TERM_TYPES,
                "This is being called from the Term build class");
        return new Term(subexpression);
    }

    public final BooleanList toList() {
        if (getTerm().getType().equals(Type.VARIABLE)) {
            return term.toList();
        }
        else if (getTerm().getType().equals(Type.EXPRESSION)){
            BooleanList booleanList = new BooleanList();
            booleanList.add(Type.OPEN);
            booleanList.add(term.toList());
            booleanList.add((Type.CLOSE));
            return booleanList;
        }
        else
            throw new UnsupportedOperationException("Cannot convert term to list.");
    }

    @Override
    public String toString() {
        return getTerm().toString();
    }

    public final long complexity() {
        return getTerm().complexity();
    }

    public final Symbol simplified() {
        Symbol simplifiedTerm = this;
        Symbol comparisonTerm;

//        if (subterm().isPresent())
//            return simplified();
//
//        else
//            return Term.build(term);


        boolean isSimplificationInProgress = true;
        while (isSimplificationInProgress) {
//            this.subterm().get();
            comparisonTerm = simplifiedTerm;
            simplifiedTerm = subterm(simplifiedTerm).get();
            System.out.println(simplifiedTerm.toList().getListRepresentation());
            System.out.println(comparisonTerm.toList().getListRepresentation());
            System.out.println();

            isSimplificationInProgress = !simplifiedTerm.toString().equals(comparisonTerm.toString());
        }

        return simplifiedTerm;
    }

    @Override
    public final Optional<Symbol> subterm(Symbol termSub) {
        List<ListSymbol> listRep = termSub.toList().getListRepresentation();

        if (hasExtraParentheses(listRep)) {
            System.out.println(listRep);
            return Optional.of(removeFirstAndLastIndexOfTerm(listRep));
        }
        else
            return Optional.of(Term.build(Parser.parse(termSub.toList()).getExpression().get(0)));
    }

    private boolean hasExtraParentheses (List<ListSymbol> listRep) {
//        if (isPairOfEmptyParentheses(listRep));

        if (hasDoubleOpenParenthesesAtStart(listRep)
                && hasDoubleCloseParenthesesAtEnd(listRep)) {
            return true;
        }
        return false;
    }

    private boolean hasDoubleOpenParenthesesAtStart(List<ListSymbol> listRep) {

        if (listRep.size() < 2)
            return false;

        return (listRep.get(0).getType().equals(Type.OPEN)
                && listRep.get(1).getType().equals(Type.OPEN));
    }

    private boolean hasDoubleCloseParenthesesAtEnd(List<ListSymbol> listRep) {
        if (listRep.size() < 2)
            return false;

        return (listRep.get(listRep.size()-2).getType().equals(Type.CLOSE)
                && listRep.get(listRep.size()-1).getType().equals(Type.CLOSE) );
    }

//    private boolean isPairOfEmptyParentheses(List<ListSymbol> listRep) {
//
//        if (listRep.size() < 2)
//            return false;
//
//        return (listRep.get(0).getType().equals(Type.OPEN)
//            && listRep.get(1).getType().equals(Type.CLOSE));
//    }

    private Term removeFirstAndLastIndexOfTerm(List<ListSymbol> listRep) {

        BooleanList newBooleanList = new BooleanList();
        for(int i = 2; i < listRep.size()-2; i++) {
            newBooleanList.add(listRep.get(i).toList());
            System.out.println(listRep.get(i).toList());
        }
//        System.out.println();

        return Term.build(Parser.parse(newBooleanList).getExpression().get(0));
    }
}

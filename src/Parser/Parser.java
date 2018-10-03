package Parser;

import java.util.*;

public final class Parser{

    private static final LinkedHashSet<Reduction> reductionSet = new LinkedHashSet<Reduction>();

    private static final void populateReductionsSet() {
        for (Pattern pattern : Pattern.values()) {
            reductionSet.add(Reduction.build(pattern.getTypeList(), pattern.getReductionFunction()));
        }
    }

    /**
     * use this method to parse a boolean list and apply type reductions until it reaches a single expression
     * @param input the boolean list to be reduced
     * @return the state with the reduced list as its expression
     */
    public static final State parse(BooleanList input) {
        List<Symbol> workingList = new ArrayList<Symbol>();
        boolean isParseInProgress;

        Parser.populateReductionsSet();

        for (Symbol symbol : input) {
            workingList.add(symbol);

            List<Symbol> tempComparisonList;
            isParseInProgress = true;
            while (isParseInProgress) {
                tempComparisonList = workingList;
                workingList = applyRelevantReductions(tempComparisonList);
                isParseInProgress = (!tempComparisonList.equals(workingList));
            }
        }

        return State.build(workingList);
    }

    // Scans through all reductions and applies all that are matches to the list
    private static final List<Symbol> applyRelevantReductions(List<Symbol> workingList) {
        for (Reduction reduction : reductionSet) {
            if (symbolListMatchesReduction(reduction, getSubListOfReducibleSymbols(reduction, workingList))) {
                workingList = reduceLastSymbols(reduction, workingList);
            }
        }
        return workingList;
    }

    private static final boolean symbolListMatchesReduction (Reduction reduction, List<Symbol> symbolList) {
        if (symbolList.size() >= reduction.size()) {
            return reduction.matches(Parser.convertSymbolListToTypeList(getSubListOfReducibleSymbols(reduction, symbolList)));
        }
        else
            return false;
    }

    // replaces the last few symbols with the result of applying the reduction.
    private static final List<Symbol> reduceLastSymbols(Reduction reduction, List<Symbol> workingList) {
        ListIterator<Symbol> listIterator = getSubListOfReducibleSymbols(reduction, workingList).listIterator();

        Symbol reducedSymbol = reduction.apply(getSubListOfReducibleSymbols(reduction, workingList));

        while (listIterator.hasNext()) {
            listIterator.next();
            listIterator.remove();
        }

        workingList.add(reducedSymbol);
        return workingList;
    }

    private static final List<Symbol> getSubListOfReducibleSymbols(Reduction reduction, List<Symbol> symbolList) {
        if (symbolList.size() >= reduction.size()) {
            return symbolList.subList(
                    symbolList.size() - reduction.size(),
                    symbolList.size());
        }
        else return Collections.emptyList();
    }

    private static final List<Type> convertSymbolListToTypeList(List<Symbol> symbolList) {
        List<Type> convertedTypeList = new ArrayList<Type>(); // good place for stream
        for (Symbol symbol : symbolList) {
            convertedTypeList.add(symbol.getType());
        }
        return convertedTypeList;
    }

}

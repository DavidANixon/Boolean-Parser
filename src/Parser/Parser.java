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
        boolean isParseInProgress = true;

        Parser.populateReductionsSet();

        for (Symbol symbol : input) { //stream
            workingList.add(symbol);
        }

        List<Symbol> tempComparisonList;
        while (isParseInProgress) {
            tempComparisonList = workingList;
            workingList = parseThroughList(tempComparisonList);
            isParseInProgress = (!tempComparisonList.equals(workingList));

        }

        return State.build(workingList);
    }

    private static final List<Symbol> parseThroughList(List<Symbol> workingList) {
        List<Symbol> newWorkingList = new ArrayList<Symbol>();

        for(Symbol symbol : workingList) {
            newWorkingList.add(symbol);
            newWorkingList = applyRelevantReductions(newWorkingList);
        }
        return newWorkingList;
    }

    // Scans through all reductions and applies all that are matches to the list
    private static final List<Symbol> applyRelevantReductions(List<Symbol> workingList) {
        for (Reduction reduction : reductionSet) {
            if (symbolListMatchesReduction(workingList, reduction)) {
                workingList = reduceLastSymbols(workingList, reduction);
            }
        }
        return workingList;
    }

    private static final boolean symbolListMatchesReduction (List<Symbol> symbolList, Reduction reduction) {
        if (symbolList.size() >= reduction.size()) {
            return reduction.matches(Parser.convertSymbolListToTypeList(getSubListOfLastSymbols(symbolList, reduction)));
        }
        else
            return false;
    }

    // replaces the last few symbols with the result of applying the reduction.
    private static final List<Symbol> reduceLastSymbols(List<Symbol> workingList, Reduction reduction) {
        List<Symbol> newWorkingList = new ArrayList<Symbol>();
        newWorkingList.addAll(getSubListOfFirstSymbols(workingList, reduction));
        newWorkingList.add(reduction.apply(getSubListOfLastSymbols(workingList, reduction)));

        return newWorkingList;
    }

    private static final List<Symbol> getSubListOfLastSymbols (List<Symbol> symbolList, Reduction reduction) {
        return symbolList.subList(
                symbolList.size() - reduction.size(),
                symbolList.size());
    }

    private static final List<Symbol> getSubListOfFirstSymbols(List<Symbol> symbolList, Reduction reduction) {
        if (symbolList.size() == reduction.size())
            return new ArrayList<Symbol>();
        return symbolList.subList(0, symbolList.size() - reduction.size());
    }

    private static final List<Type> convertSymbolListToTypeList(List<Symbol> symbolList) {
        List<Type> convertedTypeList = new ArrayList<Type>();
        for (Symbol symbol : symbolList) {
            convertedTypeList.add(symbol.getType());
        }
        return convertedTypeList;
    }

}

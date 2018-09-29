package Parser;

import java.util.*;

public final class Parser{


    private static final HashSet<Reduction> reductionSet = new HashSet<Reduction>();

    // USE A STATIC BLOCK
    private static final void populateReductionsSet() {
        for (Pattern pattern : Pattern.values()) {
            reductionSet.add(Reduction.build(pattern.getTypeList(), pattern.getReductionFunction()));
        }
    }

    private static final List<Symbol> getSubListOfLastSymbols (List<Symbol> symbolList, Reduction reduction) {
        return symbolList.subList(
                symbolList.size() - reduction.size(),
                symbolList.size());
    }

    private static final List<Symbol> getSubListOfFirstSymbols(List<Symbol> symbolList, Reduction reduction) {
        return symbolList.subList(0, symbolList.size() - reduction.size());
    }

    private static final List<Symbol> replaceLastSymbols(List<Symbol> workingList, Reduction reduction) {
        List<Symbol> newWorkingList = new ArrayList<Symbol>();
        newWorkingList.addAll(getSubListOfFirstSymbols(workingList, reduction));
        newWorkingList.add(reduction.apply(getSubListOfLastSymbols(workingList, reduction)));
        return newWorkingList;
    }

    private static final boolean symbolListMatchesReduction (List<Symbol> symbolList, Reduction reduction) {
        if (symbolList.size() >= reduction.size()) {
            return reduction.matches(Parser.convertSymbolListToTypeList(getSubListOfLastSymbols(symbolList, reduction)));
        }
        else
            return false;
    }

    private static final List<Type> convertSymbolListToTypeList(List<Symbol> symbolList) {
        List<Type> convertedTypeList = new ArrayList<Type>();
        for (Symbol symbol : symbolList) {
            convertedTypeList.add(symbol.getType());
        }
        return convertedTypeList;
    }

    private static final List<Symbol> applyRelevantReductions(List<Symbol> workingList) {
        for (Reduction reduction : reductionSet) {
            if (symbolListMatchesReduction(workingList, reduction)) {
                workingList = replaceLastSymbols(workingList, reduction);
            }
        }
        return workingList;
    }

    private static final List<Symbol> parseThroughList(List<Symbol> workingList) {
        for (Symbol symbol : workingList) {
            workingList = applyRelevantReductions(workingList);
        }
        return workingList;
    }

    public static final State parse(BooleanList input) {
        List<Symbol> workingList = new ArrayList<Symbol>();
        boolean isParseInProgress = true;
        Parser.populateReductionsSet();

        for (Symbol symbol : input) {
            workingList.add(symbol);
        }

        List<Symbol> comparisonList;
        while (isParseInProgress) {
            comparisonList = workingList;
            workingList = parseThroughList(workingList);
            isParseInProgress = (!comparisonList.equals(workingList));
        }

        return State.build(workingList);
    }
}

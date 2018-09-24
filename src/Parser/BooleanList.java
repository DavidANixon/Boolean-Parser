package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public final class BooleanList implements Iterable<ListSymbol> {

    private final List<ListSymbol> listRepresentation = new ArrayList<>();
    private boolean isFrozen = false;

    public final List<ListSymbol> getListRepresentation() {
        return List.copyOf(listRepresentation);
    }

    public Iterator<ListSymbol> iterator()
    {
        return getListRepresentation().iterator();
    }

    /**
     * Use this method to add a new List Symbol to this list.
     * Cannot add to the list if it has been frozen. This will throw an exception.
     * @param listSymbol The List Symbol to be added
     * @return A boolean representing whether the operation changed the list
     */
    public final Boolean add(ListSymbol listSymbol) {
        if (!isFrozen)
            return listRepresentation.add(listSymbol);
        else
            throw new UnsupportedOperationException("This list has been frozen and can no longer be modified.");
    }

    /**
     * A wrapper for the add method for convenience in adding Connectors.
     * This method builds a new Connector and adds it to the list.
     * @param type The type of the Connector to be added
     * @return A boolean representing whether the operation changed the list
     */
    public final Boolean add(Type type) {
        return this.add(Connector.build(type));
    }

    /**
     * A wrapper for the add method for convenience in adding Variables.
     * This method builds a new Connector and adds it to the list.
     * @param variableStringRepresentation The type of the Variable to be added
     * @return A boolean representing whether the operation changed the list
     */
    public final Boolean add(String variableStringRepresentation) {
        return this.add(Variable.build(variableStringRepresentation));
    }

    /**
     * A wrapper for the add method that takes another Boolean List
     * It iterates through the list and calls other add wrappers depending
     * on the type of the current symbol
     * @param booleanList the new list to be added to this Boolean List
     * @return A boolean representing whether the operation changed the list
     */
    public final Boolean add(BooleanList booleanList) {
        Symbol currentSymbol;
        BooleanList tempList = booleanList;
        Iterator<ListSymbol> iterator = booleanList.iterator();

        while (iterator.hasNext()) {
            currentSymbol = iterator.next();
            if (currentSymbol.getType().equals(Type.VARIABLE)) {
                this.add(currentSymbol.toString());
            }
            else if (currentSymbol.getType().isValidConnectorType()) {
                this.add(currentSymbol.getType());
            }
            else
                throw new UnsupportedOperationException("Could not add list to boolean list");
        }
        return didListChange(tempList, booleanList);
    }

    /**
     * This method calculates the complexity of a List by counting the number of ORs and ANDs
     * @return a long representation of the complexity of this Boolean List
     */
    public final long complexity() {
        return listRepresentation.stream()
                .filter(Symbol -> Symbol.complexity() == 1)
                .count();
    }

    /**
     * This method iterates through the boolean list and builds a string using each Symbol's toString method.
     * @return A String representation of the elements in the list
     */
    @Override
    public String toString() {
        return listRepresentation.stream().map(Symbol::toString).collect(Collectors.joining(" "));
    }

    public final void freeze() { // could also set listRepresentation = Collections.UnmodifiableList
        isFrozen = true;
    }

    private final boolean didListChange(BooleanList tempList, BooleanList booleanList) {
        return (!tempList.equals(booleanList));
    }
}

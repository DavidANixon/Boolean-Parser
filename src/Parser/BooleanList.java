package Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BooleanList implements Iterable<ListSymbol> {

    private final List<ListSymbol> listRepresentation = new ArrayList<>();
    private boolean isFrozen = false;

    public final List<ListSymbol> getListRepresentation() {
        return listRepresentation;
    }

    public Iterator<ListSymbol> iterator()
    {
        return listRepresentation.iterator();
    }

    /**
     * Use this method to add a new List Symbol to this list.
     * Cannot add to the list if it has been frozen. This will throw an exception.
     * @param listSymbol The List Symbol to be added
     * @return A boolean representing whether the operation changed the list
     */
    public final Boolean add(ListSymbol listSymbol) {
        if (!isFrozen) {
            List<ListSymbol> temp = listRepresentation;
            listRepresentation.add(listSymbol);
            return temp.equals(listRepresentation);
        }
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
     * This method iterates through the boolean list and builds a string using each Symbol's toString method.
     * @return A String representation of the elements in the list
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator listIterator = listRepresentation.iterator();
        while (listIterator.hasNext()) {
            stringBuilder.append(listIterator.next().toString());
            stringBuilder.append(' ');
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    public final void freeze() {
        isFrozen = true;
    }
}

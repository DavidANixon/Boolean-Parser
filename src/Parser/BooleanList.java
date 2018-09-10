package Parser;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class BooleanList implements Iterable<ListSymbol> {

    private final List<ListSymbol> listRepresentation = Collections.emptyList();
    private boolean isFrozen = false;

    public final List<ListSymbol> getListRepresentation() {
        return listRepresentation;
    }

    public Iterator<ListSymbol> iterator()
    {
        return listRepresentation.iterator();
    }

    public final Boolean add(ListSymbol listSymbol) {
        if (!isFrozen) {
            List<ListSymbol> temp = listRepresentation;
            listRepresentation.add(listSymbol);
            return temp.equals(listRepresentation);
        }
        else
            throw new UnsupportedOperationException("This list has been frozen and can no longer be modified.");
    }

    public final Boolean add(Type type) {
        return this.add(Connector.build(type));
    }

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

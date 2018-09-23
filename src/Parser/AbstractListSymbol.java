package Parser;

abstract class AbstractListSymbol implements ListSymbol {
    /**
     * This method is used to create a boolean list consisting of a single AbstractListSymbol.
     * @return A new boolean list consisting only of the symbol which called it
     */
    @Override
    public BooleanList toList() {
       BooleanList boolList = new BooleanList();
       boolList.add(this);
       return boolList;
    }
}

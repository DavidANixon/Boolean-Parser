package Parser;

abstract class AbstractListSymbol implements ListSymbol {
    @Override
    public BooleanList toList() {
       BooleanList boolList = new BooleanList();
       boolList.add(this);
       return boolList;
    }
}

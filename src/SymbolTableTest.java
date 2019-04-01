import org.junit.Test;

public class SymbolTableTest
{
    /** An object named test table that will be used for test. */
    private SymbolTable testTable;


    @Test
    public void testAddPredefinedSymbols()
    {
        SymbolTable testTable = new SymbolTable();
        for(int i = 0; i<16; i++)
        {
            String symbol ="R" + i;
            assert (i == testTable.getSymbol(symbol));
        }
    }

    @Test
    public void testContainsSymbol()
    {
        SymbolTable testTable = new SymbolTable();
        assert(testTable.containsSymbol("R0") == true);
        assert(testTable.containsSymbol("R6") == true);
        assert(testTable.containsSymbol("r0")== false);
        assert(testTable.containsSymbol("4")== false);
    }
}
import java.util.HashMap;
/**
 * SymbolTable : A symbol table that keeps a correspondence between symbolic labels and numeric addresses.
 */

public class SymbolTable
{
    /**
     * The HashMap containing the symbols.
     */
    private HashMap<String, Integer> mapOfSymbols;

    /**
     * Constructor that initializes the hash map that contains the symbols.
     * Calls method addPredefinedSymbols() to add default symbols.
     */
    public SymbolTable()
    {
        mapOfSymbols = new HashMap<String, Integer>();
        addPredefinedSymbols();
    }

    /**
     * This method adds predefined symbols to the symbol table after initialisation.
     */
    private void addPredefinedSymbols()
    {
        for (int i = 0; i < 16; i++)
        {
            String symbol = "R" + i;
            mapOfSymbols.put(symbol, i);
        }
        mapOfSymbols.put("Screen", 16384);
        mapOfSymbols.put("KBD", 24576);
        mapOfSymbols.put("SP", 0);
        mapOfSymbols.put("LCL", 1);
        mapOfSymbols.put("ARG", 2);
        mapOfSymbols.put("THIS", 3);
        mapOfSymbols.put("THAT", 4);
    }


    /**
     * Returns the integer associated with the symbol.
     * @param String
     * Input string that is the symbol.
     * @return
     * returns the integer related to the string.
     */
     public int getSymbol(String symbol)
     {
         return mapOfSymbols.get(symbol);
     }

     /**
      * Returns the integer associated with the symbol.
      * @param symbol
      * Input string that is the symbol.
      * @return
      * returns true if the symbol is in the table.
      */
      public boolean containsSymbol(String symbol)
      {
          return mapOfSymbols.containsKey(symbol);
      }

    /**
     * Adds a symbol and its associated value to the symbol table.
     * @param symbol
     * Input string that is the symbol.
     * @param value
     * Input integer that is associated with the symbol
     */
      public void addSymbol(String symbol, int value)
      {
          mapOfSymbols.put(symbol, value);
      }




}
import java.util.HashMap;

/**
 * Code: Translates Hack assembly language mnemonics into binary codes.
 * Done with :
 */
public class Code
{
    private HashMap<String, String> mapOfComps;
    private HashMap<String, String> mapOfDests;
    private HashMap<String, String> mapOfJumps;

    /**
     * Constructor that initializes hashMaps by populating them with codes.
     */
    public Code()
    {
        addCompsToHashMap();
        addDestsToHashMap();
        addJumpsToHashMap();
    }

    /**
     * Method that returns the binary code jump mnemonic.
     * @param jmpMnemonic
     * This parameter is the jump mnemonic that is to be converted to hack binary.
     * @return
     * Returns the binary representation of the jump mnemonic as a string.
     */

    public String getJumps(String jmpMnemonic)
    {
        return mapOfJumps.get(jmpMnemonic);
    }
    /**
     * Method that returns the binary code comp mnemonic.
     * @param compMnemonic
     * This parameter is the comp mnemonic that is to be converted to hack binary.
     * @return
     * Returns the binary representation of the comp mnemonic as a string.
     */
    public String getComp(String compMnemonic)
    {
        return mapOfComps.get(compMnemonic);
    }

    /**
     * Method that returns the binary code dest mnemonic.
     * @param destMnemonic
     * This parameter is the dest mnemonic that is to be converted to hack binary.
     * @return
     * Returns the binary representation of the dest mnemonic as a string.
     */
    public String getDests(String destMnemonic)
    {
        return mapOfDests.get(destMnemonic);
    }

    /**
     * Method that fills the mapOfJumps hashMap with binary codes and equivalent symbols.
     * This is only for Jumps.
     */
    public void addJumpsToHashMap()
    {
        mapOfJumps = new HashMap<String, String>();

        mapOfJumps.put("","000");
        mapOfJumps.put("null", "000");
        mapOfJumps.put("Null","000");
        mapOfJumps.put("JGT","001");
        mapOfJumps.put("JEQ","010");
        mapOfJumps.put("JGE","011");
        mapOfJumps.put("JLT","100");
        mapOfJumps.put("JNE","101");
        mapOfJumps.put("JLE","110");
        mapOfJumps.put("JMP","111");
    }
    /**
     * Method that fills the mapOfDests hashMap with binary codes and equivalent symbols.
     * This is only for destinations.
     */
    public void addDestsToHashMap()
    {
        mapOfDests = new HashMap<String, String>();

        mapOfDests.put("null", "000");
        mapOfDests.put("Null", "000");
        mapOfDests.put("M", "001");
        mapOfDests.put("D","010");
        mapOfDests.put("MD","011");
        mapOfDests.put("DM","011");
        mapOfDests.put("A","100");
        mapOfDests.put("AM","101");
        mapOfDests.put("MA","101");
        mapOfDests.put("AD","110");
        mapOfDests.put("DA","110");
        mapOfDests.put("AMD","111");
        mapOfDests.put("DAM","111");
        mapOfDests.put("MAD","111");
        mapOfDests.put("DMA","111");
        mapOfDests.put("MDA","111");
        mapOfDests.put("ADM","111");
    }
    /**
     * Method that fills the mapOfComps hashMap with binary codes and equivalent symbols.
     * This is only for comps.
     */
    private void addCompsToHashMap()
    {
        mapOfComps = new HashMap<String, String>();

        //START WITH A= 0 FOR COMP
        //0
        mapOfComps.put("0", "0101010");
        //1
        mapOfComps.put("1", "0111111");
        //-1
        mapOfComps.put("-1", "0111010");
        //D
        mapOfComps.put("D", "0001100");
        //A
        mapOfComps.put("A", "0110000");
        //!D
        mapOfComps.put("!D", "0001101");
        //!A
        mapOfComps.put("!A", "0110001");
        //-D
        mapOfComps.put("-D", "0001111");
        //-A
        mapOfComps.put("-A", "0110011");
        //D+1
        mapOfComps.put("D+1", "0011111");
        //A+1
        mapOfComps.put("A+1", "0110111");
        //D-1
        mapOfComps.put("D-1", "0001110");
        //A-1
        mapOfComps.put("A-1", "0110010");
        //D+A
        mapOfComps.put("D+A", "0000010");
        //D-A
        mapOfComps.put("D-A", "0010011");
        //A-D
        mapOfComps.put("A-D", "0000111");
        //D&A
        mapOfComps.put("D&A", "0000000");
        //D|A
        mapOfComps.put("D|A", "0010101");

        //NEXT DO A = 1 FOR COMP COMMANDS.

        //M
        mapOfComps.put("M", "1110000");
        //!M
        mapOfComps.put("!M", "1110001");
        //-M
        mapOfComps.put("-M", "1110011");
        //M+1
        mapOfComps.put("M+1", "1110111");
        //M-1
        mapOfComps.put("M-1", "1110010");
        //D+M
        mapOfComps.put("D+M", "1000010");
        //D-M
        mapOfComps.put("D-M", "1010011");
        //M-D
        mapOfComps.put("M-D", "1000111");
        //D&M
        mapOfComps.put("D&M", "1000000");
        //D|M
        mapOfComps.put("D|M", "1010101");
    }

    /**
     * Method that converts a string number to a binary number string.
     * @param number
     * The Number in string for that is to be converted to a 15 bit number.
     * @return
     * Returns the number, the integer, as a binary form. This in string format.
     */
    public String formatNumberAsBinary(String number)
    {
        int value = Integer.parseInt(number);
        String binaryNumber = Integer.toBinaryString(value);
        String formattedBinaryNumber =
                String.format("%16s", binaryNumber).replace(' ', '0');
        return formattedBinaryNumber;
    }

}

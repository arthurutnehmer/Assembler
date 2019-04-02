import org.junit.Test;

import static org.junit.Assert.*;

public class CodeTest
{
    Code testCode;
    String[] comps = {"0", "1", "-1", "D", "A", "!D", "!A", "-D", "-A", "D+1", "A+1", "D-1", "A-1", "D+A", "D-A", "A-D"
    , "D&A", "D|A"};

    String[] compsCodes = {"0101010", "0111111", "0111010", "0001100", "0110000"};

    public void testAddCompsToHashMap()
    {
        testCode = new Code();
    }

    @Test
    public void testGetFunction()
    {
        testAddCompsToHashMap();
        for(int i = 0; i<compsCodes.length;i++)
        {
            assert (testCode.getComp(comps[i]).equals(compsCodes[i]) );
        }

    }

    @Test
    public void testGetJump()
    {
        testCode = new Code();
        System.out.println(testCode.getJumps("null"));

    }


}
import org.junit.Test;

import java.io.IOException;

public class ParserTest
{
    /** An object named test parser that will be used for test. */
    private Parser testParser;

    @Test
    public void testConstructor()
    {
        testParser = new Parser();
    }

    @Test
    public void testIsValidCharacter()
    {
        testParser = new Parser();

        assert (testParser.isValidCharacter('a') == true);
        assert (testParser.isValidCharacter('b') == true);
        assert (testParser.isValidCharacter('c') == true);
        assert (testParser.isValidCharacter('d') == true);
        assert (testParser.isValidCharacter('A') == true);
        assert (testParser.isValidCharacter('!') == true);
        assert (testParser.isValidCharacter('^') == false);
        assert (testParser.isValidCharacter('%') == false);

    }

    @Test
    public void testParseCommandType()
    {
        testParser = new Parser();
        assert(testParser.parseCommandType("A=D") == 'C');
        assert(testParser.parseCommandType("D=!D") == 'C');
        assert(testParser.parseCommandType("D&M;JEQ") == 'C');
        assert(testParser.parseCommandType("@16384") == 'A');
        assert(testParser.parseCommandType("(LOOP)") == 'L');
        assert(testParser.parseCommandType("(okkk)") == 'L');
    }

    @Test
    public void testAdvanceMethod()
    {
        try
        {
            testParser = new Parser("testFile.asm");
            while (testParser.hasMoreCommands())
            {
                testParser.advance();
                if(  (testParser.commandType()  == '0'))
                {

                }
                else
                {
                   System.out.println((testParser.commandType()));
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Failed to open file" + e);
        }
    }

    @Test
    public void testDest()
    {
        try
        {
            testParser = new Parser("testFile.asm");
            while (testParser.hasMoreCommands())
            {
                testParser.advance();
                if(testParser.parseCommandType(testParser.getCurrentCommand()) == 'C')
                {
                    System.out.println(testParser.dest());
                }
            }
        }

        catch (IOException e)
        {

        }
    }

    @Test
    public void testComp()
    {
        try
        {
            testParser = new Parser("testFile.asm");
            while (testParser.hasMoreCommands())
            {
                testParser.advance();
                if(testParser.parseCommandType(testParser.getCurrentCommand()) == 'C')
                {
                     System.out.println(testParser.comp());
                }
            }
        }

        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    @Test
    public void testJump()
    {
        try
        {
            testParser = new Parser("testFile.asm");
            while (testParser.hasMoreCommands())
            {
                testParser.advance();
                if(testParser.parseCommandType(testParser.getCurrentCommand()) == 'C')
                {
                    System.out.println(testParser.jump());
                }
           }
        }

        catch (IOException e)
        {

        }
    }

    @Test
    public void testSymbol()
    {
        try
        {
            testParser = new Parser("testFile.asm");
            while (testParser.hasMoreCommands())
            {
                testParser.advance();
                if(testParser.parseCommandType(testParser.getCurrentCommand()) == ('A'))
                {
                    System.out.println(testParser.symbol());
                }
                else if (testParser.parseCommandType(testParser.getCurrentCommand()) == 'L')
                {
                    System.out.println(testParser.symbol());
                }
            }
        }

        catch (IOException e)
        {

        }
    }

}
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
/**
 * Parser : Encapsulates access to the input code. Reads an assembly language command, parses it, and provides
 * convenient access to the commands components (fields and symbols). In addition, removes all white space and comments.
 *
 * Done with :
 * constructor()
 * dest()
 * advance()
 * getCurrentCommand()
 * hasMoreCommands()
 * parseCommandsType()
 * cleanLine()
 * comp()
 * jump()
 * symbol()
 */
public class Parser
{
    private boolean[] arrayOfAllowedCharacters;
    private  Scanner fileInput;
    private String destination;
    private String computation;
    private String jump;
    private String currentCommand;
    private int aSMline;

    /**
     * Constructor that opens the input file/stream and parses it.
     * Also initializes table of allowed characters and fills it up.
     */
    public Parser(String inputFileName) throws IOException
    {
        AddAllowedCharacters();
        fileInput = new Scanner(new File(inputFileName));
        fileInput.useDelimiter("\n");
    }

    /**
     *  Constructor that initializes a table of allowed characters and fills it up.
     *  This is a blank constructor.
     */
    public Parser()
    {
        AddAllowedCharacters();
    }

    /**
     * Method that returns the symbol or decimal xxx of the
     * current command @xxx or (xxx). Should be called only
     * when commandType() is A_command or L_command.
     * @return
     * -Either a label or variable.
     */
    public String symbol()
    {
        if(parseCommandType(currentCommand) == 'A')
        {
            return currentCommand.substring(1);

        }
        else if(parseCommandType(currentCommand) == 'L')
        {
            return currentCommand.substring(1, currentCommand.length()-1);
        }
        else
        {
            return null;
        }
    }
    /**
     * Method that returns the dest mnemonic in the current C-command (8-possibilities).
     * Should be called only when commandType is C.
     * @return
     * Returns a string with the mnemonic of jump.
     * JLT
     * JEQ
     * JGT
     * JLE
     * JNE
     * JGE
     * JMP
     */
    public String jump()
    {
        if(currentCommand.contains(";"))
        {
            jump = currentCommand.substring(currentCommand.indexOf(";")+1);
        }
        else
        {
            jump = "null";
        }
        return jump;
    }

    /**
     * Method that returns the dest mnemonic in the current C-command (8-possibilities).
     * Should be called only when commandType is C.
     * @return
     * Returns a string with the mnemonic.
     */
    public String dest()
    {
            if(currentCommand.contains("="))
            {
                destination = currentCommand.substring(0, currentCommand.indexOf("="));
            }
            else
            {
                destination = "null";
            }
        return destination;
    }

    /**
     * Method that returns the comp mnemonic in the current C-command (8-possibilities).
     * Should be called only when commandType is C.
     * @return
     * Returns a string with the mnemonic that represents a comp.
     */
    public String comp()
    {
        //must handle D=D-1;JGT  ADM=0  AD=A+1
        if(currentCommand.contains("="))
        {
            //for commands with = and ;
            if(currentCommand.contains(";"))
            {
                computation = currentCommand.substring(currentCommand.indexOf("=")+1, currentCommand.indexOf(";"));
            }
            //if command only has =
            else
            {
                computation = currentCommand.substring(currentCommand.indexOf("=") + 1);
            }
        }
        //must handle 0;JMP  D;JNE
        else if(currentCommand.contains(";"))
        {
            computation = currentCommand.substring(0, currentCommand.indexOf(";"));
        }
        else
        {
            computation = null;
        }
        return computation;
    }

    /**
     * Method that is used to move the scanner aka the programs current command to the next one
     * if possible.
     * @exception
     * throws a RuntimeException if an invalid character is used.
     */
    public void advance() throws RuntimeException
    {
        currentCommand = cleanLine(fileInput.nextLine());
        aSMline++;

        for(int i = 0; i< currentCommand.length(); i++)
        {
            if(!isValidCharacter(currentCommand.charAt(i)) && (commandType()== 'C') )
            {
                throw new RuntimeException("Error, invalid character " + currentCommand.charAt(i) + " on line " + aSMline + " of file.");
            }
            else
            {

            }
        }
    }

    /**
     * Method that gets the current raw and unfiltered command.
     * @return
     * Returns the current command.
     */
    public String getCurrentCommand()
    {
        return currentCommand;
    }

    /**
     * A method that checks to see if the file being parsed has any further commands to process.
     * @return
     * returns true if a next line is present, returns false if no next line is present.
     * */
    public boolean hasMoreCommands()
    {
        return fileInput.hasNext();
    }

    /**
     * Helper Method that determines the command type.
     * Can either be an A instruction, C instruction,or a label.
     * @param clean
     * The cleaned line of assembly that is to be parsed for command type.
     * @return
     * Returns a char indicating what type of command it is.
     * If the method cannot detect what type of command it is, it will return 0.
     * A - a instruction.
     * C - c instruction.
     * L - a label.
     */
    public char parseCommandType(String clean)
    {
        char commandType = '0';
        if(clean.contains("@"))
        {
            commandType = 'A';
        }
        else if(clean.contains("=") || clean.contains(";"))
        {
            commandType = 'C';
        }
        else if(clean.contains("("))
        {
            commandType = 'L';
        }

        return commandType;
    }

    /**
     * Method that determines the command type of the current command stored in the parameter current command.
     * Can either be an A instruction, C instruction,or a label.
     * @return
     * Returns a char indicating what type of command it is.
     * If the method cannot detect what type of command it is, it will return 0.
     * A - a instruction.
     * C - c instruction.
     * L - a label.
     */
    public char commandType()
    {
        return parseCommandType(currentCommand);
    }


    /**
     * Helper Method that removes whitespace and comments from the line.
     * @param nextLineOfAssembly
     * The line of assembly that is to be cleaned.
     * @return
     * Returns a string of assembly clear of whitespace and comments.
     */
    private String cleanLine(String nextLineOfAssembly)
    {
        //work to located comments.
        int containsComments = nextLineOfAssembly.indexOf("//");

        //remove all characters after // for a single line.
        if(containsComments > -1)
        {
            nextLineOfAssembly = nextLineOfAssembly.substring(0,containsComments);
        }

        nextLineOfAssembly = nextLineOfAssembly.replaceAll(" ","");

        return  nextLineOfAssembly;
    }

    /**
     * Helper Method that fills the arrayOfAllowedCharacters with legal characters.
     */
    private void AddAllowedCharacters()
    {
        arrayOfAllowedCharacters = new boolean[128];
        //initializes all spots to false.
        for(int i = 0; i<128;i++)
        {
            arrayOfAllowedCharacters[i] = false;
        }

        //add the uppercase characters to boolean array.
        for(int i = 65; i<91; i++)
        {
            arrayOfAllowedCharacters[i] = true;
        }
        //add the lower case characters.
        for(int i = 97; i<123; i++)
        {
            arrayOfAllowedCharacters[i] = true;
        }
        //add the numbers
        for(int i = 48; i<58; i++)
        {
            arrayOfAllowedCharacters[i] = true;
        }
        //adds  + - = @ ( ) ; ! & | to allowed characters.
        arrayOfAllowedCharacters[64] = true;
        arrayOfAllowedCharacters[43] = true;
        arrayOfAllowedCharacters[45] = true;
        arrayOfAllowedCharacters[61] = true;
        arrayOfAllowedCharacters[40] = true;
        arrayOfAllowedCharacters[41] = true;
        arrayOfAllowedCharacters[59] = true;
        arrayOfAllowedCharacters[33] = true;
        arrayOfAllowedCharacters[38] = true;
        arrayOfAllowedCharacters[124] = true;
    }

    /**
     * Helper method that checks to see if the character is in the valid array.
     * @param characterToEvaluate
     * The character that is to be checked to see if it exist in allowed characters.
     * @return
     * Returns true if the character is allowed and false if not.
     */
    public boolean isValidCharacter(char characterToEvaluate)
    {
        return arrayOfAllowedCharacters[getASCIIInitEquivalent(characterToEvaluate)];
    }

    /**
     * Helper method that converts a character to ACSII int value.
     * @param characterToConvert
     * The character input.
     * @return a
     * The int value equal to the ASCII character
     */
    private int getASCIIInitEquivalent(char characterToConvert)
    {
        char c = characterToConvert;
        int a = c;
        return a;
    }

}
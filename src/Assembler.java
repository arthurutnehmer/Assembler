//Author info here
//TODO: don't forget to document each method in all classes!
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Assembler
{

    // ALGORITHM:
    // get input file name
    // create output file name and stream

    // create symbol table
    // do first pass to build symbol table (no output yet!)
    // do second pass to output translated ASM to HACK code

    // print out "done" message to user
    // close output file stream
    public static void main(String[] args)
    {

        String inputFileName, outputFileName;
        PrintWriter outputFile = null; //keep compiler happy
        SymbolTable symbolTable;

        //get input file name from command line or console input
        if(args.length == 1) {
            System.out.println("command line arg = " + args[0]);
            inputFileName = args[0];
        }
        else
        {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("Please enter assembly file name you would like to assemble.");
            System.out.println("Don't forget the .asm extension: ");
            inputFileName = keyboard.nextLine();

            keyboard.close();
        }

        outputFileName = inputFileName.substring(0,inputFileName.lastIndexOf('.')) + ".hack";

        try
        {
            outputFile = new PrintWriter(new FileOutputStream(outputFileName));
        } catch (FileNotFoundException ex)
        {
            System.err.println("Could not open output file " + outputFileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.exit(0);
        }
        //first pass.
        symbolTable = new SymbolTable();
        firstPass(inputFileName, symbolTable);

        //second pass.
        secondPass(inputFileName,symbolTable, outputFile );
        outputFile.close();
    }
    /**
     * Method that will grab all of the labels and add them to a symbol table
     * @param symbolTable
     * This is the symbol table that is used to add labels to.
     * @param inputFileName
     * The name of the file that contains the asm code.
     */
    private static void firstPass(String inputFileName, SymbolTable symbolTable)
    {
        Parser firstPass;
        int romLine = -1;

        try
        {
            firstPass = new Parser((inputFileName));
            while (firstPass.hasMoreCommands())
            {
                firstPass.advance();
                if(firstPass.commandType() == 'L')
                {
                    symbolTable.addSymbol(firstPass.symbol(), romLine+1);
                }
                else if(firstPass.commandType() == 'A')
                {
                    romLine++;
                }
                else if (firstPass.commandType() == 'C')
                {
                    romLine++;
                }
                else
                {
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error opening file.");
            System.out.println(e);
            System.exit(0);
        }
        catch (RuntimeException e)
        {
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * This method is responsible for making a second pass that converts each line of asm to
     * binary. After conversion, the code is added to a printwriter and printed to a file .hack.
     * @param inputFileName
     * The name of the file to be opened for a second pass.
     * @param symbolTable
     * The symbol table containing user defined and predefined symbols.
     * @param outputFile
     * This is the name of the file that will contain the converted .asm code, which will be binary code.
     */
    private static void secondPass(String inputFileName, SymbolTable symbolTable, PrintWriter outputFile)
    {
        Parser secondPass;
        Code converter = new Code();
        int nextAvailableAddress = 16;

        try
        {
            secondPass = new Parser(inputFileName);

            while (secondPass.hasMoreCommands())
            {
                String commandTranslatedToHack;
                secondPass.advance();

                //c commands.
                if(secondPass.commandType() == 'C')
                {
                    outputFile.println( commandTranslatedToHack = "111" + converter.getComp(secondPass.comp())  + converter.getDests(secondPass.dest()) + converter.getJumps(secondPass.jump()));
                }
                //If the command is a @number and not anything else, translate to binary,
                else if(secondPass.commandType() == 'A' &&  secondPass.symbol().matches("\\d+"))
                {
                    outputFile.println(converter.formatNumberAsBinary(secondPass.symbol()));
                }
                else if(secondPass.commandType() == 'A' &&  !secondPass.symbol().matches("\\d+"))
                {
                    //If the symbol is in the symbol table.
                    if(symbolTable.containsSymbol(secondPass.symbol()))
                    {
                        //lookup symbol in table, and convert it to binary.
                        outputFile.println(converter.formatNumberAsBinary(Integer.toString(symbolTable.getSymbol(secondPass.symbol()))));
                    }
                    // If the symbol is not found, then it must represent a new variable:
                    else if(!symbolTable.containsSymbol(secondPass.symbol()))
                    {
                        symbolTable.addSymbol(secondPass.symbol(), nextAvailableAddress);
                        nextAvailableAddress++;
                        outputFile.println(converter.formatNumberAsBinary(Integer.toString(symbolTable.getSymbol(secondPass.symbol()))));
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("error, cannot open file.");
            System.out.println(e);
            System.exit(0);
        }
    }
}

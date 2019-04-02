//Author info here
//TODO: don't forget to document each method in all classes!
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Assembler {

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

        try {
            outputFile = new PrintWriter(new FileOutputStream(outputFileName));
        } catch (FileNotFoundException ex) {
            System.err.println("Could not open output file " + outputFileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.exit(0);
        }

        // TODO: finish driver as algorithm describes
        //first pass.
        symbolTable = new SymbolTable();
        firstPass(inputFileName, symbolTable);

        //second pass.
        secondPass(inputFileName,symbolTable, outputFile );
        outputFile.close();


    }

    // TODO: march through the source code without generating any code
    //for each label declaration (LABEL) that appears in the source code,
    // add the pair <LABEL, n> to the symbol table
    // n = romAddress which you should keep track of as you go through each line
    //HINT: when should rom address increase? For what kind of commands?
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

    // TODO: march again through the source code and process each line:
    // if the line is a c-instruction, simple (translate)
    // if the line is @xxx where xxx is a number, simple (translate)
    // if the line is @xxx and xxx is a symbol, look it up in the symbol
    //	table and proceed as follows:
    // If the symbol is found, replace it with its numeric value and
    //	and complete the commands translation
    // If the symbol is not found, then it must represent a new variable:
    // add the pair <xxx, n> to the symbol table, where n is the next
    //	available RAM address, and complete the commands translation
    // HINT: What should ram address start at? When should it increase?
    // What do you do with L commands and No commands?
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

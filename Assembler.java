

import java.io.*;
import java.util.*;

public class Assembler {
    public static void main(String[] args) throws Exception{
        if (args.length != 1){
            System.out.println("Usage: java Assembler file.asm");
            return;
        }

        String filename = args[0];
        Parser parser = new Parser();
        List<Instruction> instructions = parser.parse(filename);
        Converter converter = new Converter();

        String listingFile = filename.replace(".asm", ".lst");
        String LoadFile = filename.replace(".asm", ".load");
        PrintWriter listing = new PrintWriter(new FileWriter(listingFile));
        PrintWriter load = new PrintWriter(new FileWriter(LoadFile));

        for (Instruction instr : instructions){
            String octal = converter.convertToOctal(instr);
            String locOct = String.format("%06o",instr.location);

            listing.println(locOct + " " + octal + " " + instr.ins);  //listing file
            load.println(locOct + " " + octal);     //load file
        }

        listing.close();
        load.close();
        
        System.out.println("complete.");

    }
}

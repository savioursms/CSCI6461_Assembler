

import java.io.*;
import java.util.*;

// instructiion set architecture page 22 - use two passes
public class Parser {
    private LabelTable lt = new LabelTable();

    public LabelTable getLabelTable(){
        return lt;
    }

    private String cleanLine(String line){
        if (line.contains(";")){
            line = line.substring(0, line.indexOf(";"));
        }
        return line.trim();
    }

    public List<Instruction> parse(String filename) throws IOException{

        //Pass 1: Process the line, if it is a label, add the label to a dictionary with the code location. Process
        //the rest of the line (it could be blank, if so no code is generated). Check for errors in the
        //code. 
        int cou = 0;    // location counter
        BufferedReader reader  = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null){
            line = cleanLine(line);
            if (line.isEmpty()) continue;

            // Normalize for safety
            String upperLine = line.toUpperCase();

            // Handle LOC FIRST
            if (upperLine.startsWith("LOC")) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    cou = Integer.parseInt(parts[1]);  // decimal
                }
                continue;
            }

            // Handle label
            if (line.contains(":")){
                String[] parts = line.split(":", 2);
                String label = parts[0].trim();
                lt.addLabel(label, cou);
                line = parts[1].trim();
                if(line.isEmpty()) continue;
            }

            // Increment only if actual instruction/data
            cou++;
        }

        reader.close();


        //Pass 2: Convert the code according to the second field. 
        //Add line to listing file and to load file. 
        // Pass 2
    List<Instruction> instructions = new ArrayList<>();
    cou = 0;
    reader = new BufferedReader(new FileReader(filename));

    while ((line = reader.readLine()) != null) {
        String ol = line;
        line = cleanLine(line);
        if (line.isEmpty()) continue;

        // Handle label
        if (line.contains(":")) {
            String[] parts = line.split(":", 2);
            line = parts[1].trim();
            if (line.isEmpty()) continue;
        }

        // Handle LOC
        if (line.toUpperCase().startsWith("LOC")) {
            String[] parts = line.split("\\s+");
            if (parts.length >= 2) {
                cou = Integer.parseInt(parts[1]);  // decimal
            }
            continue;   // do NOT generate instruction
        }

        Instruction instr = new Instruction(ol);

        // Handle Data
        if (line.startsWith("Data")) {
            instr.isData = true;
            String[] parts = line.split("\\s+");
            if (parts.length < 2) continue;
            String value = parts[1];
            if (value.matches("\\d+")) {
                instr.address = Integer.parseInt(value);
            } else {
                instr.address = lt.getAddress(value);
            }
            instr.EA = instr.address;
        }
        // Handle HLT (1-token instruction)
        else if (line.equals("HLT")) {
            instr.isData = false;
            instr.Opcode = "HLT";
            instr.R = 0;
            instr.IX = 0;
            instr.I = 0;
            instr.address = 0;
        }
        // Handle normal instructions
        else {
            instr.isData = false;
            String[] parts = line.split("[ ,]+");

            instr.Opcode = parts[0];

            switch (instr.Opcode) {
                case "HLT":
                    instr.R = 0;
                    instr.IX = 0;
                    instr.I = 0;
                    instr.address = 0;
                    break;

                case "LDX":
                case "STX":
                    if (parts.length < 3) {
                        System.err.println("Skipping invalid instruction: " + ol);
                        continue;
                    }
                    instr.R = Integer.parseInt(parts[1]);
                    instr.address = Integer.parseInt(parts[2]);
                    instr.IX = 0;
                    instr.I = 0;
                    break;

                default: // LDR, STR, LDA, etc.
                    if (parts.length < 4) {
                        System.err.println("Skipping invalid instruction: " + ol);
                        continue;
                    }
                    instr.R = Integer.parseInt(parts[1]);
                    instr.IX = Integer.parseInt(parts[2]);
                    instr.address = Integer.parseInt(parts[3]);
                    if (parts.length >= 5)
                        instr.I = Integer.parseInt(parts[4]);
                    else
                        instr.I = 0;
                    break;
            }
        }

        instr.location = cou;
        instructions.add(instr);
        cou++;
    }

        reader.close();
        return instructions;
    }

    
}

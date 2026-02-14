
import java.util.HashMap;

public class Converter {
    private HashMap<String, Integer> opcodeMap = new HashMap<>();

    public Converter(){
        opcodeMap.put("LDR", 1);    //LDR -Load Register From Memory
        opcodeMap.put("STR", 2);    //STR -Store Register To Memory
        opcodeMap.put("LDA", 3);    //LDA -Load Register with Address
        opcodeMap.put("LDX", 41);   //LDX -Load Index Register from Memory
        opcodeMap.put("STX", 42);   //STX -Store Index Register to Memory
        opcodeMap.put("HLT", 0);

    }

    public String convertToOctal(Instruction instr){
        //Data
        if(instr.isData){
            int value = instr.address;
            return String.format("%06o", value);
        }

        //HLT
        if (instr.Opcode.equals("HLT")){
            return String.format("%06o", 0);
        }

        int opcode = opcodeMap.get(instr.Opcode);
        int binaryI = 0;

        binaryI |= (opcode << 10);  //opcode - 6 bits
        binaryI |= (instr.R << 8);  //R - 2 bits
        binaryI |= (instr.IX << 6);  //IX - 2 bits
        binaryI |= (instr.I << 5);  //I - 1 bits
        binaryI |= (instr.address);  //address - 5 bits

        return String.format("%06o", binaryI);
    }
}

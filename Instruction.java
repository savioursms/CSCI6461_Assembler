

public class Instruction {
    public String Opcode;
    public int IX;
    public int R;
    public int I;
    public int address;
    public int EA;  // effective address
    public String ins;  //instructions
    public String octal;
    public boolean isData;
    public int location;

    public Instruction(String l){
        this.ins = l;
    }

    public Instruction() {} 

    // Computing effective adress based on instruction architecture page 7
    public void computeEA(int[] indexR, LabelTable lt){
        int ea = this.address;

        //Add index register if IX != 0
        if (this.IX != 0){
            ea += indexR[this.IX];
        }

        if (this.I == 1){
            ea = lt.getAddress(ea);
        }

        this.EA = ea;
    }
    
}

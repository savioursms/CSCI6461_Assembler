Steps to test the assembler
1. create test file filename.asm under tests folder
2. If Mac user, open terminal
3. cd to this CSCI6461_Assembler directory
4. type command: javac *.java in terminal
5. type command: jar cfe Assembler.jar Assembler *.class in terminal
6. type command: java -jar Assembler.jar tests/filename.asm
7. Once the shows complete. in terminal, test completed and 
    filename.load, filename.lst will automatically genertated
8. Can open the filename.load and filename.lst to check
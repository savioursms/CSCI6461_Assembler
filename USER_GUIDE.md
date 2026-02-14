# USER GUIDE — CSCI6461 Assembler

## Introduction
The CSCI6461 Assembler translates assembly language (`.asm`) files into octal machine code, producing a listing file (`.lst`) and a load file (`.load`).

## Getting Started

### Prerequisites
- **Java 17+** (JDK) installed and on your `PATH`.

### Compile & Package
```bash
cd CSCI6461_Assembler
javac *.java
jar cfe Assembler.jar Assembler *.class
```

### Run the Assembler
```bash
java -jar Assembler.jar <path/to/file.asm>
```
When it prints `complete.`, the output files have been generated next to the input:
- `file.lst` — listing with location, octal encoding, and original source line.
- `file.load` — load file with location and octal encoding only.

## Input File Format (`.asm`)

| Element | Syntax | Example |
|---|---|---|
| Set location counter | `LOC <decimal>` | `LOC 10` |
| Label | `name:` (before instruction) | `End: HLT` |
| Data word | `Data <value or label>` | `Data 15` |
| Instruction | `OP R,IX,Addr[,I]` | `LDR 1,0,6` |
| Index instruction | `OP IX,Addr` | `LDX 1,20` |
| Halt | `HLT` | `HLT` |
| Comment | `; text` | `; load value` |

## Supported Instructions

| Opcode | Name | Format |
|---|---|---|
| `LDR` | Load Register from Memory | `LDR R,IX,Addr[,I]` |
| `STR` | Store Register to Memory | `STR R,IX,Addr[,I]` |
| `LDA` | Load Register with Address | `LDA R,IX,Addr[,I]` |
| `LDX` | Load Index Register | `LDX IX,Addr` |
| `STX` | Store Index Register | `STX IX,Addr` |
| `HLT` | Halt | `HLT` |

## Example

**test1.asm**
```asm
LOC 6
Data 10
Data 3
LDR 1,0,6
HLT
```

**Run:**
```bash
java -jar Assembler.jar tests/test1.asm
```

**test1.lst (output):**
```
000006 000012 Data 10
000007 000003 Data 3
000010 002406 LDR 1,0,6
000011 000000 HLT
```

## Troubleshooting

| Problem | Solution |
|---|---|
| `Usage: java Assembler file.asm` | Provide exactly one `.asm` file path as argument |
| `Skipping invalid instruction: ...` | Check instruction has correct number of operands |
| `FileNotFoundException` | Verify the file path is correct |

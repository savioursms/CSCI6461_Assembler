# DESIGN & ARCHITECTURE — CSCI6461 Assembler

## Overview
A two-pass assembler that converts assembly source into 16-bit octal machine words. Modular design separates parsing, encoding, and symbol resolution.

## Component Descriptions

### Assembler.java — Main Driver
- Entry point; takes a single `.asm` file argument.
- Orchestrates parsing and conversion.
- Writes `.lst` (listing) and `.load` (load) output files.

### Parser.java — Two-Pass Parser
- **Pass 1**: Scans for labels and `LOC` directives, builds the symbol table (`LabelTable`), and tracks location counter.
- **Pass 2**: Re-reads the file, parses each instruction into an `Instruction` object with opcode, register, index, indirect bit, and address fields.

### Converter.java — Binary/Octal Encoder
- Maps opcode mnemonics to numeric values.
- Encodes instruction fields into a 16-bit integer (6-bit opcode, 2-bit R, 2-bit IX, 1-bit I, 5-bit address).
- Formats as 6-digit octal string.

### Instruction.java — Instruction Data Structure
- Holds parsed fields: `Opcode`, `R`, `IX`, `I`, `address`, `location`, `isData`.
- Contains `computeEA()` for effective address calculation (index register + indirect addressing).

### LabelTable.java — Symbol Table
- `HashMap<String, Integer>` mapping label names to their memory locations.
- Populated during Pass 1, queried during Pass 2.

## Instruction Encoding (16-bit word)

```
Bits: 15-10  9-8   7-6   5     4-0
      Opcode  R    IX    I    Address
```

| Field | Bits | Description |
|---|---|---|
| Opcode | 6 | Instruction type |
| R | 2 | General-purpose register |
| IX | 2 | Index register |
| I | 1 | Indirect addressing flag |
| Address | 5 | Memory address |

## Design Decisions
- **Two-pass approach**: Required because forward-referenced labels must be resolved before encoding.
- **Octal output**: Matches the ISA specification (page 22).
- **Data directive**: `Data <value>` stores a raw value; `Data <label>` stores the label's resolved address.

## Future Improvements
- Add error reporting with line numbers.
- Support additional opcodes as the ISA expands.
- Add `.gitignore` for compiled artifacts.
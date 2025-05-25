package MiniJava.facade;

import MiniJava.codeGenerator.Address;
import MiniJava.codeGenerator.Memory;
import MiniJava.codeGenerator.Operation;

public class MemoryFacade {
    private Memory memory;

    public MemoryFacade() {
        memory = new Memory();
    }

    public int getDateAddress() {
        return memory.getDateAddress();
    }

    public void pintCodeBlock() {
        memory.pintCodeBlock();
    }

    public int getCurrentCodeBlockAddress() {
        return memory.getCurrentCodeBlockAddress();
    }

    public void add3AddressCode(Operation op, Address opr1, Address opr2, Address opr3) {
        memory.add3AddressCode(op, opr1, opr2, opr3);
    }

    public void add3AddressCode(int i, Operation op, Address opr1, Address opr2, Address opr3) {
        memory.add3AddressCode(i, op, opr1, opr2, opr3);
    }

    public int getNewTemp() {
        return memory.getNewTemp();
    }

    public void updateLastTempIndex() {
        memory.updateLastTempIndex();
    }

    public int saveMemory() {
        return memory.saveMemory();
    }
}

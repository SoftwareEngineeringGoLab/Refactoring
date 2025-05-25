package MiniJava.facade;

import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.scanner.token.Token;

public class CodeGeneratorFacade {
    private final CodeGenerator codeGenerator;

    public CodeGeneratorFacade() {
        codeGenerator = new CodeGenerator();
    }

    public void semanticFunction(int semanticAction, Token lookAhead) {
        codeGenerator.semanticFunction(semanticAction, lookAhead);
    }

    public void printMemory() {
        codeGenerator.printMemory();
    }
}

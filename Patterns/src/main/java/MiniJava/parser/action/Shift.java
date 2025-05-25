package MiniJava.parser.action;

import MiniJava.parser.Parser;
import MiniJava.scanner.lexicalAnalyzer;

public class Shift extends Action {

    public Shift(int number) {
        super(number);
    }

    @Override
    public ActionOutput performAction(Parser parser, ActionOutput actionOutput) {
        parser.getParsStack().push(this.number);
        lexicalAnalyzer analyzer = parser.getLexicalAnalyzer();
        actionOutput.setLookAhead(analyzer.getNextToken());
        return actionOutput;
    }


    @Override
    public String toString() {
        return "s " + getNumber();
    }

}

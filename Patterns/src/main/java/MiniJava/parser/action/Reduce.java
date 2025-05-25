package MiniJava.parser.action;

import MiniJava.Log.Log;
import MiniJava.facade.CodeGeneratorFacade;
import MiniJava.parser.ParseTable;
import MiniJava.parser.Parser;
import MiniJava.parser.Rule;

import java.util.Stack;

public class Reduce extends Action {
    public Reduce(int number) {
        super(number);
    }

    @Override
    public ActionOutput performAction(Parser parser, ActionOutput actionOutput) {
        Stack<Integer> parsStack = parser.getParsStack();
        ParseTable parseTable = parser.getParseTable();
        CodeGeneratorFacade cg = parser.getCg();

        Rule rule = parser.getRules().get(this.number);
        for (int i = 0; i < rule.RHS.size(); i++) {
            parsStack.pop();
        }
        Log.print(/*"state : " +*/ parsStack.peek() + "\t" + rule.LHS);
        parsStack.push(parseTable.getGotoTable(parsStack.peek(), rule.LHS));
        Log.print(/*"new State : " + */parsStack.peek() + "");
        try {
            cg.semanticFunction(rule.semanticAction, actionOutput.getLookAhead());
        } catch (Exception e) {
            Log.print("Code Generator Error");
        }
        return actionOutput;
    }

    @Override
    public String toString() {
        return "r " + getNumber();
    }
}

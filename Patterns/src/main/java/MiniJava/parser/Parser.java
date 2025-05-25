package MiniJava.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import MiniJava.Log.Log;
import MiniJava.errorHandler.ErrorHandler;
import MiniJava.facade.CodeGeneratorFacade;
import MiniJava.parser.action.Action;
import MiniJava.parser.action.ActionOutput;
import MiniJava.scanner.lexicalAnalyzer;

public class Parser {
    private ArrayList<Rule> rules;
    private Stack<Integer> parsStack;
    private ParseTable parseTable;
    private lexicalAnalyzer lexicalAnalyzer;
    private CodeGeneratorFacade cg;

    public Parser() {
        parsStack = new Stack<Integer>();
        parsStack.push(0);
        try {
            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rules = new ArrayList<Rule>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                rules.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cg = new CodeGeneratorFacade();
    }

    public Stack<Integer> getParsStack() {
        return parsStack;
    }

    public lexicalAnalyzer getLexicalAnalyzer() {
        return lexicalAnalyzer;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public CodeGeneratorFacade getCg() {
        return cg;
    }

    public void startParse(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);

        ActionOutput actionOutput = new ActionOutput(lexicalAnalyzer.getNextToken(), false);
        while (!actionOutput.isFinish()) {
            try {
                actionOutput = doNextAction(actionOutput);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        if (!ErrorHandler.hasError) cg.printMemory();
    }

    private ActionOutput doNextAction(ActionOutput actionOutput) {
        Action currentAction;
        Log.print(/*"lookahead : "+*/ actionOutput.getLookAhead().toString() + "\t" + parsStack.peek());
        currentAction = parseTable.getActionTable(parsStack.peek(), actionOutput.getLookAhead());
        Log.print(currentAction.toString());

        actionOutput = currentAction.performAction(this, actionOutput);
        Log.print("");
        return actionOutput;
    }
}

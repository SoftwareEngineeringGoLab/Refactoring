package MiniJava.parser.action;

import MiniJava.parser.Parser;

public class Accept extends Action {

    public Accept() {
        super(0); // Accept action does not have a specific number
    }

    @Override
    public ActionOutput performAction(Parser parser, ActionOutput actionOutput) {
        actionOutput.setFinish(true);
        return actionOutput;
    }

    @Override
    public String toString() {
        return "acc";
    }
}

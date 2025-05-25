package MiniJava.parser.action;

import MiniJava.parser.Parser;
import MiniJava.parser.action.ActionOutput;

public abstract class Action {
    // if action = shift : number is state
    // if action = reduce : number is number of rule
    public int number;

    public Action(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public abstract ActionOutput performAction(Parser parser, ActionOutput actionOutput);

    public abstract String toString();
}

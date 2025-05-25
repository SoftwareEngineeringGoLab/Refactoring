package MiniJava.parser.action;

import MiniJava.scanner.token.Token;

public class ActionOutput {
    private Token lookAhead;
    private boolean finish;

    public ActionOutput(Token lookAhead, boolean finish) {
        this.lookAhead = lookAhead;
        this.finish = finish;
    }

    public Token getLookAhead() {
        return lookAhead;
    }

    public void setLookAhead(Token lookAhead) {
        this.lookAhead = lookAhead;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

}

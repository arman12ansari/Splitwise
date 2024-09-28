package dev.arman.splitwise.commands;

/**
 * @author mdarmanansari
 */
public interface Command {

    boolean matches(String input);

    void execute(String input);
}

package seedu.address.model;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.LinkedList;
import java.util.Queue;

public class ModelManagerBuilder {

    Queue<Command> commandQueue;

    public ModelManagerBuilder() {
        this.commandQueue = new LinkedList<>();
    }

    public ModelManagerBuilder(Queue<Command> queue) {
        this.commandQueue = queue;
    }

    public ModelManagerBuilder with(Command command) {
        commandQueue.add(command);
        return new ModelManagerBuilder(commandQueue);
    }

    public void executeCommands(ModelManager modelManager) {
        Command c;
        while (!commandQueue.isEmpty()) {
            c = commandQueue.poll();
            try {
                c.execute(modelManager);
            } catch (CommandException ce) {
                // for testing commands which are errorous
                System.out.println("Model manager test: " + ce);
            }
        }
    }

    public ModelManager build() {
        ModelManager modelManager = new ModelManager();
        executeCommands(modelManager);
        return modelManager;
    }

    public ModelManager build(ModelManager modelManager) {
        executeCommands(modelManager);
        return modelManager;
    }
}

package seedu.cmdo.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.cmdo.commons.core.ComponentManager;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.logic.commands.Command;
import seedu.cmdo.logic.commands.CommandResult;
import seedu.cmdo.logic.commands.RedoCommand;
import seedu.cmdo.logic.commands.UndoCommand;
import seedu.cmdo.logic.parser.MainParser;
import seedu.cmdo.model.Model;
import seedu.cmdo.model.ToDoList;
import seedu.cmdo.model.Undoer;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final MainParser parser;
    private final Undoer undoer;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = MainParser.getInstance();
        this.undoer = Undoer.getInstance(model.getToDoList());
    }
    
    //@@author A0139661Y
    @Override	
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        // Get snapshot of existing todolist. We store a new object, not a reference.
        if (command.isUndoable) {
        	undoer.snapshot(new ToDoList(model.getToDoList()));
        	logger.info("Snapshot taken of " + model.getToDoList().toString());
        }
        // Pop redo stack unless command is undo
        if (!command.getClass().equals(UndoCommand.class) && 
        		!command.getClass().equals(RedoCommand.class)) {
        	System.out.println(command.getClass().toString());
        	undoer.clearRedoStack();
        	logger.info("Redo stack cleared.");
        }
        command.setData(model);
        return command.execute();
    }
    
    // @@author A0139661Y
    @Override
    public ObservableList<ReadOnlyTask> getBlockedList() {
    	return model.getBlockedList();
    }
    
    // @@author A0139661Y
    @Override
    public ObservableList<ReadOnlyTask> getAllTaskList() {
    	return model.getAllTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    // @@author A0139661Y
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList(boolean firstRun) {
        return model.getFilteredTaskList(firstRun);
    }    
}
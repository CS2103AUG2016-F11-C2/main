package seedu.cmdo.model;

import javafx.collections.transformation.FilteredList;
import seedu.cmdo.commons.core.ComponentManager;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.commons.events.model.UndoActionEvent;
import seedu.cmdo.commons.exceptions.CannotUndoException;
import seedu.cmdo.commons.events.model.ToDoListChangedEvent;
import seedu.cmdo.commons.util.StringUtil;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList;
import seedu.cmdo.model.task.UniqueTaskList.TaskAlreadyDoneException;
import seedu.cmdo.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Represents the in-memory model of the todo list data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ToDoList toDoList;
    private ToDoList oldToDoList = new ToDoList();
    private final FilteredList<Task> filteredTasks;
    private final UserPrefs userPrefs;
    private final Undoer undoer;

    /**
     * Initializes a ModelManager with the given ToDoList
     * ToDoList and its variables should not be null
     */
    public ModelManager(ToDoList src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with todo list: " + src + " and user prefs " + userPrefs);

        toDoList = new ToDoList(src);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
        this.userPrefs = userPrefs;
        
        //@@author A0139661Y
        oldToDoList.resetData(toDoList);
        this.undoer = Undoer.getInstance((ReadOnlyToDoList)oldToDoList);
        logger.info("Saved new toDoList into Undoer stack. " + toDoList.toString());
    }

    public ModelManager() {
        this(new ToDoList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyToDoList initialData, UserPrefs userPrefs) {
        toDoList = new ToDoList(initialData);
        filteredTasks = new FilteredList<>(toDoList.getTasks());
        this.userPrefs = userPrefs;

        //@@author A0139661Y
        oldToDoList.resetData(toDoList);
        this.undoer = Undoer.getInstance((ReadOnlyToDoList)oldToDoList);
        logger.info("Saved last stable toDoList into Undoer stack. " + toDoList.toString());
    }

    @Override
    public void resetData(ReadOnlyToDoList newData) {
        oldToDoList.resetData(toDoList);
    	toDoList.resetData(newData);
        indicateToDoListChanged();
    }

    @Override
    public ReadOnlyToDoList getToDoList() {
        return toDoList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged() {
    	ToDoList saveState = new ToDoList(toDoList);
        raise(new ToDoListChangedEvent(saveState));
    }
    
    /** Raises an event to indicate the model has changed */
    private void indicateToDoListChanged(Class calling) {
        raise(new ToDoListChangedEvent(toDoList, calling));
    }
    
    /** 
     * Undo a single action.
     * 
     * @@author A0139661Y-unused
     */
    @Override
    public void undoOne() throws CannotUndoException {
    	if (toDoList.equals(oldToDoList)) {
    		throw new CannotUndoException("Nothing to undo.");
    	}
    	toDoList.resetData(oldToDoList);
    	indicateToDoListChanged();
    }
    
    /**
     * Undo functionality
     * 
     * @@author A0139661Y
     */
    @Override
    public synchronized void undo() throws CannotUndoException {
    	try {
    		toDoList.resetData(undoer.undo());
    	} catch (EmptyStackException ese) {
    		throw new CannotUndoException("Nothing to undo.");
    	}
    	indicateToDoListChanged(Undoer.class);
    	updateFilteredListToShowAll();
    }



    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
    	toDoList.removeTask(target);
        indicateToDoListChanged();
    }
    
    @Override
    public synchronized void doneTask(Task target) throws TaskNotFoundException, TaskAlreadyDoneException {
        if (target.checkDone().value) {
        	throw new TaskAlreadyDoneException();
        }
    	target.checkDone().setDone();
        indicateToDoListChanged();
        updateFilteredListToShowAll();
    }

    @Override
    public synchronized void addTask(Task task) {
        toDoList.addTask(task);
        updateFilteredListToShowAll();
        indicateToDoListChanged();
        
    }
    
    /**
     * Edits a task
     * 
     * @author A0139661Y
     */
    @Override
    public synchronized void editTask(ReadOnlyTask taskToEdit, Task toEditWith) throws TaskNotFoundException {
    	toDoList.editTask(taskToEdit, toEditWith);
    	updateFilteredListToShowAll();
    	indicateToDoListChanged();
    }
    
    @Override
    public void changeStorageFilePath(String filePath) {
    	userPrefs.setStorageSettings(filePath);
    	indicateToDoListChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================
    
    @Override 
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
    	return new UnmodifiableObservableList<>(filteredTasks); 
    }
    
    @Override 
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(boolean firstRun) {
    	UnmodifiableObservableList<ReadOnlyTask> initList = new UnmodifiableObservableList<>(filteredTasks);
    	// This prevents even done tasks from showing up at first run.
    	updateFilteredListToShowAll(false);
    	return initList;
    }
    
    @Override 
    public UnmodifiableObservableList<ReadOnlyTask> getBlockedList() {
    	UnmodifiableObservableList<ReadOnlyTask> initList = new UnmodifiableObservableList<>(filteredTasks);
    	updateFilteredListToShowBlocked();
    	return initList;
    }
    
    //@@author A0139661Y
    @Override
    public void updateFilteredListToShowBlocked() {
        updateFilteredListToShowAll(new PredicateExpression(new BlockQualifier()));
    }
    
    // By default a list with no done tasks where taskStatus is false
    @Override
    public void updateFilteredListToShowAll() {
        updateFilteredListToShowAll(new PredicateExpression(new DetailQualifier()));
    }
    
    // Used by find done or list done where taskStatus is true
    @Override
    public void updateFilteredListToShowAll(boolean taskStatus) {
        updateFilteredListToShowAll(new PredicateExpression(new DetailQualifier(taskStatus)));
    }
    
    private void updateFilteredListToShowAll(Expression expression) {
    	assert expression != null;
    	filteredTasks.setPredicate(expression::satisfies);
    }
    
    // Used by find done <...> or find <...> where taskStatus depends on user input.
    @Override
    public void updateFilteredTaskList(Set<String> keywords, boolean taskStatus){
        updateFilteredTaskList(new PredicateExpression(new DetailQualifier(keywords, taskStatus)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class DetailQualifier implements Qualifier {
        private Set<String> detailKeyWords = Collections.EMPTY_SET;
        private final boolean taskStatus;
        
        // Keywords, specified tasks status
        // Likely a find done <...>
        DetailQualifier(Set<String> detailKeyWords, boolean taskStatus) {
            this.detailKeyWords = detailKeyWords;
            this.taskStatus = taskStatus;
        }
        
        // No keywords, a specified task status
        // Likely a find done or a list done
        DetailQualifier(boolean taskStatus) {
            this.taskStatus = taskStatus;
        }
        
        // No keywords, no specified task status
        // Likely a list
        DetailQualifier() {
        	taskStatus = false;
        }
        
        /*
         * shows only undone tasks
         * 
         * @return boolean: true if match, false if not
         */
        @Override
        public boolean run(ReadOnlyTask task) {
        	// Determine if done tasks match the user's filter criteria.
        	// In this case, no keywords were specified.
        	if (detailKeyWords.isEmpty()) {
        		return task.checkDone().value.equals(taskStatus);
        	}
        	if (task.checkDone().value != taskStatus)
        		return false;
            return detailKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDetail().details, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "detail=" + String.join(", ", detailKeyWords);
        }
    }
    
    //@@author A0139661Y
    private class BlockQualifier implements Qualifier {           
        /*
		 * Produces a list of blocks only.
         * 
         * @return boolean: true if match, false if not
         */
        @Override
        public boolean run(ReadOnlyTask task) {
        	return task.getBlock();
        }
    }
}

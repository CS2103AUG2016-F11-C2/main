package seedu.cmdo.model;

import java.util.Set;

import seedu.cmdo.commons.core.UnmodifiableObservableList;
import seedu.cmdo.commons.exceptions.CannotUndoException;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList;
import seedu.cmdo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyToDoList newData);

    /** Returns the ToDoList */
    ReadOnlyToDoList getToDoList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Done the given task. */
    void doneTask(ReadOnlyTask target, Task replacer) throws UniqueTaskList.TaskNotFoundException, UniqueTaskList.TaskAlreadyDoneException;
    
    /** Adds the given task 
     * @return */
    int addTask(Task task);

    /** Returns the filtered task list as an {@code UnmodifiableObservaibleList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();
    
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList(boolean firstRun);
    
    UnmodifiableObservableList<ReadOnlyTask> getBlockedList();
    
	UnmodifiableObservableList<ReadOnlyTask> getAllTaskList();

    void updateFilteredListToShowBlocked();
    
    /** Updates the filter of the filtered task list to show all undone tasks by default **/
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to show all done tasks */
    void updateFilteredListToShowAll(boolean taskStatus);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords, boolean taskStatus);

	int editTask(ReadOnlyTask taskToEdit, Task toEditWith) throws TaskNotFoundException;

	void changeStorageFilePath(String filePath);

	void undo() throws CannotUndoException;

	void redo() throws CannotUndoException;

}

package seedu.cmdo.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.model.ToDoList;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.Done;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
import seedu.cmdo.model.task.UniqueTaskList;

/**
 * Adds a task to CMDo.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: <details> by/on <date> at <time> /<priority> /<TAG...>\n"
            + "Example: " + COMMAND_WORD
            + " bring dog to the vet on Thursday at noon /high -dog";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in CMDo";

    private final Task toAdd;

    /**
     * Created an add command for SINGULAR NON-RANGE DATE AND TIME
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * 
     * @@author A0139661Y
     */
    public AddCommand(String details,
                      LocalDate dueByDate,
                      LocalTime dueByTime,
                      String priority,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Detail(details),
                new DueByDate (dueByDate),
                new DueByTime(dueByTime),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
    }
    
    /**
     * Created an add command for RANGE DATE AND TIME
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * 
     * @@author A0139661Y
     */
    public AddCommand(String details,
                      LocalDate dueByDateStart,
                      LocalTime dueByTimeStart,
                      LocalDate dueByDateEnd,
                      LocalTime dueByTimeEnd,
                      String priority,
                      Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Detail(details),
                new DueByDate (dueByDateStart, dueByDateEnd),
                new DueByTime(dueByTimeStart, dueByTimeEnd),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
    }


    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyTask getTask() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

}
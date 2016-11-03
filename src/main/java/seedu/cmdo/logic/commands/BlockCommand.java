package seedu.cmdo.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.cmdo.commons.exceptions.IllegalValueException;
import seedu.cmdo.commons.exceptions.TaskBlockedException;
import seedu.cmdo.logic.parser.Blocker;
import seedu.cmdo.model.tag.Tag;
import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;
/**
 * Created an Block command
 *
 * @throws IllegalValueException if any of the raw values are invalid
 * 
 * @@author A0141128R
 */

public class BlockCommand extends Command {

    public static final String COMMAND_WORD = "block";

    public static final String MESSAGE_USAGE = ": Blocks a timeslot in CMDo. "
            + "\n" + "Parameters: '<details>' by/on <date> at <time> /<priority> -<TAG>\n"
    		+ "priority and tags can be left blank"
            + "Example: " + COMMAND_WORD
            + " 'unconfirmed business meeting' on Thursday at noon to 1300 /high -business"
    		+ "Single time and date input is allowed as well"
    		+ "Example: " + COMMAND_WORD
            + " 'unconfirmed business meeting' on Thursday at 1300/high -business"
            + "creates a blocked slot with timing for 1300 to 1400 on thrusday";

    public static final String MESSAGE_SUCCESS = "Time slot blocked: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This time slot if already booked";

    private final Task toBlock;
    private final Blocker blocker = new Blocker();

    
    /**
     * Created an Block command
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public BlockCommand(String details,
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
        this.toBlock = new Task(
                new Detail(details),
                new DueByDate (dueByDateStart, dueByDateEnd),
                new DueByTime(dueByTimeStart, dueByTimeEnd),
                new Priority(priority),
                new UniqueTagList(tagSet)
        );
        //makes the task a block time slot
        toBlock.setBlock();
        this.isUndoable = true;
    }


    public BlockCommand(Task toBlock) {
        this.toBlock = toBlock;
        this.isUndoable = true;
    }

    public ReadOnlyTask getBlock() {
        return toBlock;
    }

    //@@author A0139661Y
    @Override
    public CommandResult execute() {
        try {
    		blocker.checkBlocked(toBlock, model.getBlockedList());
            model.addTask(toBlock);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toBlock));
        } catch (TaskBlockedException tbe) {
        	return new CommandResult (tbe.getMessage());
        }
    }

}
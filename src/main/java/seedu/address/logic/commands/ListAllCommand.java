package seedu.address.logic.commands;

public class ListAllCommand {
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list all";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListAllCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


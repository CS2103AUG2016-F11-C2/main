package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

import java.util.List;

/*
 * @author A0141128R
 */
public class EditCommandTest extends ToDoListGuiTest {

    @Test
    public void edit() {
        //edit first task
    	TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        TestTask taskToEdit = td.family;
        assertEditSuccess(targetIndex, taskToEdit, currentList);

        //edit the last in the list
        currentList = td.getTypicalTasks();
        targetIndex = currentList.length;
        taskToEdit = td.dog;
        assertEditSuccess(targetIndex,taskToEdit, currentList);
        
        //edit an empty list
        commandBox.runCommand("clear");
        currentList = td.getTypicalTasks();
        targetIndex = currentList.length/2;
        assertEditSuccess(targetIndex,td.house, currentList);

        //invalid command
        commandBox.runCommand("edit Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void assertEditSuccess(int targetIndex, TestTask taskToEdit, TestTask... currentList) {
        commandBox.runCommand("edit " + targetIndex + " " + taskToEdit.getEditCommand());

        //confirm the new card contains the right data
        TaskCardHandle EditedCard = taskListPanel.navigateToTask(taskToEdit.getDetail().details);
        assertMatching(taskToEdit, EditedCard);
        
        //need to draw the updated list out to put in the expected list
        //confirm the list now contains all previous tasks plus the new edited task
        TestTask[] expectedList = 
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}

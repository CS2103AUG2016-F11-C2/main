package guitests;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;
import static seedu.cmdo.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import static org.junit.Assert.assertTrue;

//@@author A0141128R tested and passed
public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);
        currentList = updateList(targetIndex,currentList);

        //delete the last in the list
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);
        currentList = updateList(targetIndex,currentList);

        //delete from the middle of the list
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);
        currentList = updateList(targetIndex,currentList);

        //invalid index
        invalidCommand("delete " + currentList.length + 1);
        
        //delete something from an empty list
        commandBox.runCommand("clear");
        targetIndex = 1;
        invalidCommand("delete " + targetIndex);
    }
   
    //check if invalid command throws the right error
    private void invalidCommand(String input){
    	commandBox.runCommand(input);
    	assertResultMessage("The task index provided is invalid");
    }
    
    private TestTask[] updateList(int targetIndex, TestTask... currentList){
    	return TestUtil.removeTaskFromList(currentList, targetIndex);
    }
    
    private void runDeleteCommand(int targetIndex){
    	commandBox.runCommand("delete " + targetIndex);
    }
    
    //confirm the list now contains all previous tasks except the deleted task
    private void compareList(TestTask[] expectedRemainder){
    	  assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        runDeleteCommand(targetIndexOneIndexed);
        
        TestTask[] expectedRemainder = updateList(targetIndexOneIndexed,currentList);

        //confirm the list now contains all previous tasks except the deleted task
        compareList(expectedRemainder);

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS,taskToDelete));
    }

}

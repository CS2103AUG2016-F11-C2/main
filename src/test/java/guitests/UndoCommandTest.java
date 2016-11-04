package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.UndoCommand.MESSAGE_UNDO_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import seedu.cmdo.testutil.TestTask;

//@@author A0141128R
public class UndoCommandTest extends ToDoListGuiTest {

    @Test
    public void undo() {
        
        TestTask[] currentList = td.getTypicalTasks();
        currentList = sortList(currentList);
        
        //undo up to 3 times
        for(int i=0;i<3;i++){
        	TestTask taskToAdd = td.car;
        	commandBox.runCommand(taskToAdd.getAddCommand());	
        }
        
        for(int y=0;y<2;y++)
        commandBox.runCommand("undo");
        
        assertUndoSuccess(currentList);
        
        //nothing to undo
        commandBox.runCommand("undo");
        assertResultMessage("Nothing to undo.");
        
        //undo add task with date/time range
        TestTask taskToAdd = td.vacation;
        commandBox.runCommand(taskToAdd.getAddRangeCommand());
        assertUndoSuccess(currentList);
        
        //undo a block command
        TestTask timeToBlock = td.meeting;
        commandBox.runCommand(timeToBlock.getBlockCommand());
        assertUndoSuccess(currentList);
        
        //undo a delete command
        run("delete " + "1",currentList);
       
        //undo a done command
        run("done " + "2", currentList);
        
        //undo a redo
        taskToAdd = td.dog;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("undo");
        run("redo", currentList);
      
        //undo clear command
        run("clear", currentList);
        
        //undo the edit the time of the first task in the list  
        run("edit " + "2 " + "'Eat Buffet'", currentList);


        //undo the edit the priority of the last task in the list
        int targetIndex = currentList.length;
        run("edit " + targetIndex + " /low", currentList);
        
        //undo the action of making last task floating
        targetIndex = 1;
        run("edit " + targetIndex + " floating",currentList);
        
        //undo the change tags of last task
        targetIndex = currentList.length;
        run("edit " + targetIndex + " -dangerous",currentList);
        
        //undo the edit of removing priority of first task using 'rp' or 'remove priority'
        targetIndex = 1;
        run("edit " + targetIndex + " rp", currentList);
        run("edit " + targetIndex + " remove priority", currentList);
        
        //undo the edit of time of task 2
        targetIndex = 2;
        run("edit " + targetIndex + " 1120",currentList);
        
        //undo the edit of date of task 2
        targetIndex = 2;
        run("edit " + targetIndex + " 10/20/2016", currentList);
        
        //undo the edit of task 3 to a range task
        targetIndex = 3;
        run("edit " + targetIndex + " 11/12/2016 1300 to 12/12/2016 1500", currentList);

    }
    
    //confirm the list now contains all previous tasks except the deleted task
    private void compareList(TestTask[] expectedRemainder){
    	  assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }
    
    //run successful commands
    private void run(String input,TestTask... currentList){
    	commandBox.runCommand(input);
    	assertUndoSuccess(currentList);
    }
    
    //sort list
    private TestTask[] sortList(TestTask... currentList){
    	ArrayList<TestTask> list = new ArrayList<TestTask>(Arrays.asList(currentList));
    	Collections.sort(list);
    	return list.toArray(new TestTask[currentList.length]);
    }

    private void assertUndoSuccess(TestTask... currentList) {
    	
    	commandBox.runCommand("undo");
    	
        //sort list
        currentList = sortList(currentList);
    	
    	//confirm the list matches
        compareList(currentList);
        
        //confirm
        assertResultMessage(MESSAGE_UNDO_SUCCESS);
    }

}
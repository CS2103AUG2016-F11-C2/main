package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.cmdo.commons.core.Messages;
import seedu.cmdo.testutil.TestTask;

//@@author A0141006B
public class PowerSearchTest extends ToDoListGuiTest {

    @Test
    public void find() {
        
    	String keyWord = "Mark";
    	assertFindResult("find " + keyWord); //no results
    	
    	//80% match
    	keyWord = "ga";
        assertFindResult("find " + keyWord, td.car, td.zika); //multiple results
        
        //60% match
        keyWord = "pai";
        assertFindResult("find " + keyWord, td.house);
        
        //less than 60% match
        keyWord = "pa";
        assertFindResult("find " + keyWord);
        
        //find after deleting one result
        runCommand("list all");
        runCommand("delete 1");
        keyWord = "mosquit";
        assertFindResult("find " + keyWord,td.zika);
        
        runCommand("clear");
        assertFindResult("find " + keyWord); //no results

        runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    private void runCommand(String input){
    	commandBox.runCommand(input);
    }
    
    private void checkListSize(TestTask... expectedHits){
    	assertListSize(expectedHits.length);
    }
    
    private void checkList(TestTask... expectedHits ){
    	assertTrue(taskListPanel.isListMatching(expectedHits));
    }
    
    private void assertFindResult(String command, TestTask... expectedHits ) {
        runCommand(command);
        checkListSize(expectedHits);
        checkList(expectedHits);
    }
}

package seedu.cmdo.ui;

import java.time.LocalDate;
import java.time.LocalTime;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import seedu.cmdo.commons.events.ui.JumpToListRequestEvent;
import seedu.cmdo.model.task.ReadOnlyTask;

//@@author A0141006B
/**
 * Task Card displays the individual tasks in the list.
 * 
 */
public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    private static final String TOMORROW = "/images/tomorrow.png";
    private static final String OVERDUE = "/images/overdue.png";
    private static final String FLOATING = "/images/floating.png";
    private static final String TODAY = "/images/today.png";
    private static final String THISWEEK = "/images/thisweek.png";
    private static final String NEXTTIME = "/images/nexttime.png";
    

    @FXML
    private HBox cardPane;
    @FXML
    private HBox firstLine;
    @FXML
    private HBox secondLine;
    @FXML
    private HBox thirdLine;
    @FXML
    private Label detail;
    @FXML
    private Rectangle status;
    @FXML
    private Label id;
    @FXML
    private Label st;
    @FXML
    private Label sd;
    @FXML
    private Label dbd;
    @FXML
    private Label dbt;
    @FXML
    private Label priority;
    @FXML
    private Label tags;

    private ReadOnlyTask task;
    private int displayedIndex;

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {   	
    	setStatus();    
    	setId();
    	setStartDateAndTime();
    	setDueDateAndTime();
    	setDetails();    	
    	setTags();
    	setPriority();                   
    }

    private void setPriority() {
    	priority.setText(task.getPriority().value);        
        	switch(task.getPriority().value) {
          		case "low": 
          			priority.setTextFill(Color.LAWNGREEN);
          		break;
          	case "medium":
          			priority.setTextFill(Color.DARKCYAN);
          		break;
          	case "high":
          			priority.setTextFill(Color.RED);
          		break;
          }
	}

	private void setTags() {
    	 tags.setText(task.tagsString());
         tags.setTextFill(Color.MAROON);
         tags.wrapTextProperty();
         tags.setWrapText(true);
	}

	private void setDetails() {
		detail.setText(task.getDetail().details);
    	detail.wrapTextProperty();
    	detail.setWrapText(true);
    }

	private void setId() {
		if(displayedIndex < 10) {
			id.setText("0"+ displayedIndex + "");
		} else {
			id.setText(displayedIndex + "");
		}
	}

	private void setDueDateAndTime() {
    	  dbd.setText(task.getDueByDate().getFriendlyEndString());      
          //format due time
          if(task.getDueByTime().end.equals(LocalTime.MAX)) {
          	dbt.setText(task.getDueByTime().getFriendlyEndString());
          }
          else {
          	dbt.setText(task.getDueByTime().getFriendlyEndString());
          }
	}

	private void setStartDateAndTime() {
    	 sd.setText(task.getDueByDate().getFriendlyStartString());  		
    	  //format start time
         if(task.getDueByTime().start.equals(LocalTime.MAX)) {
         	st.setText(task.getDueByTime().getFriendlyStartString());
         }else {
         	st.setText(task.getDueByTime().getFriendlyStartString());
         } 
	}

	private void setStatus() {
    	//Images for status
    	Image chill = new Image(NEXTTIME);
    	Image overdue = new Image (OVERDUE);
    	Image floating = new Image (FLOATING);
    	Image today = new Image(TODAY);
    	Image thisWeek = new Image(THISWEEK);
    	Image tomorrow = new Image (TOMORROW);
    	
    	status.setStrokeWidth(0.0);    	
    	if(task.getDueByDate().start.isEqual(LocalDate.now())){
    		status.setFill(new ImagePattern(today));
		} else if(task.getDueByDate().start.isBefore(LocalDate.now().plusDays(2)) 
        			&& task.getDueByDate().start.isAfter(LocalDate.now())) {
        		status.setFill(new ImagePattern(tomorrow));	
    	} else if(task.getDueByDate().start.isBefore(LocalDate.now().plusDays(7)) 
    			&& task.getDueByDate().start.isAfter(LocalDate.now().plusDays(1))) {
    		status.setFill(new ImagePattern(thisWeek));
		} else if(task.getDueByDate().start.isBefore(LocalDate.now().plusDays(9999)) 
    			&& task.getDueByDate().start.isAfter(LocalDate.now())) {
    		status.setFill(new ImagePattern(chill));
    	} else if(task.getDueByDate().start.isBefore(LocalDate.now())) {
    		status.setFill(new ImagePattern(overdue));
    		setTextColor(id,detail,sd,st,dbd,dbt,Color.RED);
    	} else {
    		status.setFill(new ImagePattern(floating));
    	}
    	// Done all green    	
    	if(task.checkDone().value) {
    		status.setFill(Color.GREEN);
    		setTextColor(id,detail,sd,st,dbd,dbt,Color.BLACK);
    	} else if (task.getBlock()) {
    		status.setFill(Color.RED);
    	}
	}

	private void setTextColor(Label id, Label detail, Label sd, Label st, 
			Label dbd, Label dbt, Color color) {
		id.setTextFill(color);
		detail.setTextFill(color);
		sd.setTextFill(color);
		st.setTextFill(color);
		dbd.setTextFill(color);
		dbt.setTextFill(color);
	}
    
    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}

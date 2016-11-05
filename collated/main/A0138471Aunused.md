# A0138471Aunused
###### /java/seedu/cmdo/logic/parser/MainParser.java
``` java
//  private void extractDetailUnused() throws IllegalValueException {
//  	checkValidDetailInput();
//  	// Split into '  ...  '
//  	String[] details = args.split("^ '(.+)'$");
//  	// Details only, get rid of anything after the '
//  	String output = new StringBuilder(details[0]).replace(details[0].lastIndexOf("'"), 
// 													details[0].length(), 
// 													"").toString();
//      // Details only, get rid of anything before the ' 
//      for(char o : output.toCharArray()) { 
//      	if(o =='\'') { 
//      		break; 
//      	} 
//      	else 
//      		output=output.replaceFirst(".", ""); 
//      } 
//      // Get rid of the first '
//      output = output.replaceFirst("'","");
//      // Save to instance
//      detailToAdd = output;
//  	// return rear end
//  	args = new StringBuilder(details[0]).substring(details[0].lastIndexOf("'")+1).toString();
//  	// return args without details 
//  	args = args.replace(output, ""); 
//    }

}
```

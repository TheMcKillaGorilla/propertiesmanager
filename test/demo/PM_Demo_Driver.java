package demo;

import java.util.ArrayList;
import properties_manager.InvalidXMLFileFormatException;
import properties_manager.PropertiesManager;

/**
 * This driver demonstrates simple usage of the 
 * Properties Manager API.
 * 
 * @author THE McKilla Gorilla (accept no imposters)
 * @version 2.0
 */
public class PM_Demo_Driver {
    
    // THIS PROVIDES AND EXAMPLE OF PROPERTIES
    // TO BE PLACED IN AND RETRIEVED
    enum MyProps { MY_STRING, MY_STRING_OPTIONS };
    
    public static void main(String[] args) {
        try {
            // FIRST GET THE PropertiesManager SINGLETON OBJECT
            PropertiesManager props = PropertiesManager.getPropertiesManager();

            // SET THE DATA PATH WHERE THE XML FILE TO LOAD IS LOCATED
            props.setPropertiesDataPath("test/junit_test_beds/data/");
            
            // LOAD THE XML FILE (MUST BE IN THE DATA PATH DIRECTORY)
            props.loadProperties("valid_test_properties.xml");
        
            // GET AN INDIVIDUAL PROPERTY
            String myString = props.getProperty(MyProps.MY_STRING);
            System.out.println("myString was loaded as: " + myString);
        
            // GET A LIST OF VALUES
            ArrayList<String> optionsList = props.getPropertyOptionsList(MyProps.MY_STRING_OPTIONS);
            System.out.println("The first option loaded is " + optionsList.get(0));
            
            // ONCE YOU'RE DONE USING PROPERTIES YOU CAN REMOVE THEM
            props.removeProperty("MY_STRING");
            System.out.println("myString was loaded as: " + props.getProperty("MY_STRING"));
            
            // OR JUST REMOVE ALL OF THEM
            props.clear();            
            System.out.println("Number of Property Options Lists Left: " + props.getNumPropertyOptionsLists());
        }
        catch(InvalidXMLFileFormatException ixffe) {
            // PROVIDE CUSTOM FEEDBACK TO THE USER HERE TELLING THEM THAT
            // THE XML FILE DID NOT LOAD PROPERLY, WHICH IS LIKELY DUE 
            // EITHER BECAUSE THE PATH OR FORMAT IS INCORRECT
            System.out.println("AN ERROR OCCURRED!!!");
        }
    }
}
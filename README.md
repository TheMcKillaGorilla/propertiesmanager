
# Welcome
Welcome to the Properties Manager API page. Note that the Properties Manager API can be used independently or integrated into the [Desktop Java Framework](https://bitbucket.org/mckilla-gorilla/desktop-java-framework/wiki/Home), which also makes use of the [jTPS API](https://bitbucket.org/mckilla-gorilla/jtps-api/wiki/Home). These APIs together are useful for making and learning about Java desktop applications that make use of user interfaces.

## What is the Properties Manager API?

It's a Java framework for loading textual properties from XML files. This can be useful for initializing textual user interface controls that are language independent, meaning the language-dependent text (i.e. English, Spanish, etc) that might appear in labels, buttons, prompts, can be setup for use in a generic way and the language can be swapped out dynamically upon user request by loading a properties file and then loading those textual values into the interface controls. Note that this framework does not include any user interface components, it simply helps load data from files into a format that would be easy to use for a user interface application or framework. Note that this framework was created by [McKilla Gorilla](http://www.cs.stonybrook.edu/~richard) in order to help students learn about frameworks and how to author code that can serve multiple purposes (i.e. code reuse).

Also note that as with all code it is imperfect. Some decisions made in the creation of this library were done simply to demonstrate certain techniques rather than optimization. In addition, it emphasizes ease of use so that new users can easily understand how it works. It has been useful to instructors over the years in [Computer Science III](https://www.cs.stonybrook.edu/students/Undergraduate-Studies/courses/CSE219) at [Stony Brook University](http://www.cs.stonybrook.edu), where it has been used for dozens of projects. Note that students become true developers when they can look at others' code and have opinions about it and think of better ways of doing things, so any recommendations for this API or for our CS III Learning Suite of tools are welcome. To provide feedback send email to [mckilla-gorilla@gmail.com](mailto:mckilla-gorilla@gmail.com). 


## The Properties Manager XML Format

Note that this API makes use of a very specifically formatted XML file that conforms to the **properties_schema.xsd** specification. This schema is included with the source code and is used for validating all XML properties files that get loaded. The format of such an XML file is such that one would list all uniquely named properties, and then uniquely named properties lists. An example of such an XML file might is provided below. Note that the **MY_STRING** and **MY_STRING_2** properties could be named anything, but the program will need to know these names in order to access the property values. The same goes for **MY_STRING_OPTIONS** and **MY_STRING_OPTIONS_2**. Note that you may put your XML properties file anywhere you like and name it what you like. In fact it may be useful to have multiple properties files (like one for each language).

```xml
<?xml version="1.0" encoding="UTF-8"?>
<properties>
    <property_list>        
        <!-- DESKTOP JAVA FRAMEWORK CONTROLS SETTINGS -->
        <property name="MY_STRING"      value="Hello, World"></property>
        <property name="MY_STRING_2"    value="Goodbye, Cruel World"></property>
    </property_list>

    <property_options_list>
        <property_options name="MY_STRING_OPTIONS">
            <option>January</option>
            <option>February</option>
            <option>March</option>
        </property_options>
        <property_options name="MY_STRING_OPTIONS_2">
            <option>Monday</option>
            <option>Tuesday</option>
        </property_options>
    </property_options_list>
</properties>
```

## Property Names as Keys

Note that the Properties Manager API stores all properties as mappings of Strings to Strings, but the **addProperty** and **getProperty** methods take Object types as key arguments. These functions will simply get the **toString** values of the object arguments provided and use them as keys. The reason is to let the user use **enum** values as keys as done in the example above. Using **enum** values helps one avoid spelling mistake type bugs. 


## Properties Versus Property Options

The Properties Manager API lets one load either properties or properties options lists for use in an application, so what's the difference? Well, a User Interface may use a single piece of text on a button or label that can be loaded from a **property**.  One could have the same property provided in two different XML files for two different languages and then load the property as needed and use it to set button or label text.

A property options list would be useful for when there are multiple textual choices for a user to make, like in a combo box or a group of radio buttons. So one could load a properties options list for a particular language XML file and load the array of choices found there into such a control or series of controls.

## Using Properties Manager

In order to use the API you'll need to create an XML file using a format similar to the example above and then put it in a location where your application will be able to find it. You'll then need to get the properties manager, set the data path to that location, load the properties file, and then get individual properties and/or properties lists as needed. Note that you may then subsequently remove individual properties and lists or clear out the contents altogether. Here is an example usage:

```java
package demo;

import java.util.ArrayList;
import properties_manager.InvalidXMLFileFormatException;
import properties_manager.PropertiesManager;

// THIS PROVIDES AND EXAMPLE OF PROPERTIES
// TO BE PLACED IN AND RETRIEVED
enum MyProps { MY_STRING, MY_STRING_OPTIONS };

/**
 * This driver demonstrates simple usage of the 
 * Properties Manager API.
 * 
 * @author THE McKilla Gorilla
 * @version 2.0
 */
public class PM_Tester {
    public static void main(String[] args) {
        try {
            // FIRST GET THE PropertiesManager SINGLETON OBJECT
            PropertiesManager props = PropertiesManager.getPropertiesManager();

            // SET THE DATA PATH WHERE THE XML FILE TO LOAD IS LOCATED
            props.setDataPath("test/junit_test_beds/data/");
            
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
```


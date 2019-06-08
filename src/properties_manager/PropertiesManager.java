package properties_manager;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * PropertiesManager.java
 * 
 * This class is used for loading properties from XML files that can
 * then be used throughout an application. For example, for loading 
 * data to initialize and application or for loading all the textual
 * cues for a User Interface. This abstracts out these values and
 * lets the developer specify these values in an XML file rather than
 * have to change source code.
 * 
 * Once properties are loaded into the properties object they can be
 * retrieved as needed until they are cleared out or replaced. Note that 
 * this class, i.e. PropertiesManager, has methods for loading, accessing,
 * and removing properties and that it is a singleton, and so can be 
 * accessed from anywhere. To get the singleton properties manager, 
 * just use the static accessor:
 * 
 * PropertiesManager props = PropertiesManager.getPropertiesManager();
 * 
 * Now you can access all the properties it currently stores using
 * the getProperty method. Note that the properties_schema.xsd file
 * specifies how these files are to be constructed. Also note that
 * it is designed to be used with enumerations such that properties
 * are sent in as objects and keyed using their toStrings.
 * 
 * @author THE McKilla Gorilla (accept no imposters)
 * @version 2.0
 */
public class PropertiesManager {    
    // THIS CLASS IS A SINGLETON, AND HERE IS THE ONLY OBJECT
    private static PropertiesManager singleton = null;

    // WE'LL STORE PROPERTIES HERE
    private HashMap<String, String> properties;

    // LISTS OF PROPERTY OPTIONS CAN BE STORED HERE
    private HashMap<String, ArrayList<String>> propertyOptionsLists;

    // THIS WILL LOAD THE XML FOR US
    private XMLUtilities xmlUtil;
    
    // THIS IS THE CUSTOMLY SET DIRECTORY WHERE THE XML DATA
    // FILES ARE TO BE KEPT, THIS MUST BE SET BEFORE LOADING
    private String propertiesDataPath;

    // THESE CONSTANTS ARE USED FOR LOADING PROPERTIES AS THEY ARE
    // THE ESSENTIAL ELEMENTS AND ATTRIBUTES
    public static final String PROPERTY_ELEMENT                 = "property";
    public static final String PROPERTY_LIST_ELEMENT            = "property_list";
    public static final String PROPERTY_OPTIONS_LIST_ELEMENT    = "property_options_list";
    public static final String PROPERTY_OPTIONS_ELEMENT         = "property_options";    
    public static final String OPTION_ELEMENT                   = "option";
    public static final String NAME_ATT                         = "name";
    public static final String VALUE_ATT                        = "value";

    // THIS IS THE PROPERTIES FILE AGAINST WHICH ALL VALIDATION WILL BE DONE
    public static final String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";

    /**
     * The constructor is private because this is a singleton.
     */
    private PropertiesManager() {
        properties = new HashMap<>();
        propertyOptionsLists = new HashMap<>();
        xmlUtil = new XMLUtilities();
    }

    /**
     * Accessor method for getting the data path used for loading
     * properties files.
     * 
     * @return The currently set data path.
     */
    public String getPropertiesDataPath() {
        return propertiesDataPath;
    }

    /**
     * Mutator method for setting the data path. Note that this
     * must be done before calling loadProperties but only needs
     * to be done once if all properties XML files are in the
     * same directory.
     * 
     * @param initDataPath Value to use to set the data path, it 
     * should be a valid path to a directory where the XML properties
     * files are to be stored.
     */
    public void setPropertiesDataPath(String initPropertiesDataPath) {
        propertiesDataPath = initPropertiesDataPath;
    }

    /**
     * This is the static accessor for the singleton.
     * 
     * @return The singleton properties manager object.
     */
    public static PropertiesManager getPropertiesManager(){
        // IF IT'S NEVER BEEN RETRIEVED BEFORE THEN
        // FIRST WE MUST CONSTRUCT IT
        if (singleton == null) {
            // WE CAN CALL THE PRIVATE CONSTRCTOR FROM WITHIN THE CLASS
            singleton = new PropertiesManager();
        }
        // RETURN THE SINGLETON
        return singleton;
    }

    /**
     * This function adds the (property, value) tuple to the 
     * properties manager. This is useful for adding additional
     * properties dynamically (i.e. not from the XML file) as
     * needed.
     * 
     * @param property Key, i.e. property type for this pair.
     * 
     * @param value The data for this pair.
     */
    public void addProperty(Object property, String value) {
        properties.put(property.toString(), value);
    }

    /**
     * This function adds the (propertyOptions, list) tuple to the
     * properties manager. This is useful for adding additional
     * property lists dynamically (i.e. not from the XML file) as
     * needed.
     * 
     * @param propertyOptions Key, i.e. property options type for this pair.
     * 
     * @param list The data for this pair.
     */
    public void addPropertyOptionsList(Object propertyOptions, ArrayList<String> list) {
        this.propertyOptionsLists.put(propertyOptions.toString(), list);
    }

    /**
     * This method clears out all data in the manager, leaving no
     * properties or property options lists.
     */
    public void clear() {
        this.properties.clear();
        this.propertyOptionsLists.clear();
    }

    /**
     * This method gets the number of properties currently stored
     * by the manager.
     * 
     * @return The number of properties the manager has.
     */
    public int getNumProperties() {
        return this.properties.keySet().size();
    }

    /**
     * This method gets the number of property options lists currently
     * stored by the manager.
     * 
     * @return The number of property options lists the manager has.
     */
    public int getNumPropertyOptionsLists() {
        return this.propertyOptionsLists.keySet().size();
    }    

    /**
     * Accessor method for getting a property from this manager.
     * 
     * @param property The key for getting a property.
     * 
     * @return The value associated with the key.
     */
    public String getProperty(Object property) {
        return properties.get(property.toString());
    }

    /**
     * Accessor method for testing to see if a particular
     * property has been loaded.
     * 
     * @param property The key for getting a property.
     * 
     * @return true if property has been loaded, false otherwise.
     */
    public boolean hasProperty(Object property) {
        return properties.containsKey(property.toString());
    }

    /**
     * Accessor method for testing to see if a particular
     * property has a boolean value of true. If it does, true
     * is returned, else false. Note that this should only be
     * be done on properties with boolean data, not textual 
     * or numeric.
     * 
     * @param property The key for getting a property.
     * 
     * @return true if the property's value is true, false otherwise.
     */
    public boolean isTrue(Object property) {
        return Boolean.valueOf(getProperty(property.toString()));
    }

    /**
     * Accessor method for getting a property options list associated
     * with the property key.
     * 
     * @param property The key for accessing the property options list.
     * 
     * @return The property options list associated with the key.
     */
    public ArrayList<String> getPropertyOptionsList(Object property) {
        return propertyOptionsLists.get(property.toString());
    }

    /**
     * This function loads the xmlDataFile in this property manager, first
     * make sure it's a well formed document according to the rules specified
     * in the xmlSchemaFile.
     * 
     * @param xmlDataFile XML document to load.
     * 
     * @param xmlSchemaFile Schema that the XML document should conform to.
     * 
     * @throws InvalidXMLFileFormatException This is thrown if the XML file
     * is invalid.
     */
    public void loadProperties(String xmlDataFile)
            throws InvalidXMLFileFormatException {
        // NOTE THAT THE DATA PATH MUST ALREADY HAVE BEEN LOADED
        xmlDataFile = propertiesDataPath + "/" + xmlDataFile;

        // GET THE SCHEMA PATH
        String xmlSchemaFile = getClass().getResource(PROPERTIES_SCHEMA_FILE_NAME).getPath();
        
        // FIRST LOAD THE FILE
        Document doc = xmlUtil.loadXMLDocument(xmlDataFile, xmlSchemaFile);
        
        // NOW LOAD ALL THE PROPERTIES
        Node propertyListNode = xmlUtil.getNodeWithName(doc, PROPERTY_LIST_ELEMENT);
        ArrayList<Node> propNodes = xmlUtil.getChildNodesWithName(propertyListNode, PROPERTY_ELEMENT);
        for(Node n : propNodes)
        {
            NamedNodeMap attributes = n.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++)
            {
                Node att = attributes.getNamedItem(NAME_ATT);
                String attName = attributes.getNamedItem(NAME_ATT).getTextContent();
                String attValue = attributes.getNamedItem(VALUE_ATT).getTextContent();
                properties.put(attName, attValue);
            }
        }
        
        // AND THE PROPERTIES FROM OPTION LISTS
        Node propertyOptionsListNode = xmlUtil.getNodeWithName(doc, PROPERTY_OPTIONS_LIST_ELEMENT);
        if (propertyOptionsListNode != null)
        {
            ArrayList<Node> propertyOptionsNodes = xmlUtil.getChildNodesWithName(propertyOptionsListNode, PROPERTY_OPTIONS_ELEMENT);
            for (Node n : propertyOptionsNodes)
            {
                NamedNodeMap attributes = n.getAttributes();
                String name = attributes.getNamedItem(NAME_ATT).getNodeValue();
                ArrayList<String> options = new ArrayList<>();
                propertyOptionsLists.put(name, options);
                ArrayList<Node> optionsNodes = xmlUtil.getChildNodesWithName(n, OPTION_ELEMENT);
                for (Node oNode : optionsNodes)
                {
                    String option = oNode.getTextContent();
                    options.add(option);
                }
            }
        }
    }

    /**
     * This function removes the propertyToRemove property
     * from the manager. Accessing it afterwards will return
     * null unless added back again.
     * 
     * @param propertyToRemove The property to remove from
     * the manager.
     */
    public void removeProperty(Object propertyToRemove) {
        this.properties.remove(propertyToRemove);
    }
    
    /**
     * This function removes the propertyOptionsLitToRemove list
     * from the manager. Accessing it afterwards will return
     * null unless added back again.
     * 
     * @param propertyToRemove The property list to remove from
     * the manager.
     */    
    public void removePropertyOptionsList(Object propertyOptionsListToRemove) {
        this.propertyOptionsLists.remove(propertyOptionsListToRemove);
    }
}
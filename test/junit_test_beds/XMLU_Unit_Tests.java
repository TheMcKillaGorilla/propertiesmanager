package junit_test_beds;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import properties_manager.InvalidXMLFileFormatException;
import static properties_manager.PropertiesManager.PROPERTIES_SCHEMA_FILE_NAME;
import properties_manager.XMLUtilities;

/**
 * XMLU_Unit_Tests.java
 * 
 * This file provides a test bed for the XMLUtilities class, testing both valid
 * and invalid usage to make sure it provides well defined behavior for both.
 * 
 * @author McKilla Gorilla
 * @version 2.0
 */
public class XMLU_Unit_Tests {
    // A CORRECTLY FORMATTED XML DATA FILE FOR TESTING XMLUtilities
    public static final String VALID_XML_RESOURCE_PATH = "data/valid_test_properties.xml";
    
    // INCORRECTLY FORMATTED XML FILES FOR TESTING XMLUtilities
    public static final String[] INVALID_XML_RESOURCE_PATHS = 
    {   "data/invalid_test_properties_1.xml",
        "data/invalid_test_properties_2.xml",
        "data/invalid_test_properties_3.xml"};
    
    /**
     * This test method tests the XMLUtilities' validateXMLDoc method
     * using a properly formatted (i.e. valid) XML file.
     */
    @Test
    public void testValidateXMLUsingValidFile() {
        // FIRST CONSTRUCT THE XML UTILITIES OBJECT WE'LL BE TESTING
        XMLUtilities xmlUtil = new XMLUtilities();
        
        // WE KNOW THIS IS A GOOD PATH
        String testXMLPath = getClass().getResource(VALID_XML_RESOURCE_PATH).getPath();
        String defaultSchemaPath = xmlUtil.getClass().getResource(PROPERTIES_SCHEMA_FILE_NAME).getPath();
        
        // FIRST LOAD THE FILE
        boolean success = xmlUtil.validateXMLDoc(testXMLPath, defaultSchemaPath);
        Assert.assertTrue(success);
    }

    
    /**
     * This test method tests the XMLUtilities' validateXMLDoc method
     * using improperly formatted (i.e. valid) XML files.
     */    
    @Test
    public void testValidateXMLUsingInvalidFile() {
        // FIRST CONSTRUCT THE XML UTILITIES OBJECT WE'LL BE TESTING
        XMLUtilities xmlUtil = new XMLUtilities();
        
        // WE KNOW THIS IS A GOOD PATH
        for (int i = 0; i < INVALID_XML_RESOURCE_PATHS.length; i++) {
            String testXMLPath = getClass().getResource(INVALID_XML_RESOURCE_PATHS[i]).getPath();
            String defaultSchemaPath = xmlUtil.getClass().getResource(PROPERTIES_SCHEMA_FILE_NAME).getPath();
        
            // FIRST LOAD THE FILE
            boolean success = xmlUtil.validateXMLDoc(testXMLPath, defaultSchemaPath);
            Assert.assertFalse(success);
        }
    }
    
    /**
     * This test method tests the XMLUtilities' loadXMLDocument method
     * using a properly formatted (i.e. valid) XML file.
     */
    @Test
    public void testLoadUsingValidFile() {
        try {
            // FIRST CONSTRUCT THE XML UTILITIES OBJECT WE'LL BE TESTING
            XMLUtilities xmlUtil = new XMLUtilities();
        
            // WE KNOW THIS IS A GOOD PATH
            String testXMLPath = getClass().getResource(VALID_XML_RESOURCE_PATH).getPath();
            String defaultSchemaPath = xmlUtil.getClass().getResource(PROPERTIES_SCHEMA_FILE_NAME).getPath();
        
            // LOAD THE DOC
            Document doc = xmlUtil.loadXMLDocument(testXMLPath, defaultSchemaPath);
            
            // DID IT SUCCEED?
            boolean success = doc != null;
            Assert.assertTrue(success);

        } catch(InvalidXMLFileFormatException ixffe) {
            // EXCEPTION SHOULD NOT BE THROWN IN THIS TEST
            Assert.fail("Valid XML file was not loaded");
        }
    }

    /**
     * This test method tests the XMLUtilities' loadXMLDocument method
     * using improperly formatted (i.e. valid) XML files.
     */    
    @Test
    public void testLoadXMLDocumentUsingInvalidFile() {
        try {
            // FIRST CONSTRUCT THE XML UTILITIES OBJECT WE'LL BE TESTING
            XMLUtilities xmlUtil = new XMLUtilities();
        
            // WE KNOW THIS IS A GOOD PATH
            String testXMLPath = getClass().getResource(INVALID_XML_RESOURCE_PATHS[0]).getPath();
            String defaultSchemaPath = xmlUtil.getClass().getResource(PROPERTIES_SCHEMA_FILE_NAME).getPath();
        
            // LOAD THE DOC
            Document doc = xmlUtil.loadXMLDocument(testXMLPath, defaultSchemaPath);
            
            // DID IT SUCCEED?
            boolean success = doc != null;
            Assert.fail("Invalid XML file should not have loaded");

        } catch(InvalidXMLFileFormatException ixffe) {
            // EXCEPTION SHOULD NOT BE THROWN IN THIS TEST
            Assert.assertTrue(true);
        }
    }
}

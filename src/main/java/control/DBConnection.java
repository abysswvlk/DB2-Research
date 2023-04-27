package control;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Person;
import model.Phone;
import model.PhoneGuide;
import oracle.nosql.driver.NoSQLHandle;
import oracle.nosql.driver.NoSQLHandleConfig;
import oracle.nosql.driver.NoSQLHandleFactory;
import oracle.nosql.driver.Region;
import oracle.nosql.driver.iam.SignatureProvider;
import oracle.nosql.driver.ops.DeleteRequest;
import oracle.nosql.driver.ops.GetRequest;
import oracle.nosql.driver.ops.GetResult;
import oracle.nosql.driver.ops.PutRequest;
import oracle.nosql.driver.ops.QueryRequest;
import oracle.nosql.driver.ops.QueryResult;
import oracle.nosql.driver.ops.TableLimits;
import oracle.nosql.driver.ops.TableRequest;
import oracle.nosql.driver.ops.TableResult;
import oracle.nosql.driver.values.JsonOptions;
import oracle.nosql.driver.values.MapValue;
import oracle.nosql.driver.values.StringValue;

public class DBConnection {
    private NoSQLHandle connection; // Connection object for NoSQLHandle
    private String filePEMPath = "C:\\Users\\HP\\Desktop\\DB2-Research-Final\\arce-04-23-00-28.pem"; // Path of .pem file
    
    /**
     * Constructor to establish a connection with NoSQLHandle
     */
    public DBConnection() {
        connection = getNoSQLConnection();
    }
    
    /**
     * Method to close the connection with NoSQLHandle
     */
    public void close() {
        connection.close();
    }
    
    /**
     * Method to create a connection with NoSQLHandle
     * 
     * @return Returns an instance of NoSQLHandle with the provided configurations
     */
    private NoSQLHandle getNoSQLConnection() {
        // Creates a new instance of SignatureProvider with the required parameters
        SignatureProvider authProvider = new SignatureProvider(
            "ocid1.tenancy.oc1..aaaaaaaay4fiu37ujv6gj5befd5l6t6ikg4bfapwqbmk7t5q2chdnyqyudda",
            "ocid1.user.oc1..aaaaaaaac3bexxqkh2mwsquhntrv55n4mlarc4joti2dbvdfvwlu2be34eqq",
            "d8:3f:d4:19:00:78:48:62:83:25:ac:29:8d:9d:20:d5",
            new File(filePEMPath),
            "xd".toCharArray());
        // Returns an instance of NoSQLHandle with the specified Region and authentication provider
        return (NoSQLHandleFactory.createNoSQLHandle( new NoSQLHandleConfig(Region.MX_QUERETARO_1, authProvider)));
    }

    
    /**
     * Deletes a table with a given name.
     *
     * @param tableName Name of the table to delete
     * 
     * References:
     *  - Brey, D., & Rubin, D. (2020, March 31). Write a simple Hello World application and connect it to NoSQL Database under 15 mins. Oracle. https://blogs.oracle.com/nosql/post/15-minutes-to-hello-world
     */
    public void debugDeleteTable(String tableName) {
        TableRequest req = new TableRequest().setStatement(
            "DROP TABLE if exists " + tableName);

        TableResult tr = connection.tableRequest(req);

        /**
         * Table deletion is async so wait for the table to become deleted 
         * before returning.  This call will wait for a total of up to 2 minutes
         * for the table to become deleted and will check every 500 milliseconds
         * to see if the table has been deleted
         */
        tr.waitForCompletion(connection, 120000, 250);

        /*  
         * We waited long enough.  If the table still exists then 
         * something went wrong
         */
        if (tr.getTableState() != TableResult.State.DROPPED)  {
            throw new RuntimeException("Unable to delete table " + tableName + 
                " " + tr.getTableState());
        }
    }
    
    /**
     * Creates a table with a given name and column definitions.
     *
     * @param tableName Name of the table to create
     * @param columnDefs List of column definitions for the table
     * 
     * References:
     *  - Brey, D., & Rubin, D. (2020, March 31). Write a simple Hello World application and connect it to NoSQL Database under 15 mins. Oracle. https://blogs.oracle.com/nosql/post/15-minutes-to-hello-world
     */
    public void debugCreateTable(String tableName, List<String> columnDefs) {
        String columnString = String.join(",", columnDefs);
        TableRequest req = new TableRequest().setStatement(
            "CREATE TABLE " + tableName + " (" + columnString + ")");

        TableResult tr = connection.tableRequest(req);

        /**
         * Table creation is async so wait for the table to become created
         * before returning. This call will wait for a total of up to 2 minutes
         * for the table to become created and will check every 500 milliseconds
         * to see if the table has been created
         */
        tr.waitForCompletion(connection, 120000, 250);

        /*
         * We waited long enough. If the table still doesn't exist then
         * something went wrong
         */
        if (tr.getTableState() != TableResult.State.ACTIVE) {
            throw new RuntimeException("Unable to create table " + tableName +
                    " " + tr.getTableState());
        }
    }

    /**

    This method creates three tables: PhoneGuide, Phone, and People, each with their respective attributes as columns.
    PhoneGuide table has a composite primary key with phoneID and personID, while Phone and People tables have single
    primary keys. This method throws an Exception if an error occurs during table creation.
    @throws Exception if an error occurs during table creation
    
    */
    public void debugCreateTables() throws Exception{
    debugCreateTablesAUX("PhoneGuide(phoneID INTEGER, personID INTEGER, primary key (phoneID, personID)");
    debugCreateTablesAUX("Phone(phone INTEGER, company STRING, primary key (phone)");
    debugCreateTablesAUX("People(personID INTEGER, name STRING, lastName STRING, primary key (personID)");
    }
    /**
     *
     * This method deletes three tables: PhoneGuide, Phone, and People, if they exist in the database.
     *
     */
    public void debugDeleteTables(){
    debugDeleteTable("PhoneGuide");
    debugDeleteTable("Phone");
    debugDeleteTable("People");
    }
    
    /**
     * References:
     *  - Brey, D., & Rubin, D. (2020, March 31). Write a simple Hello World application and connect it to NoSQL Database under 15 mins. Oracle. https://blogs.oracle.com/nosql/post/15-minutes-to-hello-world
     */
    public void debugCreateTablesAUX(String tableInfo) 
	throws Exception {
        TableRequest req = new TableRequest().setStatement(
          	"CREATE TABLE if not exists " + tableInfo + ")");
        /** reading, writing, storage **/
        req.setTableLimits(new TableLimits(100, 100, 1));
        TableResult tr = connection.tableRequest(req);

        /**
  	 * Table creation is async so wait for the table to become active 
	 * before returning.  This call will wait for a total of up to 2 minutes
	 * for the table to become up and will check every 250 milliseconds
	 * to see if the table has become active
	 */
        tr.waitForCompletion(connection, 120000, 250);

        /*  
	 * We waited long enough.  If the table is still not active then 
	 * something went wrong
	 */
        if (tr.getTableState() != TableResult.State.ACTIVE)  {
            throw new Exception("Unable to create table " +
 			    tr.getTableState());
        }
     }
    
    /**
     * Inserts a new person into the People table
     * 
     * @param personID ID of the person to insert
     * @param name     First name of the person
     * @param lastName Last name of the person
     */
    public void insertPeople(int personID, String name, String lastName) {
        MapValue value = new MapValue()
            .put("personID", personID)
            .put("name", name)
            .put("lastName", lastName);

        PutRequest putRequest = new PutRequest()
            .setValue(value).setTableName("People");
        connection.put(putRequest);
    }

    /**
     * Removes a person from the People table based on their ID
     * 
     * @param personID ID of the person to remove
     */
    public void removePerson(int personID) {
        MapValue key = new MapValue().put("personID", personID);
        DeleteRequest deleteRequest = new DeleteRequest()
            .setKey(key).setTableName("People");
        connection.delete(deleteRequest);
    }

    /**
     * Inserts a new phone into the Phone table
     * 
     * @param phone   Phone number to insert
     * @param company Company name associated with the phone
     */
    public void insertPhone(int phone, String company) {
        MapValue value = new MapValue().put("phone", phone).put("company", company);

        PutRequest putRequest = new PutRequest()
            .setValue(value).setTableName("Phone");
        connection.put(putRequest);
    }

    /**
     * Removes a phone from the Phone table based on its number
     * 
     * @param phone Phone number to remove
     */
    public void removePhone(int phone) {
        DeleteRequest deleteRequest = new DeleteRequest()
            .setKey(new MapValue().put("phone", phone)).setTableName("Phone");
        connection.delete(deleteRequest);
    }

    /**
     * Inserts a mapping between a person and a phone into the PhoneGuide table
     * 
     * @param personID ID of the person
     * @param phoneID  Phone number
     */
    public void insertPhoneGuide(int personID, int phoneID) {
        MapValue value = new MapValue()
            .put("phoneID", phoneID).put("personID", personID);

        PutRequest putRequest = new PutRequest()
            .setValue(value).setTableName("PhoneGuide");

        connection.put(putRequest);
    }

    /**
     * Removes a mapping between a person and a phone from the PhoneGuide table
     * 
     * @param personID ID of the person
     * @param phoneID  Phone number
     */
    public void removePhoneGuide(int personID, int phoneID) {
        MapValue key = new MapValue()
            .put("phoneID", phoneID).put("personID", personID);

        DeleteRequest deleteRequest = new DeleteRequest()
            .setKey(key).setTableName("PhoneGuide");

        connection.delete(deleteRequest);
    }

    /**
     * Debug function that inserts a new person, phone, and PhoneGuide entry
     * 
     * @param idPerson ID of the person to insert
     * @param name     First name of the person
     * @param lastName Last name of the person
     * @param phone    Phone number to insert
     * @param company  Company name associated with the phone
     */
    public void debugInsertPhoneGuide(int idPerson, String name, String lastName, int phone, String company) {
        insertPeople(idPerson, name, lastName);
        insertPhone(phone, company);
        insertPhoneGuide(idPerson, phone);
    }

    /**
     * This method retrieves a JsonObject with information about a phone given its number
     *
     * @param phone the phone number to retrieve
     * @return a JsonObject with the phone information or null if not found
     */
    public JsonObject getPhone(int phone) {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey(new MapValue().put("phone", phone));
        getRequest.setTableName("Phone");

        long before = System.currentTimeMillis(); // Start measuring the time it takes to retrieve the phone

        GetResult gr = connection.get(getRequest);

        if (gr.toString() != null) {
            String json = gr.getValue().toJson(new JsonOptions());
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.getAsJsonObject();
        } else {
            return null;
        }
    }

    /**
     * This method retrieves a JsonObject with information about a person given their ID
     *
     * @param personID the ID of the person to retrieve
     * @return a JsonObject with the person information or null if not found
     */
    public JsonObject getPerson(int personID) {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey(new MapValue().put("personID", personID));
        getRequest.setTableName("People");
        GetResult gr = connection.get(getRequest);

        if (gr.toString() != null) {
            String json = gr.getValue().toJson(new JsonOptions());
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.getAsJsonObject();
        } else {
            return null;
        }
    }

    /**
     * This method retrieves a JsonObject with information about a phone guide entry given the phone ID and person ID
     *
     * @param phoneID  the phone ID of the entry to retrieve
     * @param personID the person ID of the entry to retrieve
     * @return a JsonObject with the phone guide entry information or null if not found
     */
    public JsonObject getPhoneGuide(int phoneID, int personID) {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey(new MapValue().put("phoneID", phoneID).put("personID", personID));
        getRequest.setTableName("PhoneGuide");

        GetResult gr = connection.get(getRequest);

        if (gr.toString() != null) {
            String json = gr.getValue().toJson(new JsonOptions());
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.getAsJsonObject();
        } else {
            return null;
        }
    }

    /**
     * This method retrieves information about a person given their ID and prints it to the console
     *
     * @param personID the ID of the person to retrieve
     */
    public void getPeople(int personID) {
        GetRequest getRequest = new GetRequest()
                .setKey(new MapValue().put("personID", personID))
                .setTableName("PhoneGuide");

        GetResult result = connection.get(getRequest);

        if (result.getValue() == null) {
            System.out.println("No se encontró ninguna persona con el id " + personID);
        } else {
            MapValue value = result.getValue().asMap();
            StringValue name = value.get("personID").asString();
            StringValue lastName = value.get("phoneID").asString();

            System.out.println("La persona con el id " + personID + " es " + name + " " + lastName);
        }
    }
    
    /**
     * Retrieves a list of phone IDs associated with a given person ID
     * 
     * @param personID ID of the person to retrieve phone IDs for
     * @return List of phone IDs associated with the given person ID
     */
    public List<Integer> getPhonesByPeopleID(int personID) {
        // Build SQL statement to retrieve phone IDs associated with the given person ID
        String statement = "SELECT * FROM PhoneGuide WHERE PhoneGuide.personID = " + personID;

        // Execute the SQL statement and retrieve the results
        QueryRequest req = new QueryRequest().setStatement(statement);
        QueryResult tr = connection.query(req);

        // Parse the results and extract the phone IDs
        List<Integer> result = new ArrayList();
        tr.getResults().forEach(s -> {
            result.add(Integer.parseInt(s.get("phoneID").toString()));
        });

        // Return the list of phone IDs
        return result;
    }

    /**
     * Retrieves all people from the database
     * 
     * @return PersonController containing all people from the database
     */
    public PersonController getPeople(){
        // Build SQL statement to retrieve all people from the database
        String statement = "SELECT * FROM People";

        // Execute the SQL statement and retrieve the results
        QueryRequest req = new QueryRequest().setStatement(statement);
        QueryResult tr = connection.query(req);

        // Create a PersonController to store the retrieved people
        PersonController personController = new PersonController();

        // Parse the results and create Person objects for each row
        for (MapValue s: tr.getResults()){
            int idPerson = Integer.parseInt(s.get("personID").toString());
            String name = s.get("name").toString().replaceAll("\"", "");
            String lastName = s.get("lastName").toString().replaceAll("\"", "");;

            Person person = new Person(idPerson, name, lastName);
            personController.addPerson(person);
        }

        // Return the PersonController containing all retrieved people
        return personController;
    }

    /**
     * Retrieves all phones from the database
     * 
     * @return PhoneController containing all phones from the database
     */
    public PhoneController getPhone(){
        // Build SQL statement to retrieve all phones from the database
        String statement = "SELECT * FROM Phone";

        // Execute the SQL statement and retrieve the results
        QueryRequest req = new QueryRequest().setStatement(statement);
        QueryResult tr = connection.query(req);

        // Create a PhoneController to store the retrieved phones
        PhoneController phoneController = new PhoneController();

        // Parse the results and create Phone objects for each row
        for (MapValue s: tr.getResults()){
            int number = Integer.parseInt(s.get("phone").toString());
            String company = s.get("company").toString().replaceAll("\"", "");

            Phone phone = new Phone(number, company);
            phoneController.addPhone(phone);
        }

        // Return the PhoneController containing all retrieved phones
        return phoneController;
    }
    
    /**
     * Retrieves all PhoneGuide entries from the database and creates a PhoneGuideController with them.
     *
     * @return a PhoneGuideController object containing all the PhoneGuide entries in the database.
     */
    public PhoneGuideController getPhoneGuide(){
        // Define the SQL query to retrieve all PhoneGuide entries
        String statement = "SELECT * FROM PhoneGuide";

        // Create a QueryRequest object with the SQL query
        QueryRequest req = new QueryRequest().setStatement(statement);

        // Execute the query and store the results in a QueryResult object
        QueryResult tr = connection.query(req);

        // Create a new PhoneGuideController object to hold the PhoneGuide entries
        PhoneGuideController phoneGuideController = new PhoneGuideController();

        // Loop through each result in the QueryResult object
        for (MapValue s: tr.getResults()){
            // Retrieve the phoneID and personID values from the current result
            int number = Integer.parseInt(s.get("phoneID").toString());
            int idPerson = Integer.parseInt(s.get("personID").toString());

            // Create a new PhoneGuide object with the retrieved values
            PhoneGuide phoneGuide = new PhoneGuide(idPerson, number);

            // Add the new PhoneGuide object to the PhoneGuideController object
            phoneGuideController.addPhoneGuide(phoneGuide);
        }

        // Return the PhoneGuideController object
        return phoneGuideController;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.DBConnection;
import control.PersonController;
import control.PhoneController;
import control.PhoneGuideController;
import java.util.List;
import java.util.Random;
import model.Person;
import model.Phone;
import model.PhoneGuide;
import com.github.javafaker.Faker;
import java.util.Locale;
/**
 *
 * @author Ailer
 */
/******************************
 * Debug Insert Info
 ******************************/
public class debugInsertInfo {
    // Define constants
    private static final int nPeople = 5;
    private static final int nPlusPhones = 20;
    private static final boolean createTables = true;
    private static final boolean deleteTables = false;
    private static final boolean insertData = true;

    public static void main(String[] args) {
        try {
            // Establish database connection
            DBConnection connection = new DBConnection();
            
            // Delete tables if specified
            if (deleteTables){
                connection.debugDeleteTables();
            }
            
            // Create tables if specified
            if (createTables){
                connection.debugCreateTables();
            }
            
            // Insert data if specified
            if (insertData){
                // Initialize variables and objects
                Faker faker = new Faker(new Locale("es", "CR"));
                String name = "";
                String lastName = "";
                long inicio = System.currentTimeMillis();
                Random random = new Random();
                PhoneGuideController tempPhoneGuideController = new PhoneGuideController();
                PersonController tempPersonController = new PersonController();
                PhoneController tempPhoneController = new PhoneController();

                // Add random PhoneGuides to the driver
                for (int i = 0; i < nPeople; i++) {
                    int generatedID = random.nextInt(900000000) + 100000000;  // 9-digit ascending ID
                    while (tempPersonController.exist(generatedID)) {
                        generatedID = random.nextInt(900000000) + 100000000;
                    }
                    name = faker.name().firstName();
                    lastName = faker.name().lastName();
                    Person person = new Person(generatedID, name, lastName);
                    String company = new String[]{"Claro", "Movistar", "Kolbi"}[i % 3]; // Rotate among the 3 companies
                    int generatedNumber = random.nextInt(90000000) + 10000000; // Generates a random 8 digit number
                    while (tempPhoneController.exist(generatedNumber)) {
                        generatedNumber = random.nextInt(90000000) + 10000000;
                    }
                    Phone phone = new Phone(generatedNumber, company);
                    connection.debugInsertPhoneGuide(generatedID, name, lastName, generatedNumber, company);
                    PhoneGuide phoneGuide = new PhoneGuide(generatedID, generatedNumber);
                    tempPhoneGuideController.addPhoneGuide(phoneGuide);
                    tempPersonController.addPerson(person);
                    tempPhoneController.addPhone(phone);
                }

                // Retrieves a list of all person IDs
                List<Integer> listIDS = tempPersonController.getAllPersonIds();

                // Iterates nPlusPhones times to generate phone numbers and assign them to people
                for (int i = 0; i < nPlusPhones; i++) {

                    // Selects a random person ID from the list of all person IDs
                    int randomIndex = random.nextInt(listIDS.size());
                    int randomIDPerson = listIDS.get(randomIndex);

                    // Selects a company for the phone number (rotates among the 3 companies)
                    String company = new String[]{"Claro", "Movistar", "Kolbi"}[i % 3]; 

                    // Generates a random 8 digit number and checks if it already exists
                    int generatedNumber = random.nextInt(90000000) + 10000000;
                    while (tempPhoneController.exist(generatedNumber)) {
                        generatedNumber = random.nextInt(90000000) + 10000000;
                    }

                    // Creates new phone, person, and phone guide objects
                    Phone phone = new Phone(generatedNumber, company);
                    Person person = tempPersonController.getPersonById(randomIDPerson);
                    PhoneGuide phoneGuide = new PhoneGuide(randomIDPerson, generatedNumber);

                    // Inserts phone and phone guide data into the database
                    connection.insertPhone(generatedNumber, company);
                    connection.insertPhoneGuide(randomIDPerson, generatedNumber);

                    // Adds the phone, person, and phone guide objects to their respective temporary controllers
                    tempPhoneGuideController.addPhoneGuide(phoneGuide);
                    tempPersonController.addPerson(person);
                    tempPhoneController.addPhone(phone);
                }

                // Prints out the phone, person, and phone guide data as strings
                System.out.println(tempPhoneController.toString());
                System.out.println(tempPersonController.toString());
                System.out.println(tempPhoneGuideController.toString());

                // Calculates and prints the total runtime of the program
                long fin = System.currentTimeMillis();
                long tiempoTotal = fin - inicio;
                long minutos = tiempoTotal / (60 * 1000);
                long segundos = (tiempoTotal / 1000) % 60;
                System.out.println("El programa tardó " + minutos + " minutos y " + segundos + " segundos en finalizar.");
            }
            // Closes the database connection
            connection.close();
            
          // Catches any exceptions that occur during program execution and exits with a status code of -1
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
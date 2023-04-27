/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import java.util.ArrayList;
import java.util.List;
import model.Person;
/**
 *
 * @author Ailer and Paublo
 */
public class PersonController implements Cloneable {

    private List<Person> personList;

    /**
     * Constructor for the PersonController class.
     * Initializes an empty ArrayList for the personList.
     */
    public PersonController() {
        personList = new ArrayList<>();
    }

    /**
     * Adds a person to the personList.
     * @param person the Person object to be added to the list
     */
    public void addPerson(Person person) {
        personList.add(person);
    }

    /**
     * Checks if a person with a certain ID exists in the personList.
     * @param idCheck the ID to be checked
     * @return true if the person exists, false otherwise
     */
    public boolean exist(int idCheck) {
        for (Person person : personList) {
            if (person.getIdPerson() == idCheck) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a person from the personList.
     * @param idPerson the ID of the person to be removed
     */
    public void removePerson(int idPerson) {
        for (Person person : personList) {
            if (person.getIdPerson() == idPerson) {
                personList.remove(person);
                break;
            }
        }
    }

    /**
     * Updates a person's name and last name in the personList.
     * @param person the updated Person object
     */
    public void updatePerson(Person person) {
        for (Person p : personList) {
            if (p.getIdPerson() == person.getIdPerson()) {
                p.setName(person.getName());
                p.setLastName(person.getLastName());
                break;
            }
        }
    }

    /**
     * Returns a Person object with a specific ID.
     * @param idPerson the ID of the person to be returned
     * @return a Person object if it exists, null otherwise
     */
    public Person getPersonById(int idPerson) {
        for (Person person : personList) {
            if (person.getIdPerson() == idPerson) {
                return person;
            }
        }
        return null;
    }

    /**
     * Returns the entire list of Person objects.
     * @return the personList
     */
    public List<Person> getAllPersons() {
        return personList;
    }

    /**
     * Returns a list of all Person IDs in the personList.
     * @return a list of all Person IDs
     */
    public List<Integer> getAllPersonIds() {
        List<Integer> personIds = new ArrayList<>();
        for (Person person : personList) {
            personIds.add(person.getIdPerson());
        }
        return personIds;
    }

    /**
     * Checks if a person with a specific ID exists in the personList.
     * @param idPerson the ID to be checked
     * @return true if the person exists, false otherwise
     */
    public boolean personExists(int idPerson) {
        for (Person person : personList) {
            if (person.getIdPerson() == idPerson) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overrides the clone method to create a deep copy of the PersonController object.
     * @return a deep copy of the PersonController object
     */
    @Override
    public PersonController clone() {
        try {
            PersonController cloned = (PersonController) super.clone();
            cloned.personList = new ArrayList<>(personList.size());
            for (Person person : personList) {
                cloned.personList.add(person.clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Overrides the toString method to return a string representation of the personList.
     * @return a string representation of the personList
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Person person : personList) {
            sb.append(person.toString()).append("\n");
        }
        return sb.toString();
    }
}

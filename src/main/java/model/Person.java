/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ailer and Paublo
 */
/**
 * This class represents a Person object, which has an ID, name, and last name.
 */
public class Person implements Cloneable {
    private int idPerson;        // the ID of the person
    private String name;        // the name of the person
    private String lastName;    // the last name of the person

    /**
     * Creates a new Person object with the specified ID, name, and last name.
     * @param idPerson the ID of the person
     * @param name the name of the person
     * @param lastName the last name of the person
     */
    public Person(int idPerson, String name, String lastName) {
        this.idPerson = idPerson;
        this.name = name;
        this.lastName = lastName;
    }

    /**
     * Returns the ID of the person.
     * @return the ID of the person
     */
    public int getIdPerson() {
        return idPerson;
    }

    /**
     * Sets the ID of the person.
     * @param idPerson the ID to set
     */
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * Returns the name of the person.
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the last name of the person.
     * @return the last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns a string representation of the Person object, which includes
     * the ID, name, and last name.
     * @return a string representation of the Person object
     */
    @Override
    public String toString() {
        return "Person{" +
                "idPerson=" + idPerson +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Creates and returns a copy of this Person object.
     * @return a copy of this Person object
     */
    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}


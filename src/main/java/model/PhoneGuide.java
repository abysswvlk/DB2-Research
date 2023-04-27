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
 * A class representing a phone guide entry for a person, including their ID and phone number.
 */
public class PhoneGuide implements Cloneable{
    private int idPerson;
    private int number;

    /**
     * Constructs a new PhoneGuide object with the specified ID and phone number.
     * @param idPerson the ID of the person for this entry
     * @param number the phone number for this entry
     */
    public PhoneGuide(int idPerson, int number) {
        this.idPerson = idPerson;
        this.number = number;
    }

    /**
     * Returns the ID of the person for this entry.
     * @return the ID of the person
     */
    public int getIdPerson() {
        return idPerson;
    }

    /**
     * Sets the ID of the person for this entry.
     * @param idPerson the ID of the person
     */
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * Returns the phone number for this entry.
     * @return the phone number
     */
    public int getPhoneNumber() {
        return number;
    }

    /**
     * Sets the phone number for this entry.
     * @param number the phone number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns a string representation of the value for the specified variable.
     * @param variable the variable to retrieve the value for ("ID" or "NUMBER")
     * @return a string representation of the value for the specified variable, or null if an invalid variable is passed
     */
    public String getByVariable(String variable) {
        switch(variable.toUpperCase()) {
            case "ID":
                return String.valueOf(this.getIdPerson());
            case "NUMBER":
                return String.valueOf(this.getPhoneNumber());
            default:
                return null;
        }
    }

    /**
     * Returns a clone of this PhoneGuide object.
     * @return a clone of this PhoneGuide object
     */
    @Override
    public PhoneGuide clone() {
        try {
            return (PhoneGuide) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since PhoneGuide is Clonable
            throw new InternalError(e);
        }
    }
    
    /**
     * Returns a string representation of this PhoneGuide object.
     * @return a string representation of this PhoneGuide object
     */
    @Override
    public String toString() {
        return String.format("id %s - number: %d", idPerson, number);
    }
}


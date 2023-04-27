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
 * A Phone class representing a phone number and the associated company.
 */
public class Phone implements Cloneable {
    /**
     * The phone number.
     */
    private int number;
    /**
     * The company associated with the phone number.
     */
    private String company;
    
    /**
     * Constructor for the Phone class.
     * @param number the phone number to set
     * @param company the company to set
     */
    public Phone(int number, String company) {
        this.number = number;
        this.company = company;
    }
    
    /**
     * Getter for the phone number.
     * @return the phone number
     */
    public int getPhoneNumber() {
        return number;
    }
    
    /**
     * Setter for the phone number.
     * @param number the phone number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }
    
    /**
     * Getter for the company associated with the phone number.
     * @return the company
     */
    public String getCompany() {
        return company;
    }
    
    /**
     * Setter for the company associated with the phone number.
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }
    
    /**
     * Returns a string representation of the Phone object.
     * @return the string representation of the Phone object
     */
    @Override
    public String toString() {
        return "Number [number=" + number + ", company=" + company + "]";
    }
    
    /**
     * Clones the Phone object.
     * @return a clone of the Phone object
     */
    @Override
    public Phone clone() {
        try {
            return (Phone) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}


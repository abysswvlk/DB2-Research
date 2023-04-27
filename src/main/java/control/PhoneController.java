/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import java.util.List;
import model.Phone;
/**
 *
 * @author Ailer and Paublo
 */
/**
 * PhoneController class that manages a list of phone numbers.
 */
public class PhoneController implements Cloneable {
    
    private List<Phone> phoneList;

    /**
     * Constructor that initializes the numberList as an empty ArrayList.
     */
    public PhoneController() {
        phoneList = new ArrayList<>();
    }

    /**
     * Adds a phone number to the numberList.
     * @param number The phone number to be added.
     */
    public void addPhone(Phone number) {
        phoneList.add(number);
    }
    
    /**
     * Removes a phone number from the numberList by its phone number.
     * @param phone The phone number to be removed.
     */
    public void removePhone(int phone) {
        for (Phone ph : phoneList) {
            if (ph.getPhoneNumber() == phone) {
                phoneList.remove(ph);
                break;
            }
        }
    }

    /**
     * Updates a phone number in the numberList by its phone number.
     * @param number The phone number to be updated.
     */
    public void updateNumber(Phone number) {
        for (Phone num : phoneList) {
            if (num.getPhoneNumber() == number.getPhoneNumber()) {
                num.setCompany(number.getCompany());
                break;
            }
        }
    }

    /**
     * Gets a phone number from the numberList by its phone number.
     * @param number The phone number to be retrieved.
     * @return The Phone object corresponding to the given phone number, or null if not found.
     */
    public Phone getPhoneByPhoneID(int number) {
        for (Phone num : phoneList) {
            if (num.getPhoneNumber() == number) {
                return num;
            }
        }
        return null;
    }
    
    /**
     * Checks if a phone number exists in the numberList.
     * @param phoneNumber The phone number to be checked.
     * @return True if the phone number exists, false otherwise.
     */
    public boolean exist(int phoneNumber) {
        for (Phone num : phoneList) {
            if (num.getPhoneNumber() == phoneNumber) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets a List of all phone numbers in the numberList.
     * @return The List of Phone objects in the numberList.
     */
    public List<Phone> getAllNumbers() {
        return phoneList;
    }

    /**
     * Overrides the clone() method to create a deep copy of the PhoneController object.
     * @return A deep copy of the PhoneController object.
     */
    @Override
    public PhoneController clone() {
        try {
            PhoneController cloned = (PhoneController) super.clone();
            cloned.phoneList = new ArrayList<>(phoneList.size());
            for (Phone number : phoneList) {
                cloned.phoneList.add(number.clone());
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Overrides the toString() method to create a string representation of the PhoneController object.
     * @return A string representation of the PhoneController object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Phone number : phoneList) {
            sb.append(number.toString()).append("\n");
        }
        return sb.toString();
    }
}

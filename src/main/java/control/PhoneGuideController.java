/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import model.PhoneGuide;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ailer and Paublo
 */

/**
 * This class represents a controller for managing PhoneGuide objects.
 */
public class PhoneGuideController implements Cloneable {
    
    private List<PhoneGuide> phoneGuides;

    /**
     * Constructs a new PhoneGuideController object.
     */
    public PhoneGuideController() {
        this.phoneGuides = new ArrayList<>();
    }

    /**
     * Adds a new PhoneGuide object to the controller's list of phone guides.
     *
     * @param phoneGuide the PhoneGuide object to add.
     */
    public void addPhoneGuide(PhoneGuide phoneGuide) {
        boolean exists = false;
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getIdPerson() == phoneGuide.getIdPerson() && pg.getPhoneNumber() == phoneGuide.getPhoneNumber()) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            phoneGuides.add(phoneGuide);
        }
    }

    /**
     * Removes a PhoneGuide object from the controller's list of phone guides.
     *
     * @param phoneGuide the PhoneGuide object to remove.
     */
    public void removePhoneGuide(PhoneGuide phoneGuide) {
        if (phoneGuides.contains(phoneGuide)) {
            phoneGuides.remove(phoneGuide);
        }
    }

    /**
     * Finds a PhoneGuide object in the controller's list of phone guides by the person's ID.
     *
     * @param id_person the ID of the person to search for.
     * @return the PhoneGuide object associated with the person's ID, or null if not found.
     */
    public PhoneGuide findPhoneGuideById(int id_person) {
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getIdPerson() == id_person) {
                return pg;
            }
        }
        return null;
    }

    /**
     * Finds a PhoneGuide object in the controller's list of phone guides by the phone number.
     *
     * @param number the phone number to search for.
     * @return the PhoneGuide object associated with the phone number, or null if not found.
     */
    public PhoneGuide findPhoneGuideByNumber(int number) {
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getPhoneNumber() == number) {
                return pg;
            }
        }
        return null;
    }

    /**
     * Finds a PhoneGuide object in the controller's list of phone guides by both person's ID and phone number.
     *
     * @param id_person the ID of the person to search for.
     * @param number the phone number to search for.
     * @return the PhoneGuide object associated with the person's ID and phone number, or null if not found.
     */
    public PhoneGuide findPhoneGuide(int id_person, int number) {
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getIdPerson() == id_person && pg.getPhoneNumber() == number) {
                return pg;
            }
        }
        return null;
    }

    /**
     * Returns the list of all PhoneGuide objects in the controller.
     *
     * @return the list of all PhoneGuide objects in the controller.
     */
    public List<PhoneGuide> getAllPhoneGuides() {
        return phoneGuides;
    }
    
    /**
     * Returns a list of phone numbers associated with a given person ID.
     *
     * @param idPerson the ID of the person to search for.
     * @return a list of phone numbers associated with the given person ID.
     */
    public List<Integer> getPersonsPhones(int idPerson) {
        List<Integer> phones = new ArrayList<>();
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getIdPerson() == idPerson) {
                phones.add(pg.getPhoneNumber());
            }
        }
        return phones;
    }

    /**
     * Finds the ID of the person associated with a given phone number.
     *
     * @param number the phone number to search for.
     * @return the ID of the person associated with the given phone number, or -1 if not found.
     */
    public int getPersonByNumber(int number) {
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getPhoneNumber() == number) {
                return pg.getIdPerson();
            }
        }
        return -1;
    }

    /**
     * Removes the PhoneGuide object associated with the given phone number.
     *
     * @param phoneNumber the phone number to remove.
     */
    public void removePhone(int phoneNumber) {
        PhoneGuide pgToRemove = null;
        for (PhoneGuide pg : phoneGuides) {
            if (pg.getPhoneNumber() == phoneNumber) {
                pgToRemove = pg;
                break;
            }
        }
        if (pgToRemove != null) {
            phoneGuides.remove(pgToRemove);
        }
    }

    /**
     * Creates a clone of this PhoneGuideController object.
     *
     * @return a clone of this PhoneGuideController object.
     */
    @Override
    public PhoneGuideController clone() {
        try {
            PhoneGuideController clone = (PhoneGuideController) super.clone();
            clone.phoneGuides = new ArrayList<>(this.phoneGuides.size());
            for (PhoneGuide phoneGuide : this.phoneGuides) {
                clone.phoneGuides.add(phoneGuide.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Returns a string representation of this PhoneGuideController object.
     *
     * @return a string representation of this PhoneGuideController object.
     */
    @Override
    public String toString() {
        String sb = "";
        for (PhoneGuide pg : phoneGuides) {
            sb += pg.toString() + "\n";
        }
        return sb;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Ailer and Paublo
 */
public class UIController {
    /**
     * This method sets the default locale to English and customizes the text displayed on JOptionPane buttons.
     * This is useful for ensuring consistent user interface across different systems and configurations.
     */
    public static void setEN(){
        Locale.setDefault(Locale.ENGLISH); // Set default locale to English
        // Customize the text displayed on JOptionPane buttons
        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "OK");
    }
    
    /**
     * 
     * @param isDarkMode
     * @param toPrincipalColor
     * @param componentTrees
     * @param frame 
     */
    public static void firstSetMode(boolean isDarkMode, List<Component> toPrincipalColor, List<Component> componentTrees, JFrame frame){
        if (isDarkMode){
            UIController.setLightMode(toPrincipalColor, componentTrees, frame);
            UIController.setDarkMode(toPrincipalColor, componentTrees, frame);
        } else {
            UIController.setDarkMode(toPrincipalColor, componentTrees, frame);
            UIController.setLightMode(toPrincipalColor, componentTrees, frame);
        }
    }
    
    /**
     * Displays a yes/no dialog with the specified message and background color.
     * @param message the message to display in the dialog
     * @param color the background color for the dialog and buttons
     * @return true if the user selects "Yes", false if they select "No"
     */
    public static boolean makeYesNoDialog(String message, Color color) {
        Locale.setDefault(Locale.ENGLISH);
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        optionPane.setInitialValue(JOptionPane.YES_OPTION); // set YES_OPTION as default value

        optionPane.getComponent(3).setBackground(color); // Button panel

        JPanel panelPrincipal = (JPanel) optionPane.getComponent(0);
        JPanel panelSecundario = (JPanel) panelPrincipal.getComponent(0);

        for (Component c : panelSecundario.getComponents()) {
            c.setBackground(color);
        }

        JDialog dialog = optionPane.createDialog("Confirm");

        dialog.setVisible(true);
        int result = (Integer) optionPane.getValue(); // get the value selected by the user
        return result == 0;
    }
    
    /**
     * Creates and returns a custom JOptionPane popup window
     * with the specified message, background color, and title.
     * @param message the message to display in the popup window
     * @param color the background color for the popup window and buttons
     * @param msjType the type of message to display in the popup window
     * @param title the title for the popup window
     * @return the customized popup window as a JDialog object
     */
    public static JDialog makeDialog(String message, Color color, int msjType, String title) {
        JOptionPane optionPane = new JOptionPane(message, msjType);

        optionPane.getComponent(3).setBackground(color); // Button panel

        JPanel panelPrincipal = (JPanel) optionPane.getComponent(0);
        JPanel panelSecundario = (JPanel) panelPrincipal.getComponent(0);

        for (Component c : panelSecundario.getComponents()) {
            c.setBackground(color);
        }

        JDialog dialog = optionPane.createDialog(title);
        return dialog;
    }

    /**
     * Method used to give focus and attention to a specific JFrame window.
     * @param frame the JFrame window to give focus to
     */    
    public static void focusMainGUI(JFrame frame) {
        frame.setEnabled(true); 
        frame.toFront(); 
        frame.requestFocus();
    }
    
    /**
     * Centers a JDialog on the screen.
     * @param aDialog the JDialog to be centered
     */
    public static void centerDialog(JDialog aDialog){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int pointX = (int)((dimension.getWidth() - aDialog.getWidth()) / 2);
        int pointY = (int)((dimension.getHeight() - aDialog.getHeight()) / 2);
        aDialog.setLocation(pointX, pointY);
    }
    
    /**
    * Sets the Look and Feel of a JFrame.
    * @param frame The JFrame to which the Look and Feel should be set.
    * @param lafClassName The name of the Look and Feel class to be set.
    */
    public static void setLookAndFeel(JFrame frame, String lafClassName) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (lafClassName.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                }
            }
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("No se pudo establecer el tema seleccionado.");
        }
    }
    
    /**
    * Sets a filter to allow only numbers in a JTextField and
    * limits the number of characters that it will accept.
    * @param textFieldToSet The JTextField to which the filter is to be applied.
    * @param maxLength The maximum length allowed for text in the JTextField.
    */
    public static void setOnlyNumber(JTextField textFieldToSet, int maxLength) {
        AbstractDocument doc = (AbstractDocument) textFieldToSet.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            String regex = "^[0-9]*$";

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches(regex) && (fb.getDocument().getLength() + text.length() - length) <= maxLength) { // Verify input and length
                    super.replace(fb, offset, length, text, attrs);
                } else { // If the input is not a number or exceeds the maximum length, the input is rejected.
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches(regex) && (fb.getDocument().getLength() + string.length()) <= maxLength) {// Verify input and length
                    super.insertString(fb, offset, string, attr);
                } else { // If the input is not a number or exceeds the maximum length, the input is rejected.
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    
    /**
    * Sets the appearance mode with the specified colors.
    *
    * @param toPrincipalColor List of components to which the main color will be set.
    * @param componentTrees List of components to update.
    * @param principalColor Main color to set.
    * @param textColor Text color to set.
    * @param accentColor Accent color to set.
    * @param controlColor Control color to set.
    * @param nimbusBaseColor Secondary background color to set.
    * @param nimbusDisabledTextColor Disabled text color to set.
    * @param nimbusBlueGreyColor Secondary color to set.
    * @param nimbusSelectionBackgroundColor Selection background color to set.
    * @param frame The application frame.
    */
    public static void setModeAUX(List<Component> toPrincipalColor, List<Component> componentTrees, Color principalColor, Color textColor, Color accentColor,
            Color controlColor, Color nimbusBaseColor, Color nimbusDisabledTextColor, 
            Color nimbusBlueGreyColor, Color nimbusSelectionBackgroundColor, JFrame frame) {
        // Set the principal color to the specified components
        toPrincipalColor.forEach(component -> {
            component.setBackground(principalColor);
        });
        // Text colors
        UIManager.put("info", textColor);
        UIManager.put("text", textColor);
        
         // Other colors
        UIManager.put("control",  controlColor);
        UIManager.put("nimbusBase", nimbusBaseColor);
        UIManager.put("nimbusAlertYellow", accentColor);
        UIManager.put("nimbusDisabledText", nimbusDisabledTextColor);
        UIManager.put("nimbusFocus", principalColor);
        UIManager.put("nimbusLightBackground", principalColor);
        UIManager.put("nimbusBlueGrey", nimbusBlueGreyColor);
        UIManager.put("nimbusSelectionBackground", nimbusSelectionBackgroundColor);
        
        // Update look and feel
        UIController.setLookAndFeel(frame,"Metal");
        UIController.setLookAndFeel(frame,"Nimbus");
        
        UIManager.put("OptionPane.background", principalColor);
        UIManager.put("Panel.background", principalColor);
       // JOptionPane.setDefaultLookAndFeelDecorated(true);
        // Update the specified components
        for(Component component : componentTrees) {
            SwingUtilities.updateComponentTreeUI(component);
        }

        // Repaint components
        frame.repaint();
        frame.revalidate();
        frame.pack();
    }

    /**
    * Sets the light appearance mode with predefined colors.
    *
    * @param toPrincipalColor List of components to which the main background color will be applied.
    * @param componentTrees List of components to update.
    * @param frame The frame in which the components are located.
    */
    public static void setLightMode(List<Component> toPrincipalColor, List<Component> componentTrees, JFrame frame) {
        System.out.println("setLightMode()");
        Color principalColor = new Color(255, 255, 255);
        Color textColor = new Color(0, 0, 0); // Main Text
        Color accentColor =  new Color(240, 240, 240);
        Color controlColor = new Color(240, 240, 240);
        Color nimbusBaseColor = new Color(125, 125, 125);
        Color nimbusDisabledTextColor = new Color(142, 143, 145);
        Color nimbusBlueGreyColor = new Color(180, 180, 180);
        Color nimbusSelectionBackgroundColor = new Color(57, 105, 138);

        setModeAUX(toPrincipalColor, componentTrees, principalColor, textColor, accentColor, controlColor, nimbusBaseColor, 
                nimbusDisabledTextColor, nimbusBlueGreyColor, nimbusSelectionBackgroundColor, frame
        );
    }
    
    /**
     * Sets the dark mode on the specified components, using the colors provided.
     * provided.
     * 
     * @param toPrincipalColor List of components to apply the main background color to.
     * @param componentTrees List of components to update.
     * @param frame The frame in which the components are located.
     */
    public static void setDarkMode(List<Component> toPrincipalColor, List<Component> componentTrees, JFrame frame) {
        System.out.println("setDarkMode()");
        Color principalColor = new Color(32, 32, 32); // Main background
        Color textColor = new Color(220, 220, 220); // Main text
        Color accentColor = new Color(66, 135, 245); // Accent Color
        Color controlColor = new Color(220, 220, 220); // Control color
        Color nimbusBaseColor = new Color(32, 32, 32); // Secondary background color
        Color nimbusDisabledTextColor = new Color(80, 80, 80); // Disabled Text Color
        Color nimbusBlueGreyColor = new Color(20, 20, 20); // Secondary gray color
        Color nimbusSelectionBackgroundColor = new Color(57, 105, 138);

        setModeAUX(toPrincipalColor, componentTrees, principalColor, textColor, accentColor, controlColor, nimbusBaseColor, 
                nimbusDisabledTextColor, nimbusBlueGreyColor, nimbusSelectionBackgroundColor, frame
        );
    }
    
    /**
     * Filters a text string according to certain parameters.
     * 
     * @param fieldText The text string to filter.
     * @param valueToCheck The value to search for in the string.
     * @param parseal If true, the comparison will be case insensitive.
     * @param flagCheck If false, the comparison will not be performed and true will be returned.
     * @return Returns true if the string contains or is equal to the value to search for, as appropriate.
     */
    public static boolean filterByVariable(String fieldText, String valueToCheck, boolean parseal, boolean flagCheck){
        if (!flagCheck){
            return true;
        } else if (parseal){
            return fieldText.toUpperCase().contains(valueToCheck.toUpperCase());
        }
        return fieldText.toUpperCase().equals(valueToCheck.toUpperCase());
    }
    
    /**
     * Converts a list of integers to an array of strings.
     * @param listData the list of integers to convert.
     * @return an array of strings with the elements of the list.
     */    
    public static String[] makeListData(List<Integer> listData) {
        String[] arrayListData = new String[listData.size()];
        for (int i = 0; i < listData.size(); i++) {
            arrayListData[i] = String.valueOf(listData.get(i));
        }
        return arrayListData;
    }
    
}

package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;


public class GUI {
    public static Translator translator = new JSONTranslator();
    public static CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
    public static LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JPanel languagePanel = new JPanel();
            JComboBox languageField = new JComboBox(languageCodeConverter.getLangList());
            languageField.setEditable(true);
            languagePanel.add(new JLabel("Language: "));
            languagePanel.add(languageField);

            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabelText);
            resultPanel.add(resultLabel);

            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new BorderLayout()); // makes it full width
            JList<String> countryList = new JList<>(countryCodeConverter.getCountryList());
            countryPanel.add(countryList);

            languageField.addItemListener((ItemEvent e) -> {
                String language = (String) languageField.getSelectedItem();
                String country = countryList.getSelectedValue();
                resultLabel.setText(runTranslate(language, country));
            });

            countryList.addListSelectionListener((ListSelectionEvent e ) -> {
                String language = (String) languageField.getSelectedItem();
                String country = countryList.getSelectedValue();
                resultLabel.setText(runTranslate(language, country));
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }

    public static String runTranslate(String language, String country) {
        String result = translator.translate(
                countryCodeConverter.fromCountry(country).toLowerCase(),
                languageCodeConverter.fromLanguage(language));
        System.out.println(result);
        if (result == null) {
            result = "no translation found!";
        }
        return result;
    }
}

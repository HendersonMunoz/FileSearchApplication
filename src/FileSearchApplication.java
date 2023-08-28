import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

// Automatically generated code, as I build the GUI components via the Swing UI Designer tool
public class FileSearchApplication {
    private JPanel mainFrame;
    private JButton browseButton;
    private JTextField filePathField;
    private JScrollPane matchingLinesResult;
    private JButton searchButton;
    private JTextField userInput;
    private JButton saveButton;
    private JTextArea resultsTextArea;
    private JButton clearButton;
    private File selectedFile;

    public FileSearchApplication() {
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        // Search Button - code block.
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchWord = userInput.getText();
                if (searchWord.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Please enter a search word or phrase.");
                } else if (selectedFile == null) {
                    JOptionPane.showMessageDialog(mainFrame, "Please select a file to search.");
                } else {
                    String result = processFile(selectedFile, searchWord);
                    JTextArea textArea = (JTextArea) ((JViewport) matchingLinesResult.getViewport()).getView();
                    textArea.setText(result);
                }
            }
        });

        // Save Button - code block.
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultText = resultsTextArea.getText();
                if (resultText.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "No results to save.");
                    return; // No results to save
                }

                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    try {
                        // Create a FileWriter to write the results to the selected file
                        FileWriter fileWriter = new FileWriter(selectedFile);
                        fileWriter.write(resultText);
                        fileWriter.close();

                        JOptionPane.showMessageDialog(mainFrame, "Results saved successfully.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error saving results: " + ex.getMessage());
                    }
                }
            }
        });

        // Clear Button - code block.
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the file path, search field, and the text area.
                filePathField.setText("");
                userInput.setText("");
                resultsTextArea.setText("");
            }
        });
    }

    private String processFile(File file, String searchWord) {
        StringBuilder matchingLines = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchWord)) {
                    matchingLines.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchingLines.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Search Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new FileSearchApplication().mainFrame);
        frame.pack();
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileSearchApplication {
    private JPanel mainFrame;
    private JButton browseButton;
    private JTextField displayFilePath;
    private JScrollPane matchingLinesResult;
    private JButton searchButton;
    private JButton saveButton;
    private JTextField userInput;
    private File selectedFile; // Added to store the selected file

    public FileSearchApplication() {
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    displayFilePath.setText(selectedFile.getAbsolutePath());
                }
            }
        });

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
                    ((JTextArea) matchingLinesResult.getViewport().getView()).setText(result);
                }
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

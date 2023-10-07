package com.raven.main;

import com.raven.model.ModelUser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainSystem extends javax.swing.JFrame {

    private final ModelUser user;
    private String selectedPdfFilePath; // Store the selected PDF file path

    private JButton uploadButton;
    private JButton printButton;
    private JButton previewButton; // Added a preview button
    private JLabel pdfFileNameLabel;
    private JLabel userEmailLabel;

    public MainSystem(ModelUser user) {
        this.user = user;
        initComponents();
        getContentPane().setBackground(new Color(255, 255, 255));
        lbUser.setText(user.getUserName());

        if (user.isSignedIn()) {
            userEmailLabel.setText("Signed in as: " + user.getEmail());
        } else {
            userEmailLabel.setText("Not signed in");
        }
    }

    @SuppressWarnings("unchecked")
    // ... (your existing code)

    // Create a method to handle printing
    private void print() {
        try {
            if (selectedPdfFilePath != null) {
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                printerJob.setPrintable(new PdfPrintable(selectedPdfFilePath)); // Use a custom Printable
                if (printerJob.printDialog()) {
                    printerJob.print();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a PDF file to print.", "No PDF Selected", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Printing error: " + ex.getMessage(), "Printing Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Create a method to handle previewing
    private void preview() {
        // Implement your PDF preview functionality here
        // You'll need to create a custom PDF viewer panel
    }

    // Add an action event to your print button
    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {
        print();
    }

    // Add an action event to your upload button
    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedPdfFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            // Display the selected PDF file name in your GUI
            pdfFileNameLabel.setText("Selected PDF: " + fileChooser.getSelectedFile().getName());
        }
    }

    // Add an action event to your preview button
    private void previewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        preview();
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Handle logout action here, e.g., close the current window and return to the login page
        dispose(); // Close the current window
        // Add code to return to the login page
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbUser;
    // ... (other components)

    // End of variables declaration//GEN-END:variables

    private void initComponents() {
        lbUser = new javax.swing.JLabel();
        uploadButton = new JButton("Upload PDF");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadButtonActionPerformed(e);
            }
        });

        printButton = new JButton("Print PDF");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printButtonActionPerformed(e);
            }
        });

        previewButton = new JButton("Preview PDF"); // Create a preview button
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewButtonActionPerformed(e);
            }
        });

        JButton logoutButton = new JButton("Logout"); // Create a logout button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutButtonActionPerformed(e);
            }
        });

        pdfFileNameLabel = new JLabel("Selected PDF: None");
        userEmailLabel = new JLabel("Signed in as: ");

        // Add the components to the GUI layout
        getContentPane().setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(printButton);
        buttonPanel.add(previewButton); // Add the preview button
        buttonPanel.add(logoutButton);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(pdfFileNameLabel, BorderLayout.CENTER);
        getContentPane().add(userEmailLabel, BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MainSystem");
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    public static void main(ModelUser args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ModelUser user = new ModelUser(); // Create a user instance here if needed
                new MainSystem(user).setVisible(true);
            }
        });
    }
}

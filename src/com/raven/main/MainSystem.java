package com.raven.main;

import com.raven.model.ModelUser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.rendering.PDFRenderer;

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
                printerJob.setPrintable(new PdfPrintable(selectedPdfFilePath) {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                }); // Use a custom Printable
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
    try {
        if (selectedPdfFilePath != null) {
            PDDocument document = PDDocument.load(new File(selectedPdfFilePath));

            // Create a PDFRenderer to render the PDF pages
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Create a JPanel to hold the preview label
            JPanel previewPanel = new JPanel();
            
            previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS));


            int targetWidth = 600; // Set your desired width
            int targetHeight = 800; // Set your desired height

            // Loop through each page of the PDF and add scaled images to the panel
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                BufferedImage originalImage = pdfRenderer.renderImageWithDPI(pageIndex, 300);
                Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                JLabel pageLabel = new JLabel(scaledIcon);
                previewPanel.add(pageLabel);
            }

            // Create a JScrollPane to enable scrolling
            JScrollPane scrollPane = new JScrollPane(previewPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            // Create a JFrame for displaying the preview
            JFrame previewFrame = new JFrame();
            previewFrame.setTitle("PDF Preview");
            previewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            previewFrame.getContentPane().add(scrollPane); // Add the scroll pane to the preview frame
            previewFrame.pack();
            previewFrame.setLocationRelativeTo(null);
            previewFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a PDF file to preview.", "No PDF Selected", JOptionPane.WARNING_MESSAGE);
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error loading PDF: " + ex.getMessage(), "PDF Loading Error", JOptionPane.ERROR_MESSAGE);
    }
}

        // Implement your PDF preview functionality here
        // You'll need to create a custom PDF viewer panel
    

    // Add an action event to your print button
    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        
         if (selectedPdfFilePath != null) {
        try {
            PDDocument document = PDDocument.load(new File(selectedPdfFilePath));
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            if (job.printDialog()) {
                job.print();
            }

            document.close();
        } catch (PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Printing error: " + ex.getMessage(), "Printing Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a PDF file to print.", "No PDF Selected", JOptionPane.WARNING_MESSAGE);
    }

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print data");
        job.setPrintable(new Printable(){
            
            public int print(Graphics pg,PageFormat pf, int pageNum){
                    pf.setOrientation(PageFormat.LANDSCAPE);
                 if(pageNum>0){
                    return Printable.NO_SUCH_PAGE;
                }
                
                Graphics2D g2 = (Graphics2D)pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(0.24,0.24);
                
                
//          
               
                return Printable.PAGE_EXISTS;
                         
                
            }
        });
        boolean ok = job.printDialog();
        if(ok){
        try{
            
        job.print();
        }
        catch (PrinterException ex){}
        }
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
                try {
                    printButtonActionPerformed(e);
                } catch (IOException ex) {
                    Logger.getLogger(MainSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
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

package com.raven.main;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class PdfLoader {

    public static void loadAndPrintPDF(String pdfFilePath) throws IOException, PrinterException {
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            if (job.printDialog()) {
                job.print();
            }
        } catch (IOException | PrinterException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Printing error: " + ex.getMessage(), "Printing Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        String pdfFilePath = "path/to/your.pdf";

        try {
            loadAndPrintPDF(pdfFilePath);
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        }
    }
}

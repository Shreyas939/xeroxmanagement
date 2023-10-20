package com.raven.main;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class PdfPrintable implements Printable {

    private byte[] file;

    public PdfPrintable(String pdfFilePath) {
        try {
        this.file = Files.readAllBytes(Paths.get(pdfFilePath));
    } catch (IOException ex) {
        Logger.getLogger(PdfPrintable.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex, byte[] pdfBytes) throws PrinterException, IOException {
    if (pageIndex > 0) {
        return Printable.NO_SUCH_PAGE;
    }

    try {
        PDDocument document = PDDocument.load(file);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.setPrintable(this, pageFormat);

        if (job.printDialog()) {
            job.print();
        }
    } catch (PrinterException ex) {
        JOptionPane.showMessageDialog(null, "Printing error: " + ex.getMessage(), "Printing Error", JOptionPane.ERROR_MESSAGE);
    }

    return Printable.PAGE_EXISTS;
}
}
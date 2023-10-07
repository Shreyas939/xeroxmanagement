package com.raven.main;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.pdfbox.Loader;

public class PdfLoader {

    private static byte[] file;

    public static void loadAndRenderPDF(String pdfFilePath, String imageOutputPath) throws IOException {
        try (PDDocument document = Loader.loadPDF(file);
) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int numPages = document.getNumberOfPages();

            for (int pageIndex = 0; pageIndex < numPages; pageIndex++) {
                PDPage page = document.getPage(pageIndex);
                RenderedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);

                // Save the rendered image as a file (optional)
                File outputFile = new File(imageOutputPath + "page_" + (pageIndex + 1) + ".png");
                ImageIO.write(image, "png", outputFile);
            }
        }
    }

    public static void main(String[] args) {
        String pdfFilePath = "path/to/your.pdf";
        String imageOutputPath = "path/to/output/images/";

        try {
            loadAndRenderPDF(pdfFilePath, imageOutputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
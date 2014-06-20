package com.budthapa.controller;

import com.budthapa.common.CurrentDateTime;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author budthapa
 */
@WebServlet(name = "createPDF", urlPatterns = {"/createpdf"})
public class createPDF extends HttpServlet {

    /**
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            //special font sizes
            //user for this entire page
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, new BaseColor(0, 0, 0));
            Font bodyFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

            //set document response type
            documentResponse(response);

            //generate new document. 
            Document doc = new Document();

            //add properties
            documentProperties(response, doc);

            //open document
            doc.open();

            //create title of report
            Paragraph title = new Paragraph("PDF in Java");

            //get the current date and time form the class
            CurrentDateTime date = new CurrentDateTime();
            
            //add header
            documentHeader(doc, date, title, response);

            //set number of columns and their widths, f is float
            float[] columnWidths = {2f, 3f, 3f, 3f};
            PdfPTable table = new PdfPTable(columnWidths);

            //set width and spacing of table, default width is 100f, f is float
            table.setWidthPercentage(90f);
            table.setSpacingBefore(10f);
            table.setSpacingBefore(10f);

            //set heading of table
            insertCell(table, "User Id", Element.ALIGN_CENTER, 1, titleFont);
            insertCell(table, "UserName", Element.ALIGN_CENTER, 1, titleFont);
            insertCell(table, "Address", Element.ALIGN_CENTER, 1, titleFont);
            insertCell(table, "Email", Element.ALIGN_CENTER, 1, titleFont);

            //add header row to the table, we can set more than one row
            table.setHeaderRows(1);

            //set body part of the table
            insertCell(table, "1", Element.ALIGN_CENTER, 1, bodyFont);
            insertCell(table, "budthapa", Element.ALIGN_CENTER, 1, bodyFont);
            insertCell(table, "Kathmandu", Element.ALIGN_CENTER, 1, bodyFont);
            insertCell(table, "budthapa@gmail.com", Element.ALIGN_CENTER, 1, bodyFont);
            
            //add document footer
            //this footer adds above table in the paragraph
            documentFooter(doc, table);

            //close the document
            doc.close();
        } catch (Exception e) {
            //exception here
        }
    }

    /**
     * Insert New row to the table
     *
     * @param table
     * @param text
     * @param align
     * @param colspan
     * @param font
     */
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Get the Header of document like title of document, company logo, print
     * date etc
     *
     * @param doc
     * @param date
     * @param docTitle
     */
    private void documentHeader(Document doc, CurrentDateTime date, Paragraph docTitle, HttpServletResponse response) {
        try {
            //set document header like company logo, company name etc
            //add new line
            Paragraph line = new Paragraph("\n\n\n\n");
            //add new line
            doc.add(line);

            //document print date
            Paragraph printDate = new Paragraph("Print Date " + date.getCurrentDateTime());
            doc.add(printDate);

            //create a paragraph with line
            Paragraph line1 = new Paragraph("\n");
            //add new line
            doc.add(line1);

            //add image to pdf file, this image is stored in c:/apache/apachefondation/bin
            //use your own image
            Image image = Image.getInstance("sajilobooks.png");

            //set position of image
            image.setAbsolutePosition(50f, 770f); //coordinates starts at bottom left,
            //set size of image
            image.scaleAbsolute(50f, 50f);
            //add image to document
            doc.add(image);

            //add document title
            doc.add(docTitle);

        } catch (BadElementException | IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set Document response type
     *
     * @param response
     */
    private void documentResponse(HttpServletResponse response) {

        //this will send the pdf to the browser where user will be able to download or print the report
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");

    }

    /**
     * set output stream, in this case output to browser set the document
     * properties like author, createdate, website, page size etc
     *
     * @param response
     * @param doc
     */
    private void documentProperties(HttpServletResponse response, Document doc) {
        try {

            // create a pdf file with name and set the out put stream
            // OutputStream file = new FileOutputStream(new File("G:/activeUsers" + dtime + ".pdf"));
            // this will save the pdf to the disk
            // OutputStream file = new FileOutputStream(new File("G:/salesMade" + dtime + ".pdf"));
            // send outout to browser
            PdfWriter.getInstance(doc, response.getOutputStream());

            //document header attributes, can view file->properties
            doc.addAuthor("budthapa");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("budthapa@gmail.com");
            doc.addTitle("Client Report");
            doc.setPageSize(PageSize.A4);

        } catch (IOException | DocumentException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Footer of the document Adds table, new line Adds company web address
     */
    private void documentFooter(Document doc, PdfPTable table) {
        try {
            //add the PDF table to the paragraph
            Paragraph paragraph = new Paragraph();
            paragraph.add(table);

            // add the paragraph to the document
            doc.add(paragraph); //this paragraph contains table

            Paragraph line = new Paragraph("\n\n");
            doc.add(line);

            //insert anchor tag in pdf
            Paragraph anchorPara = new Paragraph();
            anchorPara.add(new Phrase("our website : "));
            Anchor anchor = new Anchor("http://yourwebsite.com");
            anchor.setReference("http://yourwebsite.com");

            anchorPara.add(anchor);
            doc.add(anchorPara);

        } catch (DocumentException ex) {
            Logger.getLogger(ReportController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commons;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Darre
 */
public class PdfDocumento {

    public PdfDocumento() {
        document = new PDDocument();
        page = new PDPage(PDRectangle.A6);//Ancho de pagina: 297.63782, Altura de pagina: 419.52756
    }

    protected PDPageContentStream contentStream;
    protected PDDocument document;
    protected PDPage page;

    /**
     * *
     * Agrega un texto en el PDF
     *
     * @param texto
     * @param X
     * @param Y - Posición iniciando de la esquina inferior izquierda
     * @param size
     * @param pdfFont
     * @throws IOException
     */
    protected void agregarTexto(String texto, float X, float Y, int size, PDFont pdfFont) throws IOException {
        contentStream.beginText();
        contentStream.setFont(pdfFont, size);
        contentStream.newLineAtOffset(X, Y);
        contentStream.showText(texto);
        contentStream.endText();
    }

    public void Multiline(String text, float X, float Y, PDPage page, PDFont pdfFont, float fontSize) throws IOException {
        PDRectangle mediabox = page.getMediaBox();
        float margin = 10;
        float width = mediabox.getWidth() - 2 * margin;
        //X = mediabox.getLowerLeftX() + margin;
        Y = mediabox.getUpperRightY() - Y;

        float leading = 1.5f * fontSize;
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                spaceIndex = text.length();
            }
            String subString = text.substring(0, spaceIndex);
            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
            //System.out.printf("'%s' - %f of %f\n", subString, size, width);
            if (size > width) {
                if (lastSpace < 0) {
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                //System.out.printf("'%s' is line\n", subString);
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                //System.out.printf("'%s' is line\n", text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        contentStream.beginText();
        contentStream.setFont(pdfFont, fontSize);
        contentStream.newLineAtOffset(X, Y);
        for (String line : lines) {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -leading);
        }
        contentStream.endText();
    }

    public String seleccionarArchivoPDF(String Nombre_sugerido) {
        try {
            JFileChooser selector = new JFileChooser();
            int respuesta;

            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Contratos", "PDF");
            selector.setFileFilter(filtro);

            selector.setSelectedFile(new File(Nombre_sugerido));
            respuesta = selector.showSaveDialog(null);
            if (respuesta == JFileChooser.APPROVE_OPTION) {
                String ruta = selector.getSelectedFile().getPath();
                if (!ruta.contains(".pdf")) {
                    ruta = ruta + ".pdf";
                }
                return ruta;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return null;
    }

    public void agregarImagen(String ruta, float X, float Y, float ancho, float alto) throws IOException {
        PDImageXObject imagen = PDImageXObject.createFromFile(ruta, document);
        contentStream.drawImage(imagen, X, Y, ancho, alto);
    }
    
    /***
     * Abrir el archivo en un visor PDF del S.O.
     * @param ruta 
     */
    public void mostrarPDF(String ruta){
        try {
            File rutaMostrar = new File(ruta);
            Desktop.getDesktop().open(rutaMostrar);
        } catch (IOException ex) {
            Logger.getLogger(PdfDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/***
 * Genera una tabla para el contenido de una lista de cadenas
 * @param y
 * @param margin
 * @param content
 * @param page
 * @throws IOException 
 */
    public void drawTable(float y, float margin, List<String[]> content,PDPage page) throws IOException {
        final int rows = content.size();
        final int cols = content.get(0).length;
        final float rowHeight = 7f;
        final float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
        final float tableHeight = rowHeight * (float) rows;
        final float colWidth = tableWidth / (float) cols;

        //draw the rows
        float nexty = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty -= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 4f);

        final float cellMargin = 1f;
        float textx = margin + cellMargin;
        float texty = y - 4f;
        for (final String[] aContent : content) {
            for (String text : aContent) {
                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }
    }
}

package nl.saxion.webimageprinter;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfImageWriter {

  private final List<WebImage> images;

  public PdfImageWriter(List<WebImage> images) {
    this.images = images;
  }

  public void printToPdf(String file) {
    Document document = new Document();
    try {
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
      document.open();
      PdfContentByte pdfCB = new PdfContentByte(writer);
      for (WebImage image : images) {
        try {
          //Image img = Image.getInstance(pdfCB, image.getImage(10), 1);
          Image img = Image.getInstance(pdfCB, image.getImage(100), 1);
          document.add(img);
          document.add(new Paragraph(image.getName()));
        } catch (IOException e) {
          e.printStackTrace();
        } catch (BadElementException e) {
          e.printStackTrace();
        } catch (DocumentException e) {
          e.printStackTrace();
        }
      }
      document.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    }
  }

}

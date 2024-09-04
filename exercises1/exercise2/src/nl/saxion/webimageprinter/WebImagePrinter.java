package nl.saxion.webimageprinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WebImagePrinter {
  public static void main(String[] args) {
    new WebImagePrinter().print("http://concurrency.anthonyvandenberg.nl/");
  }

  /**
   * @param location
   * @return List of location of images
   * <p>
   * Reads a list of images from a web server.
   */
  private List<String> getImageList(String location) {
    List<String> images = new ArrayList<>();
    try {
      URL url = new URL(location);
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

      String image;
      while ((image = in.readLine()) != null) {
        images.add(image);
      }
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return images;
  }

  /**
   * @param location Todo: getting the images sequentially takes a lot of time.
   *                 Change the code so that each image is loaded in its own thread
   */
  public void print(String location) {

    List<String> images = getImageList(location);
    List<WebImage> webImages = new ArrayList<>();
    for (String image : images) {
      try {
        Instant start = Instant.now();
        System.out.println("Loading " + image);
        webImages.add(new WebImage(location + "/" + image));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    PdfImageWriter pdfwriter = new PdfImageWriter(webImages);
    pdfwriter.printToPdf("test.pdf");
  }

}

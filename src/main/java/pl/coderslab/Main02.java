package pl.coderslab;

import pl.coderslab.entity.HistDao;
import pl.coderslab.entity.HistImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main02 {

  static HistDao histDao = new HistDao();

  public static void main(String[] args) {
    // test
    // testCreateImage(IMAGE_FILE_PATH);
  }

  private static final String IMAGE_FILE_PATH = "lenna.png";
  // try to use DB for creating histogram
  private static void testCreateImage(String pathToFile) {
    File fileData = new File(pathToFile);
    Path file = Paths.get(pathToFile);
    try {
      if (Files.notExists(file)) {
        System.out.printf("Sorry, but this file %s not exist%n", pathToFile);
        System.exit(1);
      }
      BufferedImage img = ImageIO.read(fileData);
      String imageName = pathToFile.split("\\.")[0];
      int imageWidth = img.getWidth();
      int imageHeight = img.getHeight();
      // create new instance of image
      HistImage histImage = new HistImage(imageName, imageWidth, imageHeight);

      // this is only for checking
      String histImageInfo;

      histImageInfo = histImage.toString();
      System.out.println(histImageInfo);

      // create entity in DB
      histDao.createImage(histImage);

      // check updated information
      histImageInfo = histImage.toString();
      System.out.println(histImageInfo);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

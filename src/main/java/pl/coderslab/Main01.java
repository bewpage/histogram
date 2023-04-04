package pl.coderslab;

import pl.coderslab.entity.HistColor;
import pl.coderslab.entity.HistDao;
import pl.coderslab.entity.HistImage;
import pl.coderslab.entity.HistImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main01 {

  public static void main(String[] args) {
    // test
    // createHistogramForPicture(IMAGE_FILE_PATH);

    testPrintAllImages();
  }

  static HistDao histDao = new HistDao();
  protected static int MAX = 50;
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

  private static void createHistogramForPicture(String pathToFile) {
    File file = new File(pathToFile);
    try {
      BufferedImage img = ImageIO.read(file);

      String imageName = pathToFile.split("\\.")[0];
      int imageWidth = img.getWidth();
      int imageHeight = img.getHeight();

      // create new instance of image
      HistImage histImage = new HistImage(imageName, imageWidth, imageHeight);
      // create entity in DB
      // and image ID should be set
      histDao.createImage(histImage);

      int redMax = 0;
      HistColor histColorRed = new HistColor(histImage.getId(), "red");
      // check image data
      System.out.println(histImage.toString());

      // temp data structure for calculations
      int[] redArray = new int[256];
      int[] tmpRedArray = new int[256];

      int greenMax = 0;
      int[] greenArray = new int[256];
      int[] tmpGreenArray = new int[256];

      int blueMax = 0;
      int[] blueArray = new int[256];
      int[] tmpBlueArray = new int[256];

      for (int x = 0; x < imageWidth; x++) {
        for (int y = 0; y < imageHeight; y++) {
          int pixel = img.getRGB(x, y);
          Color color = new Color(pixel);
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();

          // count histogram for red value
          tmpRedArray[red] = tmpRedArray[red] + 1;
          redMax = currentMax(tmpRedArray[red], redMax);
          redArray[red] = (tmpRedArray[red] * MAX) / redMax;
          // and add values to color object
          histColorRed.histColorValuesArray[red].setNumberOfPixels(redArray[red]);

          tmpGreenArray[green] = tmpGreenArray[green] + 1;
          greenMax = currentMax(tmpGreenArray[green], greenMax);
          greenArray[red] = (tmpGreenArray[green] * MAX) / greenMax;

          tmpBlueArray[blue] = tmpBlueArray[blue] + 1;
          blueMax = currentMax(tmpBlueArray[blue], blueMax);
          blueArray[blue] = (tmpBlueArray[blue] * MAX) / blueMax;
        }
      }

      System.out.println("red max " + redMax);
      System.out.println("green max " + greenMax);
      System.out.println("blue max " + blueMax);

      histColorRed.printHistogram();

      // add values to DB for color RED
      histDao.createHistColor(histColorRed);

      // printHistogram(redArray, "Red");

      // printHistogram(greenArray, "Green");

      // printHistogram(blueArray, "Blue");

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void testPrintAllImages() {
    HistImages allImages = new HistImages();

    histDao.findAllImages(allImages);

    System.out.println(allImages.toString());
  }

  private static int currentMax(int rgbValue, int currentMax) {
    if (rgbValue > currentMax) {
      currentMax = rgbValue;
    }
    return currentMax;
  }

  private static void printHistogram(int[] colorValues, String colorName) {
    StringBuilder line = new StringBuilder();
    System.out.println("Histogram for color: " + colorName);
    for (int i = 0; i < colorValues.length; i++) {
      for (int j = 0; j < colorValues[i]; j++) {
        line.append("*");
      }
      System.out.println(i + ": " + line);
      line = new StringBuilder();
    }
  }
}

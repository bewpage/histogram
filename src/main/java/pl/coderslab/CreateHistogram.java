package pl.coderslab;

import pl.coderslab.entity.HistColor;
import pl.coderslab.entity.HistDao;
import pl.coderslab.entity.HistImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateHistogram {

  public static void main(String[] args) {
    /* test for creating histogram for file and save image data in DB */
    createHistogramForPicture(IMAGE_FILE_PATH);
  }

  static HistDao histDao = new HistDao();
  protected static int MAX = 50;
  /* hardcoded file name
  * please chang it to correct one
  * */
  private static final String IMAGE_FILE_PATH = "lenna.png";

  private static void createHistogramForPicture(String pathToFile) {
    File file = new File(pathToFile);
    try {
      BufferedImage img = ImageIO.read(file);

      /* Creating image instance and add image information to DB */
      HistImage histImage = createImage(pathToFile, img);

      /* Here START checking information for histogram*/
      /* Creating color histogram instance fo RED, GREEN and BLUE value */
      HistColor histColorRed = new HistColor(histImage.getId(), "red");
      HistColor histColorGreen = new HistColor(histImage.getId(), "green");
      HistColor histColorBlue = new HistColor(histImage.getId(), "blue");

      /* temp data structure for calculations to have all in one iteration
       * space complexity maybe is not the best :(,
       * maybe I will need to check how to keep only one temporary array plus histogram object
       * */
      int redMax = 0;
      int[] redArray = new int[256];
      int[] tmpRedArray = new int[256];

      int greenMax = 0;
      int[] greenArray = new int[256];
      int[] tmpGreenArray = new int[256];

      int blueMax = 0;
      int[] blueArray = new int[256];
      int[] tmpBlueArray = new int[256];

      for (int x = 0; x < img.getWidth(); x++) {
        for (int y = 0; y < img.getHeight(); y++) {
          int pixel = img.getRGB(x, y);
          Color color = new Color(pixel);
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();

          // count histogram for red value
          tmpRedArray[red] = tmpRedArray[red] + 1;
          redMax = currentMax(tmpRedArray[red], redMax);
          redArray[red] = (tmpRedArray[red] * MAX) / redMax;
          // and add final histogram values to color object for RED
          histColorRed.histColorValuesArray[red].setNumberOfPixels(redArray[red]);

          tmpGreenArray[green] = tmpGreenArray[green] + 1;
          greenMax = currentMax(tmpGreenArray[green], greenMax);
          greenArray[green] = (tmpGreenArray[green] * MAX) / greenMax;
          // and add final histogram values to color object for GREEN
          histColorGreen.histColorValuesArray[green].setNumberOfPixels(greenArray[green]);

          tmpBlueArray[blue] = tmpBlueArray[blue] + 1;
          blueMax = currentMax(tmpBlueArray[blue], blueMax);
          blueArray[blue] = (tmpBlueArray[blue] * MAX) / blueMax;
          // and add final histogram values to color object for BLUE
          histColorBlue.histColorValuesArray[blue].setNumberOfPixels(blueArray[blue]);
        }
      }

      System.out.println();

      // print out information for RED
      histColorRed.printHistogram();
      // and add values to DB for color RED
      histDao.createHistColor(histColorRed);

      // GREEN
      histColorGreen.printHistogram();
      histDao.createHistColor(histColorGreen);

      // BLUE
      histColorBlue.printHistogram();
      histDao.createHistColor(histColorBlue);

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private static int currentMax(int rgbValue, int currentMax) {
    if (rgbValue > currentMax) {
      currentMax = rgbValue;
    }
    return currentMax;
  }

  private static boolean checkIfFileExist(String filename) {
    boolean isFileExist = false;
    Path file = Paths.get(filename);
    if (Files.exists(file)) {
      isFileExist = true;
    }
    return isFileExist;
  }

  private static HistImage createImage(String pathToFile, BufferedImage img) {
    File fileData = new File(pathToFile);
    if (!checkIfFileExist(pathToFile)) {
      System.out.println("Sorry, but file not exist or path is wrong");
      System.exit(1);
    }
    // --- info about image file
    String imageName = pathToFile.split("\\.")[0];
    // create new instance of image
    HistImage histImage = new HistImage(imageName, img.getWidth(), img.getHeight());
    // create entity in DB
    // and image ID should be set
    histDao.createImage(histImage);

    return histImage;
  }
}

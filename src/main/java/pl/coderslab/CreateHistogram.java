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
  protected static int COLOR_LENGTH = 256;
  protected static int MAX = 50;
  /* hardcoded file name please change it to correct one */
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
      int[] redArray = new int[COLOR_LENGTH];

      int greenMax = 0;
      int[] greenArray = new int[COLOR_LENGTH];

      int blueMax = 0;
      int[] blueArray = new int[COLOR_LENGTH];

      for (int x = 0; x < img.getWidth(); x++) {
        for (int y = 0; y < img.getHeight(); y++) {
          int pixel = img.getRGB(x, y);
          Color color = new Color(pixel);
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();

          // count histogram for red value
          redArray[red] = redArray[red] + 1;
          redMax = currentMax(redArray[red], redMax);

          greenArray[green] = greenArray[green] + 1;
          greenMax = currentMax(greenArray[green], greenMax);

          blueArray[blue] = blueArray[blue] + 1;
          blueMax = currentMax(blueArray[blue], blueMax);
        }
      }

      for (int i = 0; i < COLOR_LENGTH; i++) {
        // count histogram value for RED
        int tmpRed = (redArray[i] * MAX) / redMax;
        redArray[i] = tmpRed;
        // and add final histogram values to color object for RED
        histColorRed.histColorValuesArray[i].setNumberOfPixels(redArray[i]);

        int tmpGreen = (greenArray[i] * MAX) / greenMax;
        greenArray[i] = tmpGreen;
        // and add final histogram values to color object for GREEN
        histColorGreen.histColorValuesArray[i].setNumberOfPixels(greenArray[i]);

        int tmpBlue = (blueArray[i] * MAX) / blueMax;
        blueArray[i] = tmpBlue;
        // and add final histogram values to color object for BLUE
        histColorBlue.histColorValuesArray[i].setNumberOfPixels(blueArray[i]);
      }

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

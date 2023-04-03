package pl.coderslab;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main01 {
  public static void main(String[] args) {
    // test
    test(IMAGE_FILE_PATH);
  }

  protected static int MAX = 50;

  private static final String IMAGE_FILE_PATH = "lenna.png";

  private static void test(String pathToFile) {
    File file = new File(pathToFile);
    try {
      BufferedImage img = ImageIO.read(file);

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

      printHistogram(redArray, "Red");

      printHistogram(greenArray, "Green");

      printHistogram(blueArray, "Blue");

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  // this is working somehow
  private static int calculateHistogram(int rgbValue, int[] array) {
    array[rgbValue] = array[rgbValue] + 1;
    return array[rgbValue];
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

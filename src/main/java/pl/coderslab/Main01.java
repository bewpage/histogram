package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

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

      int maxRed = 0;
      int[] redArray = new int[256];
      int[] greenArray = new int[256];
      int[] blueArray = new int[256];

      for (int x = 0; x < img.getWidth(); x++) {
        for (int y = 0; y < img.getHeight(); y++) {
          int pixel = img.getRGB(x, y);
          Color color = new Color(pixel);
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();
          // count histogram for red value
          redArray[red] = redArray[red] + 1;
          greenArray[green] = greenArray[green] + 1;
          blueArray[blue] = blueArray[blue] + 1;
        }
      }

      int[] redHistogram = createColorHistogram(redArray);
      printHistogram(redHistogram, "Red");

      int[] greenHistogram = createColorHistogram(greenArray);
      printHistogram(greenHistogram, "Green");

      int[] blueHistogram = createColorHistogram(blueArray);
      printHistogram(blueHistogram, "Blue");

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private static int[] createColorHistogram(int[] colorArray) {
    int maxValueForColor = Collections.max(Arrays.asList(ArrayUtils.toObject(colorArray)));
    int[] tmp = new int[256];

    for (int i = 0; i < colorArray.length; i++) {
      if (colorArray[i] != 0) {
        tmp[i] = (colorArray[i] * MAX) / maxValueForColor;
      }
    }
    return tmp;
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

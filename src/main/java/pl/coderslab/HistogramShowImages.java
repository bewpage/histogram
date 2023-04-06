package pl.coderslab;

import pl.coderslab.entity.HistDao;
import pl.coderslab.entity.HistImages;

public class HistogramShowImages {

  public static void main(String[] args) {
    /* here is method for get all images and print out in console */
    showAllImages();
  }

  static HistDao histDao = new HistDao();

  private static void showAllImages() {
    HistImages allImages = histDao.findAllImages();

    System.out.println(allImages.toString());
  }
}

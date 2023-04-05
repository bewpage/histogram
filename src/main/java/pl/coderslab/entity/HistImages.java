package pl.coderslab.entity;

import java.util.Arrays;

public class HistImages {

  HistImage[] histImages;

  public HistImages() {
    this.histImages = new HistImage[0];
  }

  public HistImage[] getHistImages() {
    return histImages;
  }

  public void setHistImages(HistImage[] histImages) {
    this.histImages = histImages;
  }

  public void addImages(HistImage img) {
    HistImage[] tmp = Arrays.copyOf(this.histImages, this.histImages.length + 1);
    tmp[this.histImages.length] = img;
    this.setHistImages(tmp);
  }

  @Override
  public String toString() {
    StringBuilder line = new StringBuilder();
    String result = "";
    if (this.histImages.length == 0) {
      System.out.println("Sorry, but images value are empty");
      result = "{}";
    } else {
      for (HistImage histImage : this.histImages) {
        long id = histImage.getId();
        String name = histImage.getName();
        int width = histImage.getWidth();
        int height = histImage.getHeight();
        String msg =
            String.format(
                "{ id: %s, name: %s, width: %s, height: %s } \n", id, name, width, height);
        line.append(msg);
      }
      result = line.toString();
    }

    return result;
  }
}

package pl.coderslab.entity;

public class HistColor {

  // image_id
  private long imageID;
  // color one of RGB
  private String color;

  public HistColorValue[] histColorValuesArray;

  public HistColor(long imageID, String color) {
    this.imageID = imageID;
    this.color = color;
    this.histColorValuesArray = new HistColorValue[256];
    this.createIntensity();
  }

  public long getImageID() {
    return imageID;
  }

  public void setImageID(long imageID) {
    this.imageID = imageID;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public HistColorValue[] getHistColorValuesArray() {
    return histColorValuesArray;
  }

  public void setHistColorValuesArray(HistColorValue[] histColorValuesArray) {
    this.histColorValuesArray = histColorValuesArray;
  }

  public void setHistColorValuesArrayTest(int index, HistColorValue histColorValue) {
    this.histColorValuesArray[index] = histColorValue;
  }

  public void printHistogram() {
    if (this.histColorValuesArray[0] == null) {
      System.out.println("Sorry, but colors value are empty");
      return;
    }
    StringBuilder line = new StringBuilder();
    System.out.println("Histogram for color: " + this.color);
    for (int i = 0; i < this.histColorValuesArray.length; i++) {
      for (int j = 0; j < this.histColorValuesArray[i].getNumberOfPixels(); j++) {
        line.append("*");
      }
      System.out.println(i + ": " + line);
      line = new StringBuilder();
    }
  }

  private void createIntensity() {
    for (int i = 0; i < this.histColorValuesArray.length; i++) {
      HistColorValue histColorValue = new HistColorValue(i, 0);
      this.histColorValuesArray[i] = histColorValue;
    }
  }
}

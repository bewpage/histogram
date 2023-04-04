package pl.coderslab.entity;

public class HistColorValue {
  // intensity - kolor level 0 - 255
  private int intensity;
  // number_of_pixels how many pixels
  private long numberOfPixels;

  public HistColorValue(int intensity, long numberOfPixels) {
    this.intensity = intensity;
    this.numberOfPixels = numberOfPixels;
  }

  public HistColorValue(){
    this.intensity = 0;
    this.numberOfPixels = 0;
  }

  public int getIntensity() {
    return intensity;
  }

  public void setIntensity(int intensity) {
    this.intensity = intensity;
  }

  public long getNumberOfPixels() {
    return numberOfPixels;
  }

  public void setNumberOfPixels(long numberOfPixels) {
    this.numberOfPixels = numberOfPixels;
  }
}

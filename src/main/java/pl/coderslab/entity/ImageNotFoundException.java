package pl.coderslab.entity;

public class ImageNotFoundException extends RuntimeException {
  private static final String MSG = "Image for given name %s not found";

  public ImageNotFoundException(String imageName) {
    super(String.format(MSG, imageName));
  }
}

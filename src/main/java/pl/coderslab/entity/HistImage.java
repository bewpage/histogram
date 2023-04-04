package pl.coderslab.entity;

public class HistImage {
  private long id;
  private String name;
  private int width;
  private int height;

  public HistImage(long id, String name, int width, int height) {
    this.id = id;
    this.name = name;
    this.width = width;
    this.height = height;
  }

  public HistImage(String name, int width, int height) {
    this.name = name;
    this.width = width;
    this.height = height;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public String toString() {
    return "HistImage{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", width="
        + width
        + ", height="
        + height
        + '}';
  }
}

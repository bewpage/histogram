package pl.coderslab.entity;

import pl.coderslab.DbUtils;

import java.sql.*;

public class HistDao {

  enum ImageColumn {
    ID,
    NAME,
    WIDTH,
    HEIGHT
  }

  enum ColorsColumn {
    IMAGE_ID,
    COLOR,
    INTENSITY,
    NUMBER_OF_PIXELS
  }

  // CREATE --- hist image record
  // push to DB info about image => hist_images
  private static final String CREATE_IMAGE_QUERY =
      "INSERT INTO hist_images(name, width, height) VALUES(?, ?, ?)";

  public HistImage createImage(HistImage image) {
    try (Connection conn = DbUtils.connectHistDB();
        PreparedStatement stmt =
            conn.prepareStatement(CREATE_IMAGE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, image.getName());
      stmt.setInt(2, image.getWidth());
      stmt.setInt(3, image.getHeight());
      stmt.executeUpdate();

      try (ResultSet resultSet = stmt.getGeneratedKeys()) {
        if (resultSet.next()) {
          long generatedID = resultSet.getLong(1);
          image.setId(generatedID);
          return image;
        } else {
          throw new RuntimeException("Filed to create user");
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // CREATE --- hist color records
  // push to DB info about RGB values => hist_colors
  // add object to DB
  private static final String CREATE_COLOR_QUERY =
      "INSERT INTO hist_colors(image_id, color, intensity, number_of_pixels) VALUES(?, ?, ?, ?)";

  public void createHistColor(HistColor histColor) {
    for (int i = 0; i < histColor.histColorValuesArray.length; i++) {
      // need to be sure we have correct image ID
      try (Connection conn = DbUtils.connectHistDB();
          PreparedStatement stmt = conn.prepareStatement(CREATE_COLOR_QUERY)) {
        stmt.setLong(1, histColor.getImageID());
        stmt.setString(2, histColor.getColor());
        stmt.setInt(3, histColor.histColorValuesArray[i].getIntensity());
        stmt.setLong(4, histColor.histColorValuesArray[i].getNumberOfPixels());
        stmt.executeUpdate();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  // READ

  private static final String FIND_IMAGE_QUERY = "SELECT * FROM hist_images WHERE name = ?";

  public HistImage readImage(String imageName) {
    try (Connection conn = DbUtils.connectHistDB();
        PreparedStatement stmt = conn.prepareStatement(FIND_IMAGE_QUERY)) {
      stmt.setString(1, imageName);
      try (ResultSet resultSet = stmt.executeQuery()) {
        if ((resultSet.next())) {
          return createImg(resultSet);
        } else {
          throw new ImageNotFoundException(imageName);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static HistImage createImg(ResultSet resultSet) throws SQLException {
    long imageID = resultSet.getLong(ImageColumn.ID.name());
    String imageName = resultSet.getString(ImageColumn.NAME.name());
    int imageWidth = resultSet.getInt(ImageColumn.WIDTH.name());
    int imageHeight = resultSet.getInt(ImageColumn.HEIGHT.name());
    return new HistImage(imageID, imageName, imageWidth, imageHeight);
  }

  // UPDATE

  // DELETE
  private static final String DELETE_IMAGE_QUERY = "DELETE FROM hist_images WHERE id = ?";

  public void deleteImage(long imageID) {
    try (Connection conn = DbUtils.connectHistDB();
        PreparedStatement stmt = conn.prepareStatement(DELETE_IMAGE_QUERY)) {
      stmt.setLong(1, imageID);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // ---- FIND ALL IMAGES ----
  private static final String FIND_ALL_IMAGES_QUERY = "SELECT * FROM hist_images";

  public HistImages findAllImages() {
    try (Connection conn = DbUtils.connectHistDB();
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(FIND_ALL_IMAGES_QUERY)) {
      HistImages currentHistImages = new HistImages();
      while (resultSet.next()) {
        HistImage createImage = createImg(resultSet);
        currentHistImages.addImages(createImage);
      }
      return currentHistImages;
    } catch (SQLException | ImageNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}

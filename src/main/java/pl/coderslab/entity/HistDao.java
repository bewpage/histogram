package pl.coderslab.entity;

import pl.coderslab.DbUtils;

import java.sql.*;

public class HistDao {

  // CREATE --- hist image record

  // push to DB info about RGB values => hist_colors
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

  // add object to DB
  private static final String CREATE_COLOR_QUERY =
      "INSERT INTO hist_colors(image_id, color, intensity, number_of_pixels) VALUES(?, ?, ?, ?)";

  public void createHistColor(HistColor histColor) {
    for(int i = 0; i < histColor.histColorValuesArray.length; i++){
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
}

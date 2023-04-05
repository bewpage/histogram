# Histogram

## Overview

RGB Histogram is a simple program written in Java that generates RGB channel histograms for photos.
These histograms show the distribution of pixel values for each channel (red, green, blue) in the selected photo,
which can be useful for image analysis, photo editing.
The program uses a MySQL database to store information about photos and their histograms.

## Requirements

- Java (JDK),
- MySQL Server,
- MySQL Connector/J (JDBC driver for MySQL)

## Configuration

- Create a database:
    - in `db` folder you will find `schemas.sql` file for DB and tables,
- Source image file:
    - you will need to specify path to image file in `CreateHistogram` class,
  ```java
    private static final String IMAGE_FILE_PATH = <PATH_TO_IMAGE_FILE>;
    ```
    - [Test photo](https://en.wikipedia.org/wiki/Lenna)
      you can [download from here ](https://upload.wikimedia.org/wikipedia/en/7/7d/Lenna_%28test_image%29.png?download).
- Graph height is set in class `CreateHistogram`:

```java
 protected static int MAX=50;
```

## Run the example

The running program will load the selected photo, calculate histograms for the RGB channels,
and then save this information in a MySQL database or print out histogram in console.
You can also view previously saved histograms and delete them from the database.

To run some examples:

- to see console print histogram:
    - run this method `createHistogramForPicture` in class `CreateHistogram`,
- to see list of all images in DB:
    - run this method `showAllImages` in class `HistogramShowImages`,

Have fun!


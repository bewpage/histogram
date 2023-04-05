CREATE DATABASE IF NOT EXISTS hist_ex
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hist_ex;

--
-- Table structure for table `hist_colors`
--

CREATE TABLE hist_colors
(
    image_id         int              NOT NULL,
    color            varchar(5)       NOT NULL,
    intensity        tinyint unsigned NOT NULL,
    number_of_pixels tinyint unsigned DEFAULT NULL,
    PRIMARY KEY (`image_id`),
    CONSTRAINT `fk_hist_image_id` FOREIGN KEY (image_id) REFERENCES `hist_images` (id) ON DELETE CASCADE,
    CONSTRAINT `hist_colors_chk_1` CHECK (color in ('red', 'green', 'blue'))
);

--
-- Table structure for table `hist_images`
--

CREATE TABLE hist_images
(
    id     int          NOT NULL AUTO_INCREMENT,
    name   varchar(100) NOT NULL,
    width  smallint     NOT NULL,
    height smallint     NOT NULL,
    PRIMARY KEY (id)
);
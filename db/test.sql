SELECT *
FROM hist_colors;

ALTER TABLE hist_colors
    DROP FOREIGN KEY fk_hist_image_id;

ALTER TABLE hist_colors
    ADD CONSTRAINT `fk_hist_image_id` FOREIGN KEY (image_id) REFERENCES hist_images (id) ON DELETE CASCADE;


SELECT *
FROM hist_images;

SELECT * FROM hist_colors WHERE color = 'red' AND number_of_pixels BETWEEN 20 AND 50;
SELECT * FROM hist_colors WHERE color = 'green' AND number_of_pixels BETWEEN 20 AND 50;
SELECT * FROM hist_colors WHERE color = 'blue' AND number_of_pixels BETWEEN 20 AND 50;
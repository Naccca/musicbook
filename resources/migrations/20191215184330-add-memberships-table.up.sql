CREATE TABLE memberships (
  band_id INT NOT NULL,
  artist_id INT NOT NULL,
  state_id INT NOT NULL DEFAULT 1,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (band_id) REFERENCES bands (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (artist_id) REFERENCES artists (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (band_id, artist_id));

INSERT INTO memberships (band_id, artist_id, state_id, created_at, updated_at)
VALUES (1, 1, 2, NOW(), NOW()),
       (1, 2, 2, NOW(), NOW()),
       (1, 3, 2, NOW(), NOW()),
       (1, 4, 2, NOW(), NOW()),
       (2, 5, 2, NOW(), NOW()),
       (2, 6, 2, NOW(), NOW()),
       (2, 7, 2, NOW(), NOW()),
       (2, 8, 2, NOW(), NOW()),
       (3, 9, 2, NOW(), NOW()),
       (3, 10, 2, NOW(), NOW()),
       (3, 11, 2, NOW(), NOW()),
       (4, 12, 2, NOW(), NOW()),
       (4, 13, 2, NOW(), NOW());
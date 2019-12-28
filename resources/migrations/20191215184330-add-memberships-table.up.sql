CREATE TABLE memberships (
  band_id INT NOT NULL,
  artist_id INT NOT NULL,
  state_id INT NOT NULL DEFAULT 1,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (band_id) REFERENCES bands (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (artist_id) REFERENCES artists (id) ON DELETE CASCADE ON UPDATE CASCADE,
  PRIMARY KEY (band_id, artist_id));

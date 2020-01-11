CREATE TABLE bands
(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(255) NOT NULL,
 bio VARCHAR,
 location VARCHAR(255),
 genres VARCHAR(255),
 created_at TIMESTAMP,
 updated_at TIMESTAMP,
 owner_id INT NOT NULL,
 FOREIGN KEY (owner_id) REFERENCES artists (id) ON DELETE CASCADE ON UPDATE CASCADE,
 has_image BOOLEAN DEFAULT FALSE);
-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id

----------------------------------

-- :name get-artists :? :*
-- :doc selects all artists
SELECT * FROM artists

-- :name get-artist :? :1
--doc retrieves an artist record given the id
SELECT * FROM artists
WHERE id = :id

-- :name artist? :? :1
-- :doc returns true if artist exists
SELECT true FROM artists
WHERE id = :id

-- :name create-artist! :! :n
-- :doc creates a new artist record
INSERT INTO artists
(username, password_hash, name, bio, location, created_at, updated_at)
VALUES (:username, :password_hash, :name, :bio, :location, :created_at, :updated_at)

-- :name update-artist! :! :n
-- :doc updates an existing artist record
UPDATE artists
SET name = :name, bio = :bio, location = :location, updated_at = :updated_at
WHERE id = :id

-- :name delete-artist! :! :n
-- :doc deletes an artist record given the id
DELETE FROM artists
WHERE id = :id 

------------------------

-- :name get-bands :? :*
-- :doc selects all bands
SELECT * FROM bands

-- :name get-band :? :1
--doc retrieves an band record given the id
SELECT * FROM bands
WHERE id = :id

-- :name create-band! :! :n
-- :doc creates a new band record
INSERT INTO bands
(name, bio, location, created_at, updated_at)
VALUES (:name, :bio, :location, :created_at, :updated_at)

-- :name update-band! :! :n
-- :doc updates an existing band record
UPDATE bands
SET name = :name, bio = :bio, location = :location, updated_at = :updated_at
WHERE id = :id

-- :name delete-band! :! :n
-- :doc deletes an band record given the id
DELETE FROM bands
WHERE id = :id 

---------------------------

-- :name get-artists-by-band-id :? :*
-- :doc selects all artists that are members of the band
SELECT * FROM artists, memberships
WHERE artists.id = memberships.artist_id AND memberships.band_id = :id

-- :name membership? :? :1
-- :doc returns true if membership exists
SELECT true FROM memberships
WHERE band_id = :band_id AND artist_id = :artist_id

-- :name create-membership! :! :n
-- :doc creates a new membership record
INSERT INTO memberships
(band_id, artist_id, created_at, updated_at)
VALUES (:band_id, :artist_id, :created_at, :updated_at)

-- :name delete-membership! :! :n
-- :doc deletes membership
DELETE FROM memberships
WHERE band_id = :band_id AND artist_id = :artist_id 













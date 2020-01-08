-- :name get-artists :? :*
-- :doc selects all artists
SELECT * FROM artists

-- :name search-artists :? :*
-- :doc search artists by name
SELECT * FROM artists
WHERE upper(name) LIKE upper(:name-like)

-- :name get-artist :? :1
-- doc retrieves an artist record given the id
SELECT * FROM artists
WHERE id = :id

-- :name get-artist-by-username :? :1
-- doc retrieve an artist record given the username
SELECT * FROM artists
WHERE username = :username

-- :name get-artist-by-name :? :1
-- doc retrieve an artist record given the name
SELECT * FROM artists
WHERE name = :name

-- :name artist? :? :1
-- :doc returns true if artist exists
SELECT true FROM artists
WHERE id = :id

-- :name create-artist! :! :n
-- :doc creates a new artist record
INSERT INTO artists
(username, password_hash, name, bio, location, instruments, created_at, updated_at)
VALUES (:username, :password_hash, :name, :bio, :location, :instruments, :created_at, :updated_at)

-- :name update-artist! :! :n
-- :doc updates an existing artist record
UPDATE artists
SET name = :name, bio = :bio, location = :location, instruments = :instruments, updated_at = :updated_at
WHERE id = :id

-- :name delete-artist! :! :n
-- :doc deletes an artist record given the id
DELETE FROM artists
WHERE id = :id 

------------------------

-- :name get-bands :? :*
-- :doc selects all bands
SELECT * FROM bands

-- :name search-bands :? :*
-- :doc search bands by name
SELECT * FROM bands
WHERE upper(name) LIKE upper(:name-like)

-- :name get-band :? :1
--doc retrieves an band record given the id
SELECT * FROM bands
WHERE id = :id

-- :name create-band! :! :n
-- :doc creates a new band record
INSERT INTO bands
(name, bio, location, genres, owner_id, created_at, updated_at)
VALUES (:name, :bio, :location, :genres, :owner_id, :created_at, :updated_at)

-- :name update-band! :! :n
-- :doc updates an existing band record
UPDATE bands
SET name = :name, bio = :bio, location = :location, genres = :genres, updated_at = :updated_at
WHERE id = :id

-- :name delete-band! :! :n
-- :doc deletes an band record given the id
DELETE FROM bands
WHERE id = :id 

---------------------------

-- :name get-artists-by-band-id :? :*
-- :doc selects all artists that are members of the band
SELECT * FROM artists, memberships
WHERE artists.id = memberships.artist_id AND memberships.band_id = :id AND state_id = :state_id

-- :name get-bands-by-artist-id :? :*
-- :doc selects all bands for artist
SELECT * FROM bands, memberships
WHERE bands.id = memberships.band_id AND memberships.artist_id = :id AND state_id = :state_id

-- :name membership? :? :1
-- :doc returns true if membership exists
SELECT true FROM memberships
WHERE band_id = :band_id AND artist_id = :artist_id

-- :name create-membership! :! :n
-- :doc creates a new membership record
INSERT INTO memberships
(band_id, artist_id, created_at, updated_at)
VALUES (:band_id, :artist_id, :created_at, :updated_at)

-- :name accept-membership! :! :n
-- :doc accepts membership
UPDATE memberships
SET state_id = 2
WHERE artist_id = :artist_id AND band_id = :band_id

-- :name delete-membership! :! :n
-- :doc deletes membership
DELETE FROM memberships
WHERE band_id = :band_id AND artist_id = :artist_id 











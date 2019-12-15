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

-- :name get-artists :? :*
-- :doc selects all artists
SELECT * FROM artists

-- :name get-artist :? :1
--doc retrieves an artist record given the id
SELECT * FROM artists
WHERE id = :id

-- :name create-artist! :! :n
-- :doc creates a new artist record
INSERT INTO artists
(name, bio, location, created_at, updated_at)
VALUES (:name, :bio, :location, :created_at, :updated_at)

-- :name update-artist! :! :n
-- :doc updates an existing artist record
UPDATE artists
SET name = :name, bio = :bio, location = :location, updated_at = :updated_at
WHERE id = :id

-- :name delete-artist! :! :n
-- :doc deletes an artist record given the id
DELETE FROM artists
WHERE id = :id 

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
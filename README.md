# musicbook

Project musicbook is made as part of the Faculty of Organizational Sciences course work. Musicbook is a platform made for musicians and bands where they can connect with each other, create bands or become part of exsisting ones.

##  Environments

Musicbook is created and developed using Leiningen in Sublime text editor and Luminus micro-framework. H2 database is used for development of this project.

## Requirements 

JDK 8 and Leiningen.


## Setup

To run database migrations:

```bash
lein run migrate
```

To start a web server for the application, run:

```bash
lein run
```

##Functionality Description

The application contains variety of pages: home page, search artists page, search bands page, artist profile, band profile and all the forms to support full CRUD functionality.

Users sign up and login as artists. Once they are logged in, they can set up their profile for public, create there own band with personalized public web page, including custom images, and invite other artists to join their band.

Application features full authentication and authorization support. Meaning, certain pages can only be accessed by authenticated users and certain resources can only be managed by authorized users. For example, only the owner of the band can update its profile.

## Library Description

Leiningen is a build automation and dependency management tool for Clojure projects.

Luminus is a Clojure micro-framework based on a set of lightweight libraries.

Ring is used for request, response, as handler and as middleware.

Reitit is used to define application routes.

Migratus is used for database migrations and HugSQL for database interaction.

For handling and validation of the structures is used Struct library.

Selmer library is used as templating language and Bulma as frontend framework with Materialize icons.

Buddy and bcryp is used for password hashing.

Imagemagick is used for image processing. 


## License

Copyright © 2020 Nadežda Nisić

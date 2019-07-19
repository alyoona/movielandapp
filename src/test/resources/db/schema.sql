CREATE SCHEMA movieland;

CREATE TABLE movieland.users (
  id IDENTITY NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.review (
  id IDENTITY NOT NULL,
  user_id BIGINT NOT NULL,
  movie_id BIGINT NOT NULL,
  description TEXT NOT NULL,
  CONSTRAINT review_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.poster (
  id IDENTITY NOT NULL,
  movie_id BIGINT NOT NULL,
  picture_path TEXT NOT NULL,
  CONSTRAINT poster_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.movie (
  id IDENTITY NOT NULL,
  name_russian VARCHAR(255) NOT NULL,
  name_native VARCHAR(255) NOT NULL,
  year DATE NOT NULL,
  description TEXT NOT NULL,
  rating DOUBLE NOT NULL,
  price DOUBLE NOT NULL,
  CONSTRAINT movie_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.genre (
  id IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  CONSTRAINT genre_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.movie_genre (
  id IDENTITY NOT NULL,
  movie_id BIGINT NOT NULL,
  genre_id BIGINT NOT NULL,
  CONSTRAINT movie_genre_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.roles (
  id IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  CONSTRAINT roles_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.user_roles (
  id IDENTITY NOT NULL,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT user_roles_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.country (
  id IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  CONSTRAINT country_pk PRIMARY KEY (id)
);



CREATE TABLE movieland.movie_country (
  id IDENTITY NOT NULL,
  movie_id BIGINT NOT NULL,
  country_id BIGINT NOT NULL,
  CONSTRAINT movie_country_pk PRIMARY KEY (id)
);




ALTER TABLE movieland.review ADD CONSTRAINT review_fk0 FOREIGN KEY (user_id) REFERENCES movieland.users(id);
ALTER TABLE movieland.review ADD CONSTRAINT review_fk1 FOREIGN KEY (movie_id) REFERENCES movieland.movie(id);

ALTER TABLE movieland.poster ADD CONSTRAINT poster_fk0 FOREIGN KEY (movie_id) REFERENCES movieland.movie(id);



ALTER TABLE movieland.movie_genre ADD CONSTRAINT movie_genre_fk0 FOREIGN KEY (movie_id) REFERENCES movieland.movie(id);
ALTER TABLE movieland.movie_genre ADD CONSTRAINT movie_genre_fk1 FOREIGN KEY (genre_id) REFERENCES movieland.genre(id);


ALTER TABLE movieland.user_roles ADD CONSTRAINT user_roles_fk0 FOREIGN KEY (user_id) REFERENCES movieland.users(id);
ALTER TABLE movieland.user_roles ADD CONSTRAINT user_roles_fk1 FOREIGN KEY (role_id) REFERENCES movieland.roles(id);


ALTER TABLE movieland.movie_country ADD CONSTRAINT movie_country_fk0 FOREIGN KEY (movie_id) REFERENCES movieland.movie(id);
ALTER TABLE movieland.movie_country ADD CONSTRAINT movie_country_fk1 FOREIGN KEY (country_id) REFERENCES movieland.country(id);


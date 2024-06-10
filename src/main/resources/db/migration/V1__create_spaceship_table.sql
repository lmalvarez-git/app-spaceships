DROP TABLE IF EXISTS spaceship;
CREATE TABLE spaceship (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    movie_series VARCHAR(255)
);

INSERT INTO spaceship (name, movie_series) VALUES
('X-Wing', 'Star Wars'),
('USS Enterprise', 'Star Trek'),
('Halcón Milenario', 'Star Wars'),
('Battlestar Galactica', 'Battlestar Galactica');
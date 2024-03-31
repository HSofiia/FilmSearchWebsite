INSERT INTO PUBLIC.ACTOR (ACTOR_NAME, GENDER, NATIONALITY) VALUES
                                                               ('Killian Merphy', 'M', 'Irish'),
                                                               ('Keira Knightley', 'F', 'British'),
                                                               ('Leonardo DiCaprio', 'M', 'American'),
                                                               ('Rami Malek', 'M', 'American'),
                                                               ('Matt Damon', 'M', 'American');

INSERT INTO PUBLIC.FILM (FILM_NAME, RELEASE_YEAR, BOX_OFFICE, GENRE) VALUES
                                                                         ('Oppenheimer', '2023-07-19', 934.9, 'HISTORY'),
                                                                         ('No Time to Die', '2021-09-30', 774.2, 'SPY'),
                                                                         ('Inception', '2010-07-21', 837.0, 'ACTION'),
                                                                         ('Pride and Prejudice', '2005-10-12', 121.6, 'ROMANCE'),
                                                                         ('Peaky Blinders', '2022-04-03', 62.0, 'HISTORY'),
                                                                         ('Pirates of the Caribbean', '2017-05-26', 795.9, 'ACTION');

INSERT INTO PUBLIC.FILM_CASTING (FILM_ID, ACTOR_ID) VALUES
                                                      (1, 1), -- Oppenheimer - Killian Merphy
                                                      (1, 4), -- Oppenheimer - Rami Malek
                                                      (2, 4), -- No Time to Die - Rami Malek
                                                      (3, 1), -- Inception - Killian Merphy
                                                      (3, 3), -- Inception - Leonardo DiCaprio
                                                      (3, 5), -- Inception - Matt Damon
                                                      (4, 2), -- Pride and Prejudice - Keira Knightley
                                                      (5, 1), -- Peaky Blinders - Killian Merphy
                                                      (6, 2); -- Pirates of the Caribbean - Keira Knightley

INSERT INTO public.director(director_name, birth, award) VALUES ('Joachim Rønning', 1972, 'Academy Award for Best International Feature Film'),
                                                                        ('Christopher Nolan', 1970, 'Academy Award for Best Picture'),
                                                                        ('Cary Joji Fukunaga',1977, 'Primetime Emmy Award for Outstanding Directing for a Drama Series'),
                                                                        ('Joe Wright', 1972, 'BAFTA Award for Outstanding Debut by a British Writer, Director or Producer'),
                                                                        ('Otto Bathurst', 1971, 'British Academy Television Award for Best Drama Serial');


INSERT INTO user_table (username, password, role) VALUES ('admin', '$2a$10$N3TGXdcRDhTBQbPxD1BFjO2c0/eV7mAZKl5bpgakNn4tRET53Kgbi', 1),
                                                      ('user', '$2a$10$N3TGXdcRDhTBQbPxD1BFjO2c0/eV7mAZKl5bpgakNn4tRET53Kgbi', 0)

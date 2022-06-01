USE tenselight;

TRUNCATE TABLE users;

INSERT INTO users(firstname, lastname, email, type, isactive)
VALUES
    ('Chris', 'Maria', 'cmaria@jahnelgroup.com', 1, 1),
    ('Dan', 'Kollar', 'email@example.com', 0, 1),
    ('Jessie', 'Lucier', 'email@example.com', 0, 1)
;

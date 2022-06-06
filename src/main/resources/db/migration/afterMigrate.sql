USE tenselight;

DELETE FROM users;
ALTER TABLE users AUTO_INCREMENT = 1;

INSERT INTO users(firstname, lastname, email, type, isactive)
VALUES
    ('Chris', 'Maria', 'cmaria@jahnelgroup.com', 0, 1),
    ('Rida', 'Rahman', 'email@example.com', 1, 1),
    ('Will', 'Bacon', 'email@example.com', 1, 1),
    ('Chris', 'Carbone', 'email@example.com', 1, 1),
    ('Chris', 'Vanderloo', 'email@example.com', 1, 1),
    ('Sean', 'Watson', 'email@example.com', 1, 1),
    ('Megan', 'TheeStreamer', 'email@example.com', 1, 1),
    ('Gregory', 'Siemers', 'email@example.com', 1, 1)
;

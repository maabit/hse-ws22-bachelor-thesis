INSERT INTO publisher (name)
    VALUES
    ('Hanser'),
    ('mitp');

INSERT INTO book (isbn, title, publisher_id)
    VALUES
    ('9783446455122', 'Spring im Einsatz', 1),
    ('9783826697005', 'Design Patterns', 2);

INSERT INTO author (name)
    VALUES
    ('Craig Walls'),
    ('Erich Gamma'),
    ('Richard Helm'),
    ('Ralph Johnson'),
    ('John Vlissides');

INSERT INTO book_author(isbn, author_id)
    VALUES
    ('9783446455122',1),
    ('9783826697005',2),
    ('9783826697005',3),
    ('9783826697005',4),
    ('9783826697005',5);
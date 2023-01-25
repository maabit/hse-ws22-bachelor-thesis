-- CREATE OR REPLACE FUNCTION random_between(low INT ,high INT)
--    RETURNS INT AS
-- $$
-- BEGIN
--    RETURN floor(random()* (high-low + 1) + low);
-- END;
-- $$ language 'plpgsql' STRICT;

-- DO
-- $$
-- BEGIN
-- 	FOR  i IN 0..99 LOOP
-- 		INSERT INTO publisher ("id", "name")
-- 		VALUES (i,CONCAT('Publisher_', i::text));
-- 	END LOOP;
-- ALTER SEQUENCE publisher_id_seq RESTART WITH 100;
-- END
-- $$;

-- DO
-- $$
-- BEGIN
-- 	FOR  i IN 0..99 LOOP
-- 		INSERT INTO author ("id","name")
-- 		VALUES (i,CONCAT('Author_', i::text));
-- 	END LOOP;
-- 	ALTER SEQUENCE author_id_seq RESTART WITH 100;
-- END
-- $$;

-- DO
-- $$
-- BEGIN
-- 	FOR  i IN 0..999 LOOP
-- 		INSERT INTO book (isbn, title, publisher_id)
-- 		VALUES (LPAD(i::text, 13, '0'), CONCAT('Title_', i::text), random_between(0,99));
-- 	END LOOP;
-- END
-- $$;

-- DO
-- $$
-- DECLARE
-- random integer :=0;

-- BEGIN
-- 	FOR i in 0..999 LOOP
-- 		random := floor(random()*10+1);
-- 		INSERT INTO book_author (isbn, author_id)
-- 		SELECT *
-- 		FROM
-- 			(SELECT LPAD(i::text,13,'0')) AS T1
-- 			CROSS JOIN
-- 			(SELECT  random_between(1,99)
-- 				FROM generate_series(1,random)
-- 				GROUP BY 1
-- 			) AS T2;
-- 	END LOOP;
-- END
-- $$;

INSERT INTO publisher (name)
    VALUES
    ('Hanser'),
    ('mitp'),
    ('dpunkt.verlag'),
    ('Addison-Wesley'),
    ('CreateSpace');

INSERT INTO book (isbn, title, publisher_id)
    VALUES
    ('9783446455122', 'Spring im Einsatz', 1),
    ('9783826697005', 'Design Patterns', 2),
    ('9783864902925', 'Langlebige Software-Architekturen', 3),
    ('9780201633856','Object-Oriented Design Heuristics', 4),
    ('9781519166913','Elegant Objects Volume 1', 5),
    ('9781534908307','Elegant Objects Volume 2', 5);

INSERT INTO author (name)
    VALUES
    ('Craig Walls'),
    ('Erich Gamma'),
    ('Richard Helm'),
    ('Ralph Johnson'),
    ('John Vlissides'),
    ('Carola Lilienthal'),
    ('Arthur J. Riel'),
    ('Yegor Bugayenko');

INSERT INTO book_author(isbn, author_id)
    VALUES
    ('9783446455122',1),
    ('9783826697005',2),
    ('9783826697005',3),
    ('9783826697005',4),
    ('9783826697005',5),
    ('9783864902925',6),
    ('9780201633856',7),
    ('9781519166913',8),
    ('9781534908307',8);
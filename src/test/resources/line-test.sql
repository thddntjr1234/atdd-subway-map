SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE station;
TRUNCATE TABLE line;

ALTER TABLE station ALTER COLUMN id RESTART WITH 1;
ALTER TABLE line ALTER COLUMN id RESTART WITH 1;

SET REFERENTIAL_INTEGRITY TRUE;
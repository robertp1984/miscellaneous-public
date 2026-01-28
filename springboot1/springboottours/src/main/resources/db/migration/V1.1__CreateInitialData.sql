
-- Initial data for tour package
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'BC', 'Backpack Cal');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'CC', 'California Calm');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'CH', 'California Hot springs');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'CY', 'Cycle California');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'DS', 'From Desert to Sea');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'KC', 'Kids California');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'NW', 'Nature Watch');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'SC', 'Snowboard Cali');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'TC', 'Taste of California');
insert into TOUR_PACKAGE(id, code, name) values(nextval('TOUR_PACKAGE_SEQ'), 'EU', 'Taste of Europe');

-- Initial data for tour
insert into TOUR(id, code, name, description, difficulty, region, tour_package_id)
	values(nextval('TOUR_SEQ'), 'POL', 'Poland', 'Poland is a country located in Central Europe. It is known for its rich history, beautiful landscapes, and vibrant culture.',
'EASY', 'CENTRAL_EUROPE', (select id from TOUR_PACKAGE where code='EU'));
insert into TOUR(id, code, name, description, difficulty, region, tour_package_id)
	values(nextval('TOUR_SEQ'), 'ITA', 'Italy', 'Italy is a country in Southern Europe known for its rich history, art, culture, and cuisine. It is home to many famous landmarks such as the Colosseum, Leaning Tower of Pisa, and Venice canals.',
'MEDIUM', 'SOUTHERN_EUROPE', (select id from TOUR_PACKAGE where code='EU'));
insert into TOUR(id, code, name, description, difficulty, region, tour_package_id)
	values(nextval('TOUR_SEQ'), 'ESP', 'Spain', 'Spain is a country located in Southwestern Europe. It is known for its diverse culture, beautiful beaches, historic cities, and delicious cuisine.',
'EASY', 'SOUTHERN_EUROPE', (select id from TOUR_PACKAGE where code='EU'));

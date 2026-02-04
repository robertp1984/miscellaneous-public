-- STICKY NOTES
insert into sticky_note(id, title, body, type, created) values(nextval('sticky_note_seq'), 'Git push', 'To push your changes to remote Git repository use: git push', 'PLAIN_TEXT', CURRENT_TIMESTAMP);
insert into sticky_note_link(id, sticky_note_id, link) values(nextval('sticky_note_link_seq'), (select id from sticky_note where title='Git push'), 'https://git-scm.com/');

insert into sticky_note(id, title, body, type, created) values(nextval('sticky_note_seq'), 'Spring Boot version', 'Current Spring Boot version is 4.1.0', 'PLAIN_TEXT', CURRENT_TIMESTAMP);
insert into sticky_note_link(id, sticky_note_id, link) values(nextval('sticky_note_link_seq'), (select id from sticky_note where title='Spring Boot version'), 'https://spring.io/');

---- AUTHENTICATION

insert into users(id, username, password, enabled, first_name, last_name) values(nextval('users_seq'), 'emma', '{bcrypt}$2a$12$yZsA2q3u0AVP2j5cY1Gpm.lyFyoJi5tGowGg9eUa0Vy2TnFs/DuaK', 1, 'Emma', 'Stone');
insert into users(id, username, password, enabled, first_name, last_name) values(nextval('users_seq'), 'ryan', '{bcrypt}$2a$12$1JC9LxpX9q2447/MX2SV5eT2MBz4ocV8NB0LW.CnjoKyntkzw3tdG', 1, 'Ryan', 'Gosling');
insert into users(id, username, password, enabled, first_name, last_name) values(nextval('users_seq'), 'margot', '{bcrypt}$2a$12$rqcVub.U3RVSPq1IJmcJeeJzb80fRjTZnLAwDsVqbpHV85LssY6Ce', 1, 'Margot', 'Robbie');
insert into users(id, username, password, enabled, first_name, last_name) values(nextval('users_seq'), 'zooey', '{bcrypt}$2a$12$rqcVub.U3RVSPq1IJmcJeeJzb80fRjTZnLAwDsVqbpHV85LssY6Ce', 1, 'Zooey', 'Deschanel');


insert into roles(id, rolename) values(nextval('roles_seq'), 'ROLE_STICKY_NOTES_ADMIN');
insert into roles(id, rolename) values(nextval('roles_seq'), 'ROLE_STICKY_NOTES_VIEWER');
insert into roles(id, rolename) values(nextval('roles_seq'), 'ROLE_STICKY_NOTES_MANAGER');


insert into user_roles(id, user_id, role_id) values(nextval('user_roles_seq'), (select id from users where username='emma'), (select id from roles where rolename='ROLE_STICKY_NOTES_VIEWER'));
insert into user_roles(id, user_id, role_id) values(nextval('user_roles_seq'), (select id from users where username='ryan'), (select id from roles where rolename='ROLE_STICKY_NOTES_VIEWER'));
insert into user_roles(id, user_id, role_id) values(nextval('user_roles_seq'), (select id from users where username='ryan'), (select id from roles where rolename='ROLE_STICKY_NOTES_MANAGER'));
insert into user_roles(id, user_id, role_id) values(nextval('user_roles_seq'), (select id from users where username='margot'), (select id from roles where rolename='ROLE_STICKY_NOTES_VIEWER'));
insert into user_roles(id, user_id, role_id) values(nextval('user_roles_seq'), (select id from users where username='margot'), (select id from roles where rolename='ROLE_STICKY_NOTES_ADMIN'));
insert into user_roles(id, user_id, role_id) values(nextval('user_roles_seq'), (select id from users where username='zooey'), (select id from roles where rolename='ROLE_STICKY_NOTES_VIEWER'));

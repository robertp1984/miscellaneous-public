----- STICKY NOTES

create table sticky_note(
    id numeric(20) primary key,
    title varchar(1024) not null,
    body varchar(10000) not null,
    type varchar(32) not null,
    created date not null
);
create sequence sticky_note_seq start with 1 increment by 1;

create table sticky_note_link(
    id numeric(20) primary key,
    sticky_note_id numeric(20) not null references sticky_note(id),
    link varchar(1024) not null
);
create sequence sticky_note_link_seq start with 1 increment by 1;


----- AUTHENTICATION

create table users (
    id numeric(20) primary key,
    username varchar(256) unique not null,
    password varchar(256) not null,
    enabled numeric(1) not null,
    first_name varchar(256) not null,
    last_name varchar(256) not null,
    CHECK(enabled = 0 or enabled = 1)
);
create sequence users_seq start with 1 increment by 1;

create table roles (
    id numeric(20) primary key,
    rolename varchar(256) unique not null
);
create sequence roles_seq start with 1 increment by 1;

create table user_roles (
    id numeric(20) primary key,
    user_id numeric(20) not null references users(id),
    role_id numeric(20) not null references roles(id)
);
create sequence user_roles_seq start with 1 increment by 1;


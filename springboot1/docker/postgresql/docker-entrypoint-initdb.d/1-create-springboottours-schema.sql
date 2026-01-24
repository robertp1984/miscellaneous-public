
drop table if exists TOUR_RATING;
drop table if exists TOUR;
drop table if exists TOUR_PACKAGE;
drop sequence if exists TOUR_RATING_SEQ;
drop sequence if exists TOUR_SEQ;
drop sequence if exists TOUR_PACKAGE_SEQ;

create table TOUR_PACKAGE (
    id   varchar(64) primary key,
    code varchar(255)  unique not null,
    name varchar(255) unique not null
);


create sequence TOUR_SEQ start 1 increment 1;
create table TOUR (
    id          numeric(32)     primary key,
    code        varchar(255)    unique not null,
    name        varchar(255)    unique not null,
    description varchar(1024)   not null,
    difficulty  varchar(50)     not null,
    region      varchar(50)     not null,
    tour_package_id varchar(64) references TOUR_PACKAGE(id)
);

create sequence TOUR_RATING_SEQ start 1 increment 1;
create table TOUR_RATING (
    id        numeric(32)       primary key,
    tour_id   numeric(32)       references TOUR(id),
    client_id numeric(32)       not null,
    comment   varchar(1024)     not null,
    score     numeric(10)       not null
);

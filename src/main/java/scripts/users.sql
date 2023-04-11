drop database if exists users;
create database users;

\c users

drop table if exists users;
create table users (
    employee_id serial primary key,
    email varchar(70),
    mobile_number varchar(20),
    password varchar(70),
    user_type varchar(70),
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    department varchar(70),
    birth_date date,
    gender varchar(10),
    position varchar(70)
);
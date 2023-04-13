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

insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, department, birth_date, gender, position) 
values('ally@gmail.com', '9178192726', '123456', 'employee', 'nika', 'artuz', 'astrero', 'development', '2015-07-25', 'female', 'developer 1');

drop table if exists user_tokens;
create table user_tokens (
	user_id int,
	token varchar(50),
	foreign key(user_id) references users(employee_id)
);
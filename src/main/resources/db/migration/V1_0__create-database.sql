create table company (
    id serial primary key,
    name varchar(50) not null
);
create table department (
    id serial primary key,
    company_id int not null,
    name varchar(50) not null,
    constraint fk_company foreign key (company_id)
        references company (id)
);
create table team (
    id serial primary key,
    department_id int not null,
    name varchar(50) not null,
    constraint fk_department foreign key (department_id)
        references department (id)
);
create table manager (
    id serial primary key,
    name varchar(50) not null,
    email varchar(50) not null
);
create table project (
    id serial primary key,
    team_id int not null,
    manager_id int not null,
    name varchar(50) not null,
    constraint fk_team foreign key (team_id)
         references team (id),
    constraint fk_manager foreign key (manager_id)
         references manager (id)
)

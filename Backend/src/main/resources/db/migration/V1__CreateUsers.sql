CREATE TABLE al_user_roles
(
    role_id        int         NOT NULL AUTO_INCREMENT,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (role_id)
);
insert into al_user_roles (role_name) values ('USER');

CREATE TABLE al_users 
(
                         id int NOT NULL AUTO_INCREMENT UNIQUE,
                         email varchar(50) NOT NULL UNIQUE,
                         password varchar(100) NOT NULL,
                         username varchar(7) NOT NULL UNIQUE,
                         role_id int not null,
                         primary key (id),
                         foreign key(role_id) references al_user_roles(role_id)
);
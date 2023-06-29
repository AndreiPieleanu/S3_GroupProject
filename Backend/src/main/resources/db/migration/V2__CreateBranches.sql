
CREATE TABLE al_branches 
(
                            id int NOT NULL AUTO_INCREMENT UNIQUE,
                            user_id int,
                            branch_name varchar(50) NOT NULL,
                            
                            primary key (id),
                            foreign key (user_id) references al_users(id)                           
);
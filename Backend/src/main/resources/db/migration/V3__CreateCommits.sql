CREATE TABLE al_commits (
                           id int NOT NULL AUTO_INCREMENT,
                           name varchar(50) not null, 
                           branch_id int NOT NULL,
                           version varchar(50) NOT NULL,
                           version_date datetime NOT NULL,
                           
                           primary key (id),
                           foreign key (branch_id) references al_branches(id)
                           );

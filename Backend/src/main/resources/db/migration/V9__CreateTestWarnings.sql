
CREATE TABLE al_testwarnings (
                                id int NOT NULL AUTO_INCREMENT,
                                warning_code int NOT NULL,
                                
                                primary key (id)
);

insert into al_testwarnings(warning_code) values (1), 
                                                 (2), 
                                                 (3);
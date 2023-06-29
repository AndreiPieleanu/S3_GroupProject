
CREATE TABLE al_resulttype (
                              id int NOT NULL AUTO_INCREMENT UNIQUE ,
                              result_name varchar(50) NOT NULL,
                              
                              primary key (id)
);

insert into al_resulttype(result_name) values ('Passed'), ('Failed'), ('Tool 
died'), ('Other issues (aborted job)'), ('No results');
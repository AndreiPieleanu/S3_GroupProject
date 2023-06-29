
CREATE TABLE al_testerrors (
                            id int NOT NULL AUTO_INCREMENT,
                            error_code varchar(50) NOT NULL,

                             primary key (id)
);

insert into al_testerrors(error_code) values ('Could not find method!'), 
                                             ('NullPointerException'), 
                                             ('Could not open file!'), 
                                             ('Method returned unexpected result!');
CREATE TABLE al_subtests (
                            id int NOT NULL AUTO_INCREMENT UNIQUE,
                            name varchar(50) NOT NULL,
                            test_result_id int NOT NULL,
                            primary key (id),
                            foreign key (test_result_id) references
                                al_tests(id)
                        
);
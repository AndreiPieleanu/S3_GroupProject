
CREATE TABLE al_tests (
                               id int NOT NULL AUTO_INCREMENT,
                               name varchar(50) NOT NULL,
                               test_set_id int NOT NULL,
                               test_duration varchar(50) not null, 
                               primary key (id),
                               foreign key (test_set_id) references al_testsets(id)
);
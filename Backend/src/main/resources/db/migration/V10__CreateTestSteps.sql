
CREATE TABLE al_teststeps (
                             id int NOT NULL AUTO_INCREMENT UNIQUE ,
                             step_no int NOT NULL,
                             subtest_id int NOT NULL,
                             result_type_id int NOT NULL,
                             error_id int,
                             warning_id int,
                             result_details text NOT NULL,
                             
                             
                             primary key (id),
                             foreign key (subtest_id) references al_subtests(id),
                             foreign key (result_type_id) references 
                                 al_resulttype(id),
                            foreign key (error_id) references al_testerrors(id),
                            foreign key (warning_id) references al_testwarnings(id)
);
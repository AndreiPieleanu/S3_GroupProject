-- Generate sample data for users table
INSERT INTO al_users (email, password, username, role_id) 
VALUES 
    ('jdoe@gmail.com', '$2a$10$VikgU9SvtQD0gU3C19KTjeQRR4uQI.NRkBFpU8Ok4haEHloBS64rS', 'JDoe', 1);

-- Generate sample data for branches table
INSERT INTO al_branches (user_id, branch_name)
VALUES (1, 'userBranch2');

INSERT INTO al_branches (branch_name)
VALUES 
    ('main'), 
    ('master'), 
    ('highCoverage'), 
    ('broken-pipe-fix');

-- Generate sample data for commits table
INSERT INTO al_commits (name, branch_id, version, version_date)
VALUES 
    ('CPP-V1 removed comments', 1, "CPP-V1", "2023-05-22 11:40:56"), 
    ('CPP-V2 added more tests', 1, "CPP-V2", "2023-05-27 09:22:34"), 
    ('CPP-V3 fixed issues', 1, "CPP-V3", "2023-06-02 08:45:56"), 

    ('CPP-V1 fixed method issue', 2, "CPP-V1", "2022-12-08 12:45:56"), 

    ('CPP-V1 added tests', 3, "CPP-V1", "2023-02-13 12:45:56"), 

    ('CPP-V1 removed unused imports', 4, "CPP-V1", "2023-04-18 12:45:56");

-- Generate sample data for testsets table
INSERT INTO al_testsets (name, commit_id)
VALUES 
    ('Test Set 1-v1', 1), 
    ('Test Set 1-v2', 2), 
    ('Test Set 1-v3', 3), 

    ('Test Set 2-v1', 4), 

    ('Test Set 3-v1', 5), 

    ('Test Set 4-v1', 6);

-- Generate sample data for tests table
INSERT INTO al_tests (name, test_set_id)
VALUES 
    ('get_print_shouldReturnSecondsArray', 1), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 1), 

    ('get_print_shouldReturnSecondsArray', 2), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 2), 

    ('get_print_shouldReturnSecondsArray', 3), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 3), 

    ('killAllPrintJobs_shouldReturnEmptyList', 4), 
    ('getTimer_shouldReturnTimeList', 4), 
    ('skipToNextJob_shouldReturnNextJobObject', 5), 
    ('switchOff_ShouldReturnVoid', 6), 
    ('turnOn_ShouldReturnEmptyList', 6), 
    ('repeatJob_ShouldReturnPreviousJobObject', 6);

-- Generate sample data for subtests table
INSERT INTO al_subtests (name, test_result_id)
VALUES 
    ('Subtest 1-v1', 1), 
    ('Subtest 2-v1', 1), 
    ('Subtest 3-v1', 2), 
    ('Subtest 4-v1', 2), 

    ('Subtest 1-v2', 3), 
    ('Subtest 2-v2', 3), 
    ('Subtest 3-v2', 4), 
    ('Subtest 4-v2', 4), 

    ('Subtest 1-v3', 5), 
    ('Subtest 2-v3', 5), 
    ('Subtest 3-v3', 6), 
    ('Subtest 4-v3', 6), 


    ('Subtest 5-v1', 7), 
    ('Subtest 6-v1', 8),
    ('Subtest 7-v1', 9), 
    ('Subtest 8-v1', 10), 
    ('Subtest 9-v1', 11), 
    ('Subtest 10-v1', 12), 
    ('Subtest 11-v1', 12), 
    ('Subtest 12-v1', 12);

-- Generate sample data for result_type table
-- No need to insert data as it's already done in the table creation script

-- Generate sample data for testerrors table
-- No need to insert data as it's already done in the table creation script

-- Generate sample data for testwarnings table
-- No need to insert data as it's already done in the table creation script

-- Generate sample data for teststeps table

-- Test steps for Subtest 1 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 1, 1, 'Test step 1 passed.'),
    (2, 1, 1, 'Test step 2 passed.'),
    (3, 1, 1, 'Test step 3 passed.'),
    (4, 1, 1, 'Test step 4 passed.'),
    (5, 1, 1, 'Test step 5 passed.');

-- Test steps for Subtest 2 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (1, 2, 3, 4, 1, 'Test step 1 failed.'),
    (2, 2, 5, 4, 1, 'No results.'),
    (3, 2, 5, 4, 1, 'No results.'),
    (4, 2, 5, 4, 1, 'No results.'),
    (5, 2, 5, 4, 1, 'No results.');

-- Test steps for Subtest 3 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 3, 1, 'Test step 1 passed.'),
    (2, 3, 1, 'Test step 2 passed.'),
    (3, 3, 1, 'Test step 3 passed.'),
    (4, 3, 1, 'Test step 4 passed.'),
    (5, 3, 1, 'Test step 5 passed.');

-- Test steps for Subtest 4 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 4, 1, 'Test step 1 passed.'),
    (2, 4, 1, 'Test step 2 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (3, 4, 2, 4, 1, 'Test step 3 failed.'),
    (4, 4, 5, 4, 1, 'No results.'),
    (5, 4, 5, 4, 1, 'No results.');


-- Test steps for Subtest 1 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 5, 1, 'Test step 1 passed.'),
    (2, 5, 1, 'Test step 2 passed.'),
    (3, 5, 1, 'Test step 3 passed.'),
    (4, 5, 1, 'Test step 4 passed.'),
    (5, 5, 1, 'Test step 5 passed.');

-- Test steps for Subtest 2 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (1, 6, 3, 4, 1, 'Test step 1 failed.'),
    (2, 6, 5, 4, 1, 'No results.'),
    (3, 6, 5, 3, 1, 'No results.'),
    (4, 6, 5, 4, 1, 'No results.'),
    (5, 6, 5, 4, 1, 'No results.');

-- Test steps for Subtest 3 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 7, 1, 'Test step 1 passed.'),
    (2, 7, 1, 'Test step 2 passed.'),
    (3, 7, 1, 'Test step 3 passed.'),
    (4, 7, 1, 'Test step 4 passed.'),
    (5, 7, 1, 'Test step 5 passed.');

-- Test steps for Subtest 4 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 8, 1, 'Test step 1 passed.'),
    (2, 8, 1, 'Test step 2 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (3, 8, 2, 4, 1, 'Test step 3 failed.'),
    (4, 8, 5, 4, 1, 'No results.'),
    (5, 8, 5, 4, 1, 'No results.');


-- Test steps for Subtest 1 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 9, 1, 'Test step 1 passed.'),
    (2, 9, 1, 'Test step 2 passed.'),
    (3, 9, 1, 'Test step 3 passed.'),
    (4, 9, 1, 'Test step 4 passed.'),
    (5, 9, 1, 'Test step 5 passed.');

-- Test steps for Subtest 2 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 10, 1, 'Test step 1 passed.'),
    (2, 10, 1, 'Test step 2 passed.'),
    (3, 10, 1, 'Test step 3 passed.'),
    (4, 10, 1, 'Test step 4 passed.'),
    (5, 10, 1, 'Test step 5 passed.');

-- Test steps for Subtest 3 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 11, 1, 'Test step 1 passed.'),
    (2, 11, 1, 'Test step 2 passed.'),
    (3, 11, 1, 'Test step 3 passed.'),
    (4, 11, 1, 'Test step 4 passed.'),
    (5, 11, 1, 'Test step 5 passed.');

-- Test steps for Subtest 4 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 12, 1, 'Test step 1 passed.'),
    (2, 12, 1, 'Test step 2 passed.'),
    (3, 12, 1, 'Test step 3 passed.'),
    (4, 12, 1, 'Test step 4 passed.'),
    (5, 12, 1, 'Test step 5 passed.');


-- Test steps for Subtest 5 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 13, 1, 'Test step 1 passed.'),
    (2, 13, 1, 'Test step 2 passed.'),
    (3, 13, 1, 'Test step 3 passed.'),
    (4, 13, 1, 'Test step 4 passed.'),
    (5, 13, 1, 'Test step 5 passed.');

-- Test steps for Subtest 6 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 14, 1, 'Test step 1 passed.'),
    (2, 14, 1, 'Test step 2 passed.'),
    (3, 14, 1, 'Test step 3 passed.'),
    (4, 14, 1, 'Test step 4 passed.'),
    (5, 14, 1, 'Test step 5 passed.');

-- Test steps for Subtest 7 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (1, 15, 5, 1, 2, 'No results.');

-- Test steps for Subtest 8 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 16, 1, 'Test step 1 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (2, 16, 2, 3, 1, 'Test step 2 failed.'),
    (3, 16, 5, 3, 1, 'No results.');

-- Test steps for Subtest 9 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 17, 1, 'Test step 1 passed.'),
    (2, 17, 1, 'Test step 2 passed.'),
    (3, 17, 1, 'Test step 3 passed.'),
    (4, 17, 1, 'Test step 4 passed.');

-- Test steps for Subtest 10 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 18, 1, 'Test step 1 passed.'),
    (2, 18, 1, 'Test step 2 passed.'),
    (3, 18, 1, 'Test step 3 passed.'),
    (4, 18, 1, 'Test step 4 passed.'),
    (5, 18, 1, 'Test step 5 passed.');

-- Test steps for Subtest 11 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 19, 1, 'Test step 1 passed.'),
    (2, 19, 1, 'Test step 2 passed.'),
    (3, 19, 1, 'Test step 3 passed.'),
    (4, 19, 1, 'Test step 4 passed.'),
    (5, 19, 1, 'Test step 5 passed.');

-- Test steps for Subtest 12 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 20, 1, 'Test step 1 passed.'),
    (2, 20, 1, 'Test step 2 passed.'),
    (3, 20, 1, 'Test step 3 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (4, 20, 4, 2, 3, 'Test step 4 failed.'),
    (5, 20, 5, 2, 3, 'No results.');

-- Add additional sample data as needed

COMMIT;
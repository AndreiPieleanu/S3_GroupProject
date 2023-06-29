START TRANSACTION;

-- Generate sample data for users table
INSERT INTO al_users (email, password, username, role_id) 
VALUES 
    ('jdoe@gmail.com', '$2a$10$VikgU9SvtQD0gU3C19KTjeQRR4uQI.NRkBFpU8Ok4haEHloBS64rS', 'JDoe', 1);

-- Generate sample data for branches table
INSERT INTO al_branches (user_id, branch_name)
VALUES 
    (1, 'userBranch2'),
    (1, 'failing_tests');

INSERT INTO al_branches (branch_name)
VALUES 
    ('main'), 
    ('master'), 
    ('highCoverage'), 
    ('broken-pipe-fix');

-- Generate sample data for commits table
INSERT INTO al_commits (name, branch_id, version, version_date)
VALUES 
    ('CPP-V1 removed comments', 1, "98.1.0.1", "2023-05-22 11:40:56"), 
    ('CPP-V2 added more tests', 1, "98.1.0.2", "2023-05-27 09:22:34"), 
    ('CPP-V3 fixed issues', 1, "98.1.0.3", "2023-06-02 08:45:56"), 

    ('CPP-V1 removed comments', 2, "45.1.0.1", "2023-05-22 11:40:56"), 
    ('CPP-V2 added more tests', 2, "45.1.0.2", "2023-05-27 09:22:34"), 
    ('CPP-V3 fixed issues', 2, "45.1.0.3", "2023-06-02 08:45:56"), 

    ('CPP-V1 fixed method issue', 3, "12.1.5.1", "2022-12-08 12:45:56"), 

    ('CPP-V1 added tests', 4, "15.2.0.1", "2023-02-13 12:45:56"), 

    ('CPP-V1 removed unused imports', 5, "1.20.0.1", "2023-04-18 12:45:56");

-- Generate sample data for testsets table
INSERT INTO al_testsets (name, commit_id)
VALUES 
    ('Test Set 1-v1', 1), 
    ('Test Set 1-v2', 2), 
    ('Test Set 1-v3', 3), 

    ('Test Set 1-v1', 4), 
    ('Test Set 1-v2', 5), 
    ('Test Set 1-v3', 6), 

    ('Test Set 2-v1', 7), 

    ('Test Set 3-v1', 8), 

    ('Test Set 4-v1', 9);

-- Generate sample data for tests table
INSERT INTO al_tests (name, test_set_id, test_duration)
VALUES 
    ('get_print_shouldReturnSecondsArray', 1, '00:00:23'), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 1, '00:00:13'), 

    ('get_print_shouldReturnSecondsArray', 2, '00:00:23'), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 2, '00:00:15'), 

    ('get_print_shouldReturnSecondsArray', 3, '00:00:23'), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 3, '00:00:56'), 

    ('get_print_shouldReturnSecondsArray', 4, '00:00:23'), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 4, '00:01:06'), 

    ('get_print_shouldReturnSecondsArray', 5, '00:00:23'), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 5, '00:00:19'), 

    ('get_print_shouldReturnSecondsArray', 6, '00:00:23'), 
    ('addPrintJobToQueue_shouldReturnUpdatedList', 6, '00:00:37'), 

    ('killAllPrintJobs_shouldReturnEmptyList', 7, '00:00:11'), 
    ('getTimer_shouldReturnTimeList', 7, '00:00:23'), 
    ('skipToNextJob_shouldReturnNextJobObject', 8, '00:00:26'), 
    ('switchOff_ShouldReturnVoid', 9, '00:00:31'), 
    ('turnOn_ShouldReturnEmptyList', 9, '00:00:07'), 
    ('repeatJob_ShouldReturnPreviousJobObject', 9, '00:00:24');

-- Generate sample data for subtests table
INSERT INTO al_subtests (name, test_result_id)
VALUES 
-- DATA for branch "userBranch2"
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

-- DATA for branch "failing_tests"
    ('Subtest 1-v1', 7), 
    ('Subtest 2-v1', 7), 
    ('Subtest 3-v1', 8), 
    ('Subtest 4-v1', 8), 

    ('Subtest 1-v2', 9), 
    ('Subtest 2-v2', 9), 
    ('Subtest 3-v2', 10), 
    ('Subtest 4-v2', 10), 

    ('Subtest 1-v3', 11), 
    ('Subtest 2-v3', 11), 
    ('Subtest 3-v3', 12), 
    ('Subtest 4-v3', 12), 

-- DATA for all other branches
    ('Subtest 5-v1', 13), 
    ('Subtest 6-v1', 14),
    ('Subtest 7-v1', 15), 
    ('Subtest 8-v1', 16), 
    ('Subtest 9-v1', 17), 
    ('Subtest 10-v1', 18), 
    ('Subtest 11-v1', 18), 
    ('Subtest 12-v1', 18);

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





-- Test steps for Subtest 1 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 13, 1, 'Test step 1 passed.'),
    (2, 13, 1, 'Test step 2 passed.'),
    (3, 13, 1, 'Test step 3 passed.'),
    (4, 13, 1, 'Test step 4 passed.'),
    (5, 13, 1, 'Test step 5 passed.');

-- Test steps for Subtest 2 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (1, 14, 3, 4, 1, 'Test step 1 failed.'),
    (2, 14, 5, 4, 1, 'No results.'),
    (3, 14, 5, 4, 1, 'No results.'),
    (4, 14, 5, 4, 1, 'No results.'),
    (5, 14, 5, 4, 1, 'No results.');

-- Test steps for Subtest 3 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 15, 1, 'Test step 1 passed.'),
    (2, 15, 1, 'Test step 2 passed.'),
    (3, 15, 1, 'Test step 3 passed.'),
    (4, 15, 1, 'Test step 4 passed.'),
    (5, 15, 1, 'Test step 5 passed.');

-- Test steps for Subtest 4 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 16, 1, 'Test step 1 passed.'),
    (2, 16, 1, 'Test step 2 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (3, 16, 2, 4, 1, 'Test step 3 failed.'),
    (4, 16, 5, 4, 1, 'No results.'),
    (5, 16, 5, 4, 1, 'No results.');


-- Test steps for Subtest 1 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 17, 1, 'Test step 1 passed.'),
    (2, 17, 1, 'Test step 2 passed.'),
    (3, 17, 1, 'Test step 3 passed.'),
    (4, 17, 1, 'Test step 4 passed.'),
    (5, 17, 1, 'Test step 5 passed.');

-- Test steps for Subtest 2 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (1, 18, 3, 4, 1, 'Test step 1 failed.'),
    (2, 18, 5, 4, 1, 'No results.'),
    (3, 18, 5, 3, 1, 'No results.'),
    (4, 18, 5, 4, 1, 'No results.'),
    (5, 18, 5, 4, 1, 'No results.');

-- Test steps for Subtest 3 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 19, 1, 'Test step 1 passed.'),
    (2, 19, 1, 'Test step 2 passed.'),
    (3, 19, 1, 'Test step 3 passed.'),
    (4, 19, 1, 'Test step 4 passed.'),
    (5, 19, 1, 'Test step 5 passed.');

-- Test steps for Subtest 4 of version 2
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 20, 1, 'Test step 1 passed.'),
    (2, 20, 1, 'Test step 2 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (3, 20, 2, 4, 1, 'Test step 3 failed.'),
    (4, 20, 5, 4, 1, 'No results.'),
    (5, 20, 5, 4, 1, 'No results.');


-- Test steps for Subtest 1 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 21, 1, 'Test step 1 passed.'),
    (2, 21, 1, 'Test step 2 passed.'),
    (3, 21, 1, 'Test step 3 passed.'),
    (4, 21, 1, 'Test step 4 passed.'),
    (5, 21, 1, 'Test step 5 passed.');

-- Test steps for Subtest 2 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 22, 1, 'Test step 1 passed.'),
    (2, 22, 1, 'Test step 2 passed.'),
    (3, 22, 1, 'Test step 3 passed.'),
    (4, 22, 1, 'Test step 4 passed.'),
    (5, 22, 1, 'Test step 5 passed.');

-- Test steps for Subtest 3 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 23, 1, 'Test step 1 passed.'),
    (2, 23, 1, 'Test step 2 passed.'),
    (3, 23, 1, 'Test step 3 passed.'),
    (4, 23, 1, 'Test step 4 passed.'),
    (5, 23, 1, 'Test step 5 passed.');

-- Test steps for Subtest 4 of version 3
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 24, 1, 'Test step 1 passed.'),
    (2, 24, 1, 'Test step 2 passed.'),
    (3, 24, 1, 'Test step 3 passed.'),
    (4, 24, 1, 'Test step 4 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (5, 24, 2, 4, 1, 'Test step 5 failed.');






-- Test steps for Subtest 5 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 25, 1, 'Test step 1 passed.'),
    (2, 25, 1, 'Test step 2 passed.'),
    (3, 25, 1, 'Test step 3 passed.'),
    (4, 25, 1, 'Test step 4 passed.'),
    (5, 25, 1, 'Test step 5 passed.');

-- Test steps for Subtest 6 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 26, 1, 'Test step 1 passed.'),
    (2, 26, 1, 'Test step 2 passed.'),
    (3, 26, 1, 'Test step 3 passed.'),
    (4, 26, 1, 'Test step 4 passed.'),
    (5, 26, 1, 'Test step 5 passed.');

-- Test steps for Subtest 7 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (1, 27, 5, 1, 2, 'No results.');

-- Test steps for Subtest 8 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 28, 1, 'Test step 1 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (2, 28, 2, 3, 1, 'Test step 2 failed.'),
    (3, 28, 5, 3, 1, 'No results.');

-- Test steps for Subtest 9 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 29, 1, 'Test step 1 passed.'),
    (2, 29, 1, 'Test step 2 passed.'),
    (3, 29, 1, 'Test step 3 passed.'),
    (4, 29, 1, 'Test step 4 passed.');

-- Test steps for Subtest 10 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 30, 1, 'Test step 1 passed.'),
    (2, 30, 1, 'Test step 2 passed.'),
    (3, 30, 1, 'Test step 3 passed.'),
    (4, 30, 1, 'Test step 4 passed.'),
    (5, 30, 1, 'Test step 5 passed.');

-- Test steps for Subtest 11 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 31, 1, 'Test step 1 passed.'),
    (2, 31, 1, 'Test step 2 passed.'),
    (3, 31, 1, 'Test step 3 passed.'),
    (4, 31, 1, 'Test step 4 passed.'),
    (5, 31, 1, 'Test step 5 passed.');

-- Test steps for Subtest 12 of version 1
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, result_details)
VALUES
    (1, 32, 1, 'Test step 1 passed.'),
    (2, 32, 1, 'Test step 2 passed.'),
    (3, 32, 1, 'Test step 3 passed.');
INSERT INTO al_teststeps (step_no, subtest_id, result_type_id, error_id, warning_id, result_details)
VALUES
    (4, 32, 4, 2, 3, 'Test step 4 failed.'),
    (5, 32, 5, 2, 3, 'No results.');

-- Add additional sample data as needed

COMMIT;
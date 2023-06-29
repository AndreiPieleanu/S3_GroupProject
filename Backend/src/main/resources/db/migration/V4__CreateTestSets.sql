CREATE TABLE al_testsets
(
    id        int    NOT NULL AUTO_INCREMENT,
    name     varchar(50) NOT NULL,
    commit_id int     NOT NULL,

    primary key (id),
    foreign key (commit_id) references al_commits (id)
);
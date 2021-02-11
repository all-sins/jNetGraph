CREATE TABLE IF NOT EXISTS `user` (
    `id`        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`      varchar(MAX),
    `surname`   varchar(MAX),
    `email`     varchar(100),
    `password`  varchar(100)

    -- Friends list FK
	-- SpeedTestCLI_FK
	-- Expample1_FK
	-- Expample2_FK
	-- ...
	-- ArthurImpl_FK

)ENGINE=InnoDB  DEFAULT CHARSET =UTF8;

CREATE TABLE IF NOT EXISTS `speedtest_cli` (

    `stcli_id` 			INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_fk`           INT,
    `exec_timestamp`    TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `response_time`     FLOAT(10),
    `download_speed` 	FLOAT(10),
    `upload_speed` 		FLOAT(10),

    -- Foreign key defining.
    FOREIGN KEY(user_fk) REFERENCES user(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tsu_impl` (

    `id` 				int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `exec_timestamp`    TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `response_time`     FLOAT(10),
    `download_speed` 	FLOAT(10),
    `upload_speed` 		FLOAT(10) -- Consider this comment as a soft-delete for the time being. To be hard-deleted later on.

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;


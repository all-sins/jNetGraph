CREATE TABLE IF NOT EXISTS `user` (
    `id`        int NOT NULL AUTO_INCREMENT PRIMARY KEY,
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

    `id` 				int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `exec_timestamp`    TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `response_time`     FLOAT(10),
    `download_speed` 	FLOAT(10),
    `upload_speed` 		FLOAT(10)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tsu_icmp` (

    `id` 				int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `exec_timestamp`    TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `response_time`     FLOAT(10),
    `download_speed` 	FLOAT(10),
    `upload_speed` 		FLOAT(10)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

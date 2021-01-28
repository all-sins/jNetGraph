CREATE TABLE IF NOT EXISTS `user` (
    `id`        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`      varchar(MAX),
    `surname`   varchar(MAX),
    `userStatus` varchar(20),
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

    `stcli_id` 			    INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_fk`               INT,
    `exec_timestamp`        TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `jitter_ms`             FLOAT(10),
    `latency_ms`     	    FLOAT(10),
    `downloadspeed_mbps`	FLOAT(10),
    `uploadspeed_mbps`      FLOAT(10),
    `packetloss_percentage` FLOAT(10),
    FOREIGN KEY(user_fk)    REFERENCES user(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tsu_icmp` (

    `id` 				int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `exec_timestamp`    TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `response_time`     FLOAT(10),
    `download_speed` 	FLOAT(10),
    `upload_speed` 		FLOAT(10)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO USER( `name` ,`surname`, `email`)
VALUES
('Dmitrijs', 'Uzvards', 'mail@mail.com');

INSERT INTO USER( `name` ,`surname`, `email`)
VALUES
('Dmitrijs', 'Uzvards', 'mail@mail.com');


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

CREATE TABLE IF NOT EXISTS `speedtestcli` (

    `stcliid` 			    INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userfk`               INT,
    `exectimestamp`        TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `jitterms`             FLOAT(10),
    `latencyms`     	    FLOAT(10),
    `downloadspeedmbps`	    FLOAT(10),
    `uploadspeedmbps`      FLOAT(10),
    `packetlosspercentage` FLOAT(10),
    FOREIGN KEY(userfk)    REFERENCES user(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tsu_impl` (

    `id` 				int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `exec_timestamp`    TIMESTAMP DEFAULT NOW(), -- Leaving this in case of manual testing. Remove if it becomes an issue.
    `response_time`     FLOAT(10),
    `download_speed` 	FLOAT(10),
    `upload_speed` 		FLOAT(10)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO USER( `name` ,`surname`, `email`, `password`)
VALUES
('Arturs', 'Uzvards', 'arturs@mail.com', 'arturs1');

INSERT INTO USER( `name` ,`surname`, `email`, `password`)
VALUES
('Mara', 'Uzvards', 'mara@mail.com', 'mara1');

INSERT INTO USER( `name` ,`surname`, `email`, `password`)
VALUES
('Admins', 'Uzvards', 'admin@mail.com', 'admin1');

INSERT INTO SPEEDTESTCLI (userfk, exectimestamp, jitterms, latencyms, downloadspeedmbps, uploadspeedmbps, packetlosspercentage)
VALUES
(1, '2021-02-10 20:17:18.108', 	0.379, 	2.124, 35.0, 38.545456, 0.0 )

CREATE TABLE IF NOT EXISTS `user` (

    `id`                            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`                          VARCHAR(MAX),
    `surname`                       VARCHAR(MAX),
    `userStatus`                    VARCHAR(20),
    `email`                         VARCHAR(100),
    `password`                      VARCHAR(100)

)ENGINE=InnoDB  DEFAULT CHARSET =UTF8;



CREATE TABLE IF NOT EXISTS `speedtestcli` (

    `stcliid` 			            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userfk`                        INT,
    `exectimestamp`                 TIMESTAMP DEFAULT NOW(),
    `jitterms`                      FLOAT(10),
    `latencyms`     	            FLOAT(10),
    `downloadspeedmbps`	            FLOAT(10),
    `uploadspeedmbps`               FLOAT(10),
    `packetlosspercentage`          FLOAT(10),
    FOREIGN KEY(userfk)             REFERENCES user(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;



CREATE TABLE IF NOT EXISTS `tsu_impl` (

    `tsuimplid` 		            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_fk`                       INT,
    `exec_timestamp`                TIMESTAMP DEFAULT NOW(),
    `response_time`                 FLOAT(2),
    `download_speed` 	            FLOAT(2),
    FOREIGN KEY(user_fk)            REFERENCES user(id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

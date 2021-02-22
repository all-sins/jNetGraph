-- INSERT INTO USER( `name` ,`surname`, `email`, `password`)
-- VALUES
-- ('Arturs', 'Uzvards', 'arturs@mail.com', 'arturs1');
--
-- INSERT INTO USER( `name` ,`surname`, `email`, `password`)
-- VALUES
-- ('Mara', 'Uzvards', 'mara@mail.com', 'mara1');
--
INSERT INTO USER( `name` ,`surname`, `email`, `password`, `userstatus`)
VALUES
('Admins', 'Uzvards', 'admin@mail.com', '$2a$10$3X40EbSlj36WVByvQgms7OlrFPICHbxWqiVZIW50ex/Vxh0QEd0UG', 'ACTIVE');

INSERT INTO USER( `name` ,`surname`, `email`, `password`, `userstatus`)
VALUES
('Useris', 'Uzvards', 'user@mail.com', '$2a$10$3X40EbSlj36WVByvQgms7OlrFPICHbxWqiVZIW50ex/Vxh0QEd0UG', 'ACTIVE');



INSERT INTO ROLE(`name`)
VALUES('ROLE_ADMIN');

INSERT INTO ROLE(`name`)
VALUES('ROLE_USER');

INSERT INTO USERS_ROLES(`user_id`, `role_id`)
VALUES(1,1);

INSERT INTO USERS_ROLES(`user_id`, `role_id`)
VALUES(2,2);

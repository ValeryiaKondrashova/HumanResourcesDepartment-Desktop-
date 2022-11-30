create database HumanResources;

use HumanResources;

CREATE TABLE users
(
	iduser					INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	name_user				varchar(45) NOT NULL,
	login_user				varchar(45) NOT NULL,
	password_user			varchar(45) NOT NULL,
	statusUser				INT NOT NULL
);

CREATE TABLE statusUser
(
	idstatus 				INT PRIMARY KEY UNIQUE NOT NULL,
    statusName 				varchar(45) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT users_statusUser_FK FOREIGN KEY (statusUser) REFERENCES statusUser (idstatus)
  ON DELETE CASCADE ON UPDATE CASCADE;
  
insert into statusUser(idstatus, statusName) values(1, 'Администратор');
insert into statusUser(idstatus, statusName) values(2, 'Пользователь');
-- select * from statusUser;
    
insert into users(name_user, login_user, password_user, statusUser) values ('user1', 'login1', 'password1', 2);
insert into users(name_user, login_user, password_user, statusUser) values ('admin', 'admin', 'admin', 1);
-- select * from users;

-- select name_user, statusName from users join statusUser on users.statusUser=statusUser.idstatus;

/*--------------------------------------------------------------------------------------------------------------------------*/

CREATE TABLE employees
(
	idemployee 				INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    first_name 				varchar(45) NOT NULL,
    last_name 				varchar(45) NOT NULL,
    patronymic 				varchar(45) NOT NULL,
    position 				INT NOT NULL,
    experience 				INT NOT NULL,
    startWork 				varchar(45) NOT NULL,
    telephone 				varchar(45) NOT NULL,
    email 					varchar(45) NOT NULL,
    timeWork 				INT NOT NULL
);

CREATE TABLE positions
(
	idposition 				INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    positionName			varchar(45) NOT NULL,
    salary					INT NOT NULL,
    id_department			INT NOT NULL
);

ALTER TABLE employees ADD CONSTRAINT employees_positions_FK FOREIGN KEY (position) REFERENCES positions (idposition)
  ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE departments
(
	iddepartment	INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    departmentName  varchar(45) NOT NULL
);

ALTER TABLE positions ADD CONSTRAINT positions_departments_FK FOREIGN KEY (id_department) REFERENCES departments (iddepartment)
  ON DELETE CASCADE ON UPDATE CASCADE;
  
INSERT INTO departments(departmentName) VALUES('Отдел кадров');
INSERT INTO departments(departmentName) VALUES('Отдел разработки ПО');
INSERT INTO positions(positionName, salary, id_department) VALUES('Кадровик', 1100, 1);
INSERT INTO positions(positionName, salary, id_department) VALUES('Front-End Разработчик', 4120, 2);
INSERT INTO employees(first_name, last_name, patronymic, position, experience, startWork, telephone, email, timeWork) 
VALUES('Иванов','Иван', 'Иванович', 1, 10, '01.01.2010', '+375290000000', 'user1@mail.ru', 160);
INSERT INTO employees(first_name, last_name, patronymic, position, experience, startWork, telephone, email, timeWork) 
VALUES('Петров','Петр', 'Петрович', 2, 12, '03.08.2008', '+375291111111', 'user2@mail.ru', 170);
-- select * from departments;
-- select * from positions;
-- select * from employees;

/*------------------------------------------------------------------------------------------------------------------------------*/

CREATE TABLE experience
(
	idexperience INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    experienceMIN   INT NOT NULL,
    experienceMAX   INT NOT NULL,
    percentOfExperience INT NOT NULL
);

INSERT INTO experience(experienceMIN, experienceMAX, percentOfExperience) VALUES(0, 4, 1);
INSERT INTO experience(experienceMIN, experienceMAX, percentOfExperience) VALUES(5, 10, 2);
INSERT INTO experience(experienceMIN, experienceMAX, percentOfExperience) VALUES(11, 15, 35);
-- SELECT * FROM experience;

/*---------------------------------------------------------------------------------------------------------------------------------*/

CREATE TABLE bonus
(
	idbonus INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    numberOfWorkingHoursMIN INT NOT NULL,
    numberOfWorkingHoursMAX INT NOT NULL,
    percentOfBonus INT NOT NULL
);

INSERT INTO bonus(numberOfWorkingHoursMIN, numberOfWorkingHoursMAX, percentOfBonus) VALUES(0, 160, 1);
INSERT INTO bonus(numberOfWorkingHoursMIN, numberOfWorkingHoursMAX, percentOfBonus) VALUES(161, 170, 20);
-- SELECT * FROM bonus;

/*----------------------------------------------------------------------------------------------------------------------------------*/

CREATE TABLE holidays
(
	idholiday INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	employee INT NOT NULL,
	startHoliday varchar(45) NOT NULL,
    endHoliday varchar(45) NOT NULL
);

ALTER TABLE holidays ADD CONSTRAINT holidays_employees_FK FOREIGN KEY(employee) REFERENCES employees(idemployee)
	ON DELETE CASCADE ON UPDATE CASCADE;
    
INSERT INTO holidays(employee, startHoliday, endHoliday) VALUES(1, '20.10.2022', '10.11.2022');
SELECT * FROM holidays;

/*-----------------------------------------------------------------------------------------------------------------------------*/

CREATE TABLE sickLeave
(
	idsickLeave INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    employee INT NOT NULL,
	startSickLeave varchar(45) NOT NULL,
    endSickLeave varchar(45)
);

ALTER TABLE sickLeave ADD CONSTRAINT sickLeave_employees_FK FOREIGN KEY (employee) REFERENCES employees(idemployee)
	ON DELETE CASCADE ON UPDATE CASCADE;
    
INSERT INTO sickLeave(employee, startSickLeave) VALUES(2, '12.10.2022');
-- SELECT * FROM sickLeave;

/*-------------------------------------------------------------------------------------------------------------------------------*/



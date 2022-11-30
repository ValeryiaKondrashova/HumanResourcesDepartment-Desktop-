USE humanresources;

/* СОТРУДНИКИ */
/* ПРОСМОТР СОТРУДНИКОВ */
SELECT idemployee, first_name, last_name, patronymic, positionName, experience, startWork, telephone, email, timeWork
FROM employees
JOIN positions ON employees.position = positions.idposition;

/* ДОБАВЛЕНИЕ СОТРУДНИКА */
INSERT INTO employees(first_name, last_name, patronymic, position, experience, startWork, telephone, email, timeWork) 
VALUES('Борисов','Борис', 'Борисович', (SELECT idposition FROM positions WHERE positionName = 'Front-End Разработчик'), 8, '03.08.2013', '+375293333333', 'user3@mail.ru', 153);
-- SELECT * FROM employees;

/* РЕДАКТИРОВАНИЕ СОТРУДНИКА */
UPDATE employees 
SET first_name = 'Ц', last_name = 'q', patronymic = 'q', position = (SELECT idposition FROM positions WHERE positionName = 'Front-End Разработчик'), experience = 1, startWork = 'op', telephone = 'op', email = 'op', timeWork = 11 
WHERE idemployee = 9; 
-- SELECT * FROM employees;

/* УДАЛЕНИЕ СОТРУДНИКА --- НЕ ПРОВЕРЕН! */
DELETE FROM employees WHERE idemployee = 6;

/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/* КАДРОВЫЕ ПЕРЕВОДЫ СОТРУДНИКА */
UPDATE employees SET position = (SELECT idposition FROM positions WHERE id_department = (SELECT iddepartment FROM departments WHERE departmentName = 'Отдел кадров')) WHERE idemployee = 4;
SELECT * FROM employees;

/* ОФОРМЛЕНИЕ БОЛЬНИЧНОГО */
INSERT INTO sickLeave(employee, startSickLeave) VALUES((SELECT idemployee FROM employees WHERE first_name = 'Сидоров'), '10.10.2010');
SELECT * FROM sickLeave;

/* ЗАКРЫТИЕ БОЛЬНИЧНОГО */
UPDATE sickLeave SET endSickLeave = '20.10.2010' WHERE employee = (SELECT idemployee FROM employees WHERE first_name = 'Сидоров');
SELECT * FROM sickLeave;

/* РАСЧЕТ ЗАРАБОТНОЙ ПЛАТЫ */
/*SELECT first_name, last_name, patronymic, positionName, experience, startWork, telephone, email, timeWorK, 
(SELECT salary FROM positions WHERE positions.idposition = employees.position)  AS 'ЗАРПЛАТА'
FROM employees
JOIN positions ON employees.position = positions.idposition;*/


/*----------------------------------------РАСЧЕТ ЗАРАБОТНОЙ ПЛАТЫ (MAIN)-----------------------------------------------------*/
-- salary + (salary*%experience) + (salary*%timeWork)

SELECT first_name, last_name, patronymic, positionName, experience, startWork, telephone, email, timeWorK, 
(SELECT salary FROM positions WHERE positions.idposition = employees.position)
+
((SELECT salary FROM positions WHERE positions.idposition = employees.position)
*
(SELECT percentOfExperience FROM experience WHERE employees.experience BETWEEN experienceMIN AND experienceMAX)) / 100
+
((SELECT salary FROM positions WHERE positions.idposition = employees.position)
*
(SELECT percentOfBonus FROM bonus WHERE employees.startWork BETWEEN numberOfWorkingHoursMIN AND numberOfWorkingHoursMAX)) / 100
AS 'ЗАРПЛАТА'
FROM employees
JOIN positions ON employees.position = positions.idposition;
/*-------------------------------------------------------------------------------------------------------------------------------------*/

select * from employees;

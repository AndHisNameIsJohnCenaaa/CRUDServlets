DELETE FROM employee_task;
DELETE FROM task;
DELETE FROM employee;
DELETE FROM company;
ALTER SEQUENCE global_seq RESTART WITH 100000;

insert into company(name, email)
values ('Aston', 'aston@mail.ru'), -- 100000
       ('DEL', 'digLeague@mail.ru'); -- 100001
insert into employee (name, telegram_id)
values ('Бывалый', 'vr_38'), -- 2
       ('Полкило', 'polkilo666'), -- 3
       ('Ляля', 'lyalya777'); -- 4
insert into task(company_id, deadline, description)
values (100000, '2020-01-30', 'очень интересно'), -- 5
       (100000, '2020-01-31', 'хахахах'), -- 6
       (100000, '2020-01-23', 'не хахахах'), -- 7
       (100001, '2020-02-24', 'очень интересно'), -- 8
       (100001, '2020-02-11', 'хахахах'), -- 9
       (100001, '2020-02-23', 'не хахахах'); --10

insert into employee_task(task_id, employee_id)
values (100005, 100002),
       (100006, 100002),
       (100007, 100002),
       (100005, 100003),
       (100007, 100003),
       (100007, 100004);

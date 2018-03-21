insert into customer (id, name, surname, patronymic, balance, is_blocked, login, password) values(1, 'Vanya', 'Ivanov', 'Ivanovich', 100, false, 'Vanya', 'Password');
insert into customer (id, name, surname, patronymic, balance, is_blocked, login, password) values(2, 'Sergey', 'Ivanov', 'Ivanovich', 200.02, false, 'Sergey', 'Password');
insert into customer (id, name, surname, patronymic, balance, is_blocked, login, password) values(3, 'Petya', 'Ivanov', 'Ivanovich', 300.03, false, 'Petya', 'Password');
insert into customer (id, name, surname, patronymic, balance, is_blocked, login, password) values(4, 'Katya', 'Ivanova', null, 400.04, false, 'Katya', 'Password');

insert into partner_mapping (id, partner_Id, account_Id, customer_id, name, surname, patronymic)
    values (100, 1, 1, 1, 'Vanek', 'Ivanov', 'Ivanovich');
insert into partner_mapping (id, partner_Id, account_Id, customer_id, name, surname, patronymic)
    values (101, 2, 1, 1, 'Vanek', 'Ivanov', 'Ivanovich');
insert into partner_mapping (id, partner_Id, account_Id, customer_id, name, surname, patronymic)
    values (102, 2, 2, 1, 'Vanek', 'Ivanov', 'Ivanovich');
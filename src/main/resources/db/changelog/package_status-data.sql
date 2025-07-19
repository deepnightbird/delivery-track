insert into package_status(name, is_initial, is_terminal) values ('Создана', 1, 0);
insert into package_status(name, is_initial, is_terminal) values ('В обработке', 0, 0);
insert into package_status(name, is_initial, is_terminal) values ('Отправлена', 0, 0);
insert into package_status(name, is_initial, is_terminal) values ('На складе', 0, 0);
insert into package_status(name, is_initial, is_terminal) values ('У курьера', 0, 0);
insert into package_status(name, is_initial, is_terminal) values ('Доставлена', 0, 1);
insert into package_status(name, is_initial, is_terminal) values ('Возвращена', 0, 1);
insert into package_status(name, is_initial, is_terminal) values ('Ошибка доставки', 0, 1);
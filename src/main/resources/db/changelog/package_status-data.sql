insert into package_status(name, is_initial, is_terminal) values ('Создана', true, false);
insert into package_status(name, is_initial, is_terminal) values ('В обработке', false, false);
insert into package_status(name, is_initial, is_terminal) values ('Отправлена', false, false);
insert into package_status(name, is_initial, is_terminal) values ('На складе', false, false);
insert into package_status(name, is_initial, is_terminal) values ('У курьера', false, false);
insert into package_status(name, is_initial, is_terminal) values ('Доставлена', false, true);
insert into package_status(name, is_initial, is_terminal) values ('Возвращена', false, true);
insert into package_status(name, is_initial, is_terminal) values ('Ошибка доставки', false, true);
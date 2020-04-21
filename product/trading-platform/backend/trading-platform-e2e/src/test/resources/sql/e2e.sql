insert into trading.users (id, login, password) values ('usr-admin', 'admin', 'CwfVmLzcXf7rs9Wxhmii8DcMFoHxcgpS');
insert into trading.users (id, login, password) values ('usr-philipp', 'philipp', 'philipp');
insert into trading.users (id, login, password) values ('usr-alice', 'alice', 'alice');
insert into trading.user_roles (id, user, role) values ('adm-role', 'usr-admin', 'admin');
insert into trading.user_roles (id, user, role) values ('philipp-role', 'usr-philipp', 'user');
insert into trading.user_roles (id, user, role) values ('alice-role', 'usr-alice', 'user');

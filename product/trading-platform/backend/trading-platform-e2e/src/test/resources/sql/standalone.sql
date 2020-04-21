insert into trading.users (id, login, password) values ('usr-xavier', 'xavier', 'xavier');
insert into trading.users (id, login, password) values ('usr-julie', 'julie', 'julie');
insert into trading.users (id, login, password) values ('usr-olivier', 'olivier', 'olivier');

insert into trading.user_roles (id, user, role) values ('xavier-role', 'usr-xavier', 'user');
insert into trading.user_roles (id, user, role) values ('julie-role', 'usr-julie', 'user');
insert into trading.user_roles (id, user, role) values ('olivier-role', 'usr-olivier', 'user');

insert into trading.accounts (id, owner, cash) values ('5d7c1cf9-2374-4aef-b278-ec3627f8ec29', 'usr-xavier', 100000.0);
insert into trading.accounts (id, owner, cash) values ('bc950999-3eb7-4ebd-a036-64e274a86760', 'usr-julie', 200000.0);
insert into trading.accounts (id, owner, cash) values ('4c0ea84f-bc95-466d-9519-7ecb99879435', 'usr-olivier', 300000.0);

insert into trading.instruments (id, price) values ('d2cc3b5c-6146-41e2-ba41-66514f3bdb40', 50.0);
insert into trading.instruments (id, price) values ('e6e1ffa2-fb87-46f1-b22d-b0b3b4570f0c', 100.0);
insert into trading.instruments (id, price) values ('abfb8320-f76c-4ef0-af9c-426d5399282f', 150.0);
insert into trading.instruments (id, price) values ('7a42bfc3-e0ef-4aea-926a-826dfdef6019', 200.0);
insert into trading.instruments (id, price) values ('0111e0fb-a438-47c1-bec9-6e5e17af1ebb', 250.0);
insert into trading.instruments (id, price) values ('7e976368-ecc1-4f91-9027-4d5361333c45', 300.0);
insert into trading.instruments (id, price) values ('473e0d5b-0699-44ac-b843-a6847085da70', 350.0);
insert into trading.instruments (id, price) values ('c70c2f93-9d9a-4bd6-b44d-e3dcd3e42b7a', 400.0);
insert into trading.instruments (id, price) values ('2e9c5d88-c3f5-4c05-8668-b096868368d1', 450.0);
insert into trading.instruments (id, price) values ('396e36c4-6107-4504-9977-786212c5575f', 500.0);

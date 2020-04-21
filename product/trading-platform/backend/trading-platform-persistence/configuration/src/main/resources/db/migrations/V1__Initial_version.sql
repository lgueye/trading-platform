DROP TABLE IF EXISTS instruments CASCADE;
DROP TABLE IF EXISTS instrument_events CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS order_events CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;

CREATE TABLE instruments
(
    id    VARCHAR(255) NOT NULL,
    price FLOAT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE instrument_events
(
    id          VARCHAR(255) NOT NULL,
    instrument  VARCHAR(255) NOT NULL,
    price       FLOAT        NOT NULL,
    "timestamp" VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE instrument_events
    ADD CONSTRAINT idx_instrument_evts_uniq UNIQUE (instrument, "timestamp");

CREATE TABLE bookings
(
    id          VARCHAR(255) NOT NULL,
    order_id    VARCHAR(255) NOT NULL,
    account     VARCHAR(255) NOT NULL,
    instrument  VARCHAR(255) NOT NULL,
    price       FLOAT        NOT NULL,
    quantity    FLOAT        NOT NULL,
    "timestamp" VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE bookings
    ADD CONSTRAINT idx_booking_evts_uniq UNIQUE (order_id, account, "timestamp");

CREATE TABLE order_events
(
    id          VARCHAR(255) NOT NULL,
    order_id    VARCHAR(255) NOT NULL,
    account     VARCHAR(255) NOT NULL,
    instrument  VARCHAR(255) NOT NULL,
    status      VARCHAR(50)  NOT NULL,
    quantity    FLOAT        NOT NULL,
    "timestamp" VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE order_events
    ADD CONSTRAINT idx_order_evts_uniq UNIQUE (order_id, account, "timestamp");

CREATE TABLE accounts
(
    id    VARCHAR(255) NOT NULL,
    owner VARCHAR(255) NOT NULL,
    cash  FLOAT        NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_accounts_owner ON accounts (owner);
CREATE INDEX idx_accounts_id ON accounts (id);

drop table user_roles if exists;
drop table users if exists;

create table user_roles
(
    id   varchar(255) not null,
    user varchar(255) not null,
    role varchar(50)  not null,
    primary key (id)
);

create table users
(
    id       varchar(255) not null,
    login    varchar(255),
    password varchar(255),
    primary key (id)
);

create index idx_user_roles_user on user_roles (user);
create index idx_user_roles_role on user_roles (role);

alter table user_roles
    add constraint idx_user_roles_uniq unique (user, role);

create index idx_user_login on users (login);
alter table users
    add constraint idx_user_login unique (login);

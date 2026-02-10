create sequence user_account_seq
    start with 1 increment by 1
    minvalue 1 maxvalue 9223372036854775807
    cache 1 no cycle;

create table user_account
(
    id                bigint                   default nextval('user_account_seq' :: regclass) not null,
    username  varchar(255) not null,
    password  varchar(255) not null,
    first_name  varchar(255) not null,
    last_name  varchar(255) not null,
    display_name  varchar(255) not null,
    role varchar(100),
    primary key (id)
);

create unique index user_account_unique_username on user_account (lower(trim(username)))
    where username is not null;

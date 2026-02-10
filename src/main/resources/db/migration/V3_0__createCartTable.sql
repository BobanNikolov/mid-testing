create sequence cart_seq start with 1 increment by 1 minvalue 1 maxvalue 9223372036854775807 cache 1 no cycle;

create table cart
(
    id                 bigint                   default nextval('cart_seq' :: regclass) not null,
    user_id            bigint not null,
    version            integer not null default 0,
    creation_time      timestamp with time zone not null,
    modification_time  timestamp with time zone not null,
    created_by         bigint,
    modified_by        bigint,
    primary key (id),
    constraint fk_cart_user
        foreign key (user_id)
            references user_account (id)
            on delete cascade
);

create unique index cart_unique_user on cart (user_id);
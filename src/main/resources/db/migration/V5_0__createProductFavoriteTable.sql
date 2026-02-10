create sequence product_favorite_seq start with 1 increment by 1 minvalue 1 maxvalue 9223372036854775807 cache 1 no cycle;

create table product_favorite
(
    id                 bigint                   default nextval('product_favorite_seq' :: regclass) not null,
    user_id            bigint not null,
    product_id         bigint not null,
    version            integer not null default 0,
    creation_time      timestamp with time zone not null,
    modification_time  timestamp with time zone not null,
    created_by         bigint,
    modified_by        bigint,
    primary key (id),
    constraint fk_product_favorite_user
        foreign key (user_id)
            references user_account (id)
            on delete cascade
);

create index idx_product_favorite_user on product_favorite (user_id);
create index idx_product_favorite_product on product_favorite (product_id);
create unique index product_favorite_unique_user_product on product_favorite (user_id, product_id);
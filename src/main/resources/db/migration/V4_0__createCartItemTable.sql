create sequence cart_item_seq start with 1 increment by 1 minvalue 1 maxvalue 9223372036854775807 cache 1 no cycle;

create table cart_item
(
    id                 bigint                   default nextval('cart_item_seq' :: regclass) not null,
    cart_id            bigint not null,
    product_id         bigint not null,
    quantity           integer not null,
    version            integer not null default 0,
    creation_time      timestamp with time zone not null,
    modification_time  timestamp with time zone not null,
    created_by         bigint,
    modified_by        bigint,
    primary key (id),
    constraint fk_cart_item_cart
        foreign key (cart_id)
            references cart (id)
            on delete cascade
);

create index idx_cart_item_cart on cart_item (cart_id);
create index idx_cart_item_product on cart_item (product_id);
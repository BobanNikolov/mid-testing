create sequence role_seq start with 1 increment by 1 minvalue 1 maxvalue 9223372036854775807 cache 1 no cycle;

create table role
(
    id                bigint                   default nextval('role_seq' :: regclass) not null,
    name              varchar(256) not null unique,
    primary key (id)
);

create table user_x_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint fk_user
        foreign key (user_id)
            references user_account (id)
                on delete cascade,
    constraint fk_role
        foreign key (role_id)
            references role (id)
                on delete cascade
);

insert into role(name) values ('ROLE_ADMIN') on conflict do nothing;
insert into role(name) values ('ROLE_USER') on conflict do nothing;

alter table user_account drop column if exists role;

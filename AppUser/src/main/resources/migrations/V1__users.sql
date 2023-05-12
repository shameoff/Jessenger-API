create table public.users
(
    id           varchar(255) not null primary key,
    avatar_uuid  varchar(255),
    birth_date   date,
    city         varchar(255),
    email        varchar(255),
    full_name     varchar(255) not null,
    username     varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255)
);
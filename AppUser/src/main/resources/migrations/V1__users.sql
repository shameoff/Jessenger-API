create table public.users
(
    id           uuid not null primary key,
    avatar_uuid  uuid,
    birth_date   date,
    city         varchar(255),
    email        varchar(255),
    full_name     varchar(255) not null,
    username     varchar(255) not null,
    password     varchar(255) not null,
    phone_number varchar(255)
);
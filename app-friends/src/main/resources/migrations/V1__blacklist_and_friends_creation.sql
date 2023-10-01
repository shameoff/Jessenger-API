create table public.blacklist
(
    id                uuid not null primary key,
    user_id           uuid not null,
    blocked_user_id   uuid not null,
    blocked_full_name varchar(255) not null,
    created_at        date,
    updated_at        date,
    deleted_at        date
);

alter table public.blacklist
    owner to friends_user;

create table public.friends
(
    id               uuid not null primary key,
    friend_id        uuid not null,
    user_id          uuid not null,
    friend_full_name varchar(255) not null,
    created_at       date,
    updated_at       date,
    deleted_at       date
);

alter table public.friends
    owner to friends_user;


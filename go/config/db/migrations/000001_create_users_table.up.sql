create table if not exists books (
    id uuid primary key not null default gen_random_uuid(),
    name varchar(255) not null,
    year integer not null,
    author varchar(255) not null,
    summary varchar(255) not null,
    publisher varchar(255) not null,
    page_count integer not null,
    read_page integer not null,
    reading boolean not null,
    inserted_at timestamp not null default (now()),
    updated_at timestamp not null default (now()),
    finished boolean not null
)

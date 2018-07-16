create table resume
(
  uuid      char(36) not null
    constraint resume_pkey
    primary key,
  full_name text     not null
);

create unique index resume_uuid_uindex
  on resume (uuid);

create table contact
(
  id          serial   not null,
  resume_uuid char(36) not null references resume (uuid) on delete cascade,
  type        text     not null,
  value       text     not null
);

create unique index contact_uuid_type_index
  on contact (resume_uuid, type);


CREATE TABLE section (
  id          SERIAL PRIMARY KEY,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  content     TEXT     NOT NULL
);
CREATE UNIQUE INDEX section_idx
  ON section (resume_uuid, type);

/*
select
    r.full_name as fullname,
    r.uuid as uuid,
    c.type as ctype,
    c.value as cvalue,
    s.type as stype,
    s.value as svalue
  from resume r
    left join contact c
      on c.resume_uuid = r.uuid
      left join section s
        on s.resume_uuid = r.uuid
  where r.uuid = '15e2b19b-727e-4eb6-b5b0-171cd5e625af'

 */
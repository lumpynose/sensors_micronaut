drop table if exists sensor;

create table sensor (
    id bigint not null auto_increment unique primary key,
    name varchar(255) not null,
    display_name varchar(255) unique,
    channel int
);

drop table if exists hidden;

create table hidden (
    id bigint not null auto_increment unique primary key,
    sensor_id bigint not null unique
);

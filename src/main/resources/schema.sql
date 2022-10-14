drop table if exists car;
create table car
(
    car_id BIGINT primary key auto_increment ,
    model varchar(50) not null,
    car_number varchar(10) not null unique
);
drop table if exists  "ORDER";
drop table if exists  reservation;
create table reservation
(
    order_id       BIGINT primary key auto_increment,
    status         integer default 0,
    start_at       datetime,
    predict_end_at datetime,
    actual_end_at  datetime,
    car_number     varchar(10),
    mobile         varchar(100),
    email          varchar(255),
    first_name     varchar(255),
    last_name      varchar(255)
)

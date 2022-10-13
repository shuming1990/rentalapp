create table "ORDER"
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

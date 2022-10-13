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


insert into "ORDER"(status, start_at, predict_end_at, actual_end_at, car_number, mobile, email, first_name, last_name)
values (0, 5,8,9, 'CAR001', '18675994623', '', 'shuming', 'cao')

insert into  "ORDER"(status, start_at, predict_end_at, actual_end_at, car_number, mobile, email, first_name, last_name)
select '12345', 0, 0,2,3, 'CAR001', '18675994623', '', 'shuming', 'cao'
from dual where not exists (
    select * from "ORDER" where
                              (start_at>=0 and start_at<=3)
                             or (actual_end_at>=0 and actual_end_at<=3)
                             or (start_at<=0 and actual_end_at>=3) and car_number = 'CAR001'
    );
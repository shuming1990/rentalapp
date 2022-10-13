create table car
(
    car_id BIGINT primary key auto_increment ,
    model varchar(50) not null,
    car_number varchar(10) not null unique
);


insert into car(model, car_number) values ('Toyota Camry','CAR001');
insert into car(model, car_number) values ('Toyota Camry','CAR002');
insert into car(model, car_number) values ('BMW 650','CAR003');
insert into car(model, car_number) values ('BMW 650','CAR004');


select model,count(*) from car where car_number not in
                            (select car_number from "ORDER" where (start_at>=0 and start_at<=1) or (actual_end_at>=0 and actual_end_at<=1) or (start_at<=0 and actual_end_at>=1))
                      group by model

select car_id, model, car_number from car where car_number not in (select car_number from "ORDER" where (start_at>=? and start_at <= ?)  or (actual_end_at>=?  and actual_end_at<= ?)  or (start_at<= ? and actual_end_at>=?))

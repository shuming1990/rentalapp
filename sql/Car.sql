drop table car;
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


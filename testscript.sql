drop table reportamounts;
-- cascade delete from report
drop table report;
-- casecade delete from store
drop table store;
-- no dependencies
drop table storeitems;
-- casecade delete from store
drop table employee;
-- cascade delete from store
drop table orders;
-- cascade delete from employee
drop table orderitems;
-- cascade delete from order
drop table sales;
-- cascade delete from employee
drop table saleitems;
-- cascade delete from sale
drop table nonalcoholicitem;
-- no dependencies
drop table beer;
-- no dependencies
drop table wine;
-- no dependencies

commit;

create table store (
    store_id int not null,
    address varchar(30),
    name varchar(30),
    phone_number char(12),
    primary key (store_id));

grant select on store to public;

create table beer (
    sku int not null,
    name varchar(30),
    tax double precision,
    deposit double precision,
    price double precision,
    description varchar(50),
    company varchar(30),
    region varchar(30),
    alcohol_percentage double precision,
    type varchar(20),
    volume int,
    pack_quantity int,
    primary key (sku));

grant select on beer to public;

create table wine (
    sku int not null,
    name varchar(30),
    tax double precision,
    deposit double precision,
    price double precision,
    description varchar(50),
    company varchar(30),
    region varchar(30),
    alcohol_percentage double precision,
    type varchar(20),
    volume int,
    subtype varchar(20),
    primary key (sku));

grant select on wine to public;

create table nonalcoholicitem (
    sku int not null,
    name varchar(30),
    tax double precision,
    deposit double precision,
    price double precision,
    description varchar(50),
    primary key (sku));

grant select on nonalcoholicitem to public;

create table storeitems (
    sku int not null,
    store_id int not null,
    stock_quantity int,
    primary key (sku, store_id),
    foreign key (sku) references beer,
    foreign key (store_id) references store);

grant select on storeitems to public;

create table employee (
    employee_id int not null,
    name varchar(30),
    username varchar(10),
    password varchar(10),
    salary double precision,
    store_id int not null,
    type int,
    primary key (employee_id),
    foreign key (store_id) references store);

grant select on employee to public;

create table orders (
    order_number int not null,
    supplier varchar(20),
    time_placed TIMESTAMP,
    date_placed date,
    time_received TIMESTAMP,
    date_received date,
    employee_id int not null,
    primary key (order_number),
    foreign key (employee_id) references employee);

grant select on orders to public;



insert into store values (1, '123 Fake Street', 'Our Private Store', '604-123-4567');
insert into beer (sku, name, price) values (500, 'GI Lager', 15.00);
insert into beer (sku, name, price) values (501, 'GI Pale Ale', 15.00);
insert into beer (sku, name, price) values (502, 'GI Honey Lager', 15.00);
insert into beer (sku, name, price) values (503, 'GI Winter Ale', 15.00);
insert into beer (sku, name, price) values (504, 'GI Summer Ale', 15.00);
insert into storeitems values (500, 1, 10);
insert into storeitems values (501, 1, 10);
insert into storeitems values (502, 1, 10);
insert into storeitems values (503, 1, 10);
insert into storeitems values (504, 1, 10);





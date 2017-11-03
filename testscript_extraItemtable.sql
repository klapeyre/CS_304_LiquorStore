drop table reportamounts;
-- cascade delete from report
drop table reports;
-- casecade delete from store
drop table stores;
-- no dependencies
drop table storeitems;
-- casecade delete from store
drop table employees;
-- cascade delete from store
drop table orders;
-- cascade delete from employee
drop table orderitems;
-- cascade delete from order
drop table sales;
-- cascade delete from employee
drop table saleitems;
-- cascade delete from sale
drop table beers;
-- no dependencies
drop table wines;
-- no dependencies

commit;

create table stores (
    store_id int not null,
    address varchar(30),
    name varchar(30),
    phone_number char(12),
    primary key (store_id));

grant select on stores to public;

commit;

create table items (
    sku int not null,
    name varchar(30),
    tax double precision,
    deposit double precision,
    price double precision,
    description varchar(50),
    primary key (sku));

grant select on items to public;

commit;

create table beers (
    sku int not null,
    company varchar(30),
    region varchar(30),
    alcohol_percentage double precision,
    type varchar(20),
    volume int,
    pack_quantity int,
    primary key (sku),
    foreign key (sku) references items on delete cascade on update cascade);

grant select on beers to public;

commit;

create table wines (
    sku int not null,
    company varchar(30),
    region varchar(30),
    alcohol_percentage double precision,
    type varchar(20),
    volume int,
    subtype varchar(20),
    primary key (sku),
    foreign key (sku) references items on delete cascade on update cascade);

grant select on wines to public;

commit;

create table storeitems (
    sku int not null,
    store_id int not null,
    stock_quantity int,
    primary key (sku, store_id),
    foreign key (sku) references items on delete cascade on update cascade,
    foreign key (store_id) references stores on delete cascade on update cascade);

grant select on storeitems to public;

commit;

create table employees (
    employee_id int not null,
    name varchar(30),
    username varchar(10),
    password varchar(10),
    salary double precision,
    store_id int not null,
    type int,
    primary key (employee_id),
    foreign key (store_id) references stores on delete no action on update cascade);

grant select on employees to public;

commit;

create table orders (
    order_number int not null,
    supplier varchar(20),
    time_placed TIMESTAMP,
    date_placed date,
    time_received TIMESTAMP,
    date_received date,
    employee_id int not null,
    primary key (order_number),
    foreign key (employee_id) references employees on delete no action on update cascade);

grant select on orders to public;

commit;

create table orderitems (
    sku int not null,
    order_number int not null,
    quantity int,
    primary key (sku, order_number),
    foreign key (sku) references items on delete no action on update no action,
    foreign key (order_number) references orders on delete set null on update cascade);

grant select on orderitems to public;

commit;

create table sales (
    sale_number int not null,
    total_price double precision,
    payment_type varchar(5),
    time timestamp,
    date date,
    employee_id int not null,
    primary key (sale_number),
    foreign key (employee_id) references employee on delete no action on update no action);

grant select on sales to public;

commit;

create table saleitems (
    sku int not null,
    sale_number int not null,
    quantity int,
    primary key (sku, sale_number),
    foreign key (sku) references items on delete no action on update no action,
    foreign key (sale_number) references sales on delete set null on update cascade);

grant select on saleitems to public;

commit;

create table reports (
    report_id int not null,
    start_date date,
    end_date date,
    store_id int not null,
    primary key (report_id),
    foreign key (store_id) references stores on delete no action on update cascade);

grant select on reports to public;

commit;

create table reportamounts (
    store_id int not null,
    start_date date,
    end_date date,
    total_sales double precision,
    total_orders double precision,
    total_wages double precision,
    primary key (store_id, start_date, end_date),
    foreign key (store_id) references stores on delete no action on update cascade,
    foreign key (start_date) references reports,
    foreign key (end_date) references reports);

grant select on reportamounts to public;

commit;

-- I still need to finish populating sample data

insert into stores values (1, '123 Fake Street', 'Our Private Store', '604-123-4567');
insert into items values (500, 'GI Lager');
insert into items values (501, 'GI Pale Ale');
insert into items values (502, 'GI Honey Lager');
insert into items values (503, 'GI Winter Ale');
insert into items values (504, 'GI Summer Ale');
insert into beers (sku, price) values (500, 15.00);
insert into beers (sku, price) values (501, 15.00);
insert into beers (sku, price) values (502, 15.00);
insert into beers (sku, price) values (503, 15.00);
insert into beers (sku, price) values (504, 15.00);
insert into storeitems values (500, 1, 10);
insert into storeitems values (501, 1, 10);
insert into storeitems values (502, 1, 10);
insert into storeitems values (503, 1, 10);
insert into storeitems values (504, 1, 10);

commit;





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
    foreign key (sku) references items on delete cascade);

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
    foreign key (sku) references items on delete cascade);

grant select on wines to public;

commit;

create table storeitems (
    sku int not null,
    store_id int not null,
    stock_quantity int,
    primary key (sku, store_id),
    foreign key (sku) references items on delete cascade,
    foreign key (store_id) references stores on delete cascade);

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
    foreign key (store_id) references stores);

grant select on employees to public;

commit;

create table orders (
    order_number int not null,
    supplier varchar(20),
    time_placed TIMESTAMP,
    date_placed TIMESTAMP,
    time_received TIMESTAMP,
    date_received TIMESTAMP,
    employee_id int not null,
    primary key (order_number),
    foreign key (employee_id) references employees);

grant select on orders to public;

commit;

create table orderitems (
    sku int not null,
    order_number int not null,
    quantity int,
    primary key (sku, order_number),
    foreign key (sku) references items,
    foreign key (order_number) references orders on delete set null);

grant select on orderitems to public;

commit;

create table store_sales (
    sale_number int not null,
    total_price double precision,
    payment_type varchar(5),
    sale_date timestamp,
    employee_id int not null,
    primary key (sale_number),
    foreign key (employee_id) references employees);

grant select on store_sales to public;

commit;

create table saleitems (
    sku int not null,
    sale_number int not null,
    quantity int,
    primary key (sku, sale_number),
    foreign key (sku) references items,
    foreign key (sale_number) references store_sales on delete set null);

grant select on saleitems to public;

commit;

create table reports (
    report_id int not null,
    st_date timestamp,
    end_date timestamp,
    store_id int not null,
    total_sales double precision,
    total_orders double precision,
    total_wages double precision,
    primary key (report_id),
    foreign key (store_id) references stores);

grant select on reports to public;

commit;

-- I still need to finish populating sample data

commit;





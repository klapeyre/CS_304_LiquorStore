drop table saleitems;
-- depends on items and employees
drop table orderitems;
-- depends on items and employees
drop table storeitems;
-- depends on items and stores
drop table beers;
-- depends on items
drop table wines;
-- depends on items
drop table items;
-- no dependencies
drop table reports;
-- depends on stores
drop table orders;
-- depends on employees
drop table store_sales;
-- depends on employees
drop view clerk_view;
-- depends on employees;
drop table employees;
-- depends on stores
drop table stores;
-- no dependencies
commit;
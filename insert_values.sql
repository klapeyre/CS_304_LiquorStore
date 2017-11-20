-- This file will populate the tables with simple data.
-- SHOULD NOT BE RUN UNTIL DROP AND CREATE HAVE BEEN RUN IN SUCCESSION

insert into stores values (1,'123 Fake Street','Liquor Store 1','604-123-4567');
insert into stores values (2,'2395 Lower Avenue','Central Liquor Store','778-234-5679');

commit;

insert into items values (1000,'Budweiser 6pk',0.15,0.30,12.00,'Cheap American beer');
insert into items values (1001,'Kokanee 6pk',0.15,0.30,11.50,'Other cheap American beer');
insert into items values (1002,'Granville Island Pale Ale',0.15,0.30,14.50,'English Bay Pale Ale');
insert into items values (1003,'Granville Island Lager',0.15,0.30,14.50,'GI Lager');
insert into items values (1004,'Granville Island Honey Lager',0.15,0.30,14.50,'GI Honey');
insert into items values (1005,'Granville Island Winter Ale',0.15,0.30,14.50,'Lions Bay Winter Ale');
insert into items values (1006,'Like This',0.15,0.05,4.50,'Superflux Like This');
insert into items values (1007,'Like That',0.15,0.05,4.50,'Superflux Like That');
insert into items values (1008,'Driftwood Fat Tug',0.15,0.05,7.50,'Driftwood Fat Tug IPA');
insert into items values (1009,'Driftwood White Bark',0.15,0.05,7.50,'Driftwood White Bark Witbier');
insert into items values (1010,'Mission Hill 5 Vineyards',0.15,0.05,14.50,'MH5 Sauvignon Blanc');
insert into items values (1011,'Naked Grape Sauvignon Blanc',0.15,0.05,12.75,'NG Sauv Blanc');
insert into items values (1012,'Naked Grape Pinot Grigio',0.15,0.05,12.75,'NG PG');
insert into items values (1013,'Naked Grape Luscious Red',0.15,0.05,12.75,'NG Luscious Red');
insert into items values (1014,'Screw It Sauvignon Blanc',0.15,0.05,12.75,'Screw It Sauv Blanc');
insert into items values (1015,'Screw It Pinot Grigio',0.15,0.05,12.75,'Screw It PG');
insert into items values (1016,'Screw It Pinot Grigio 1.5L',0.15,0.10,24.50,'Screw It PG 1.5L');
insert into items values (1017,'Screw It Shiraz',0.15,0.05,12.75,'Screw It Shiraz');
insert into items values (1018,'Johhny Q Shiraz',0.15,0.05,22.70,'Johhny Q Shiraz');
insert into items values (1019,'JT Merlot',0.15,0.05,14.50,'Jackson Triggs Merlot');
insert into items values (1020,'JT Cabernet Sauvignon',0.15,0.05,14.50,'Jackson Triggs Cab Sauv');
insert into items values (1021,'Coke 2L',0.15,0.20,4.00,'Coke 2L');
insert into items values (1022,'Tonic Water 355mL',0.15,0.05,1.25,'Canada Dry Tonic Water');
insert into items values (1023,'Doritos',0.12,0.00,5.00,'Doritos Nacho Cheese Flavor');
insert into items values (1024,'Doritos Cool Ranch',0.12,0.00,5.00,'Doritos Cool Ranch');

commit;

insert into beers values (1000,'Budweiser','America',5.0,'Lager',355,6);
insert into beers values (1001,'Kokanee','America',5.0,'Lager',355,6);
insert into beers values (1002,'Granville Island','Vancouver,BC',5.0,'Pale Ale',355,6);
insert into beers values (1003,'Granville Island','Vancouver,BC',5.0,'Lager',355,6);
insert into beers values (1004,'Granville Island','Vancouver,BC',5.0,'Honey Lager',355,6);
insert into beers values (1005,'Granville Island','Vancouver,BC',5.0,'Specialty',355,6);
insert into beers values (1006,'Superflux','Vancouver,BC',5.5,'IPA',473,1);
insert into beers values (1007,'Superflux','Vancouver,BC',5.5,'IPA',473,1);
insert into beers values (1008,'Driftwood','Victoria,BC',7.0,'IPA',750,1);
insert into beers values (1009,'Driftwood','Victoria,BC',7.0,'Witbier',750,1);

commit;

insert into wines values (1010,'Mission Hill','Kelowna,BC',12.0,'White',750,'Sauvignon Blanc');
insert into wines values (1011,'Naked Grape','Ontario',12.0,'White',750,'Sauvignon Blanc');
insert into wines values (1012,'Naked Grape','Ontario',12.0,'White',750,'Pinot Grigio');
insert into wines values (1013,'Naked Grape','Ontario',12.0,'Red',750,'Red Blend');
insert into wines values (1014,'Screw It','West Coast',12.0,'White',750,'Sauvignon Blanc');
insert into wines values (1015,'Screw It','West Coast',12.0,'White',750,'Pinot Grigio');
insert into wines values (1016,'Screw It','West Coast',12.0,'White',1500,'Pinot Grigio');
insert into wines values (1017,'Screw It','Shiraz',12.0,'Red',750,'Shiraz');
insert into wines values (1018,'Johnny Q','Australia',12.0,'Red',750,'Shiraz');
insert into wines values (1019,'Jackson Triggs','Okanagan,BC',12.0,'Red',750,'Merlot');
insert into wines values (1020,'Jackson Triggs','Okanagan,BC',12.0,'Red',750,'Cabernet Sauvignon');

commit;

insert into storeitems values (1000,1,20);
insert into storeitems values (1001,1,20);
insert into storeitems values (1002,1,20);
insert into storeitems values (1003,1,18);
insert into storeitems values (1004,1,20);
insert into storeitems values (1005,1,20);
insert into storeitems values (1006,1,48);
insert into storeitems values (1007,1,48);
insert into storeitems values (1008,1,24);
insert into storeitems values (1009,1,24);
insert into storeitems values (1010,1,12);
insert into storeitems values (1011,1,23);
insert into storeitems values (1012,1,24);
insert into storeitems values (1013,1,24);
insert into storeitems values (1014,1,21);
insert into storeitems values (1015,1,24);
insert into storeitems values (1016,1,16);
insert into storeitems values (1017,1,27);
insert into storeitems values (1018,1,12);
insert into storeitems values (1019,1,24);
insert into storeitems values (1020,1,24);
insert into storeitems values (1021,1,4);
insert into storeitems values (1022,1,6);
insert into storeitems values (1023,1,5);
insert into storeitems values (1024,1,5);
insert into storeitems values (1000,2,20);
insert into storeitems values (1001,2,17);
insert into storeitems values (1002,2,20);
insert into storeitems values (1003,2,18);
insert into storeitems values (1004,2,35);
insert into storeitems values (1005,2,20);
insert into storeitems values (1006,2,36);
insert into storeitems values (1007,2,48);
insert into storeitems values (1008,2,45);
insert into storeitems values (1009,2,24);

commit;

insert into employees values (1,'John Manage','jmanage','password1',100.00,1,'Manager');
insert into employees values (3,'Michael C','mc','password3',70.00,1,'Clerk');
insert into employees values (2,'Jane Doe','jd','password2',70.00,1,'Clerk');
insert into employees values (4,'Tiffany Sales','tsales','password4',100.00,2,'Manager');
insert into employees values (5,'Butch Cassidy','bc','password5',70.00,2,'Clerk');
insert into employees values (6,'Sundance Kid','sk','password6',70.00,2,'Clerk');

commit;

insert into orders values (100,'Granville Island','2017-10-30 10:00','2017-10-31 11:00',1);
insert into orders values (101,'Superflux','2017-11-08 10:00',null,1);
insert into orders values (102,'Mission Hill Vineyards','2017-11-01 12:00','2017-11-05 10:00',1);
insert into orders values (103,'Jackson Triggs','2017-11-01 12:00','2017-11-05 10:00',1);
insert into orders values (104,'Kokanee','2017-11-01 10:00','2017-11-02 10:00',1);
insert into orders values (105,'Driftwood','2017-11-08 10:00','2017-11-10 12:00',1);
insert into orders values (106,'Naked Grape','2017-11-08 10:00','2017-11-10 12:00',1);
insert into orders values (107,'Granville Island','2017-11-12 10:00','2017-11-14 12:00',1);
insert into orders values (108,'Screw It','2017-11-15 10:00','2017-11-20 10:00',1);
insert into orders values (109,'Johnny Q','2017-11-08 10:00',null,1);
insert into orders values (110,'Superflux','2017-11-09 10:00','2017-11-11 10:00',2);
insert into orders values (111,'Granville Island','2017-11-02 10:00',null,2);
insert into orders values (112,'Mission Hill Vineyards','2017-11-01 12:00','2017-11-05 10:00',2);
insert into orders values (113,'Jackson Triggs','2017-11-01 12:00','2017-11-05 10:00',2);
insert into orders values (114,'Naked Grape','2017-11-08 10:00','2017-11-10 12:00',2);
insert into orders values (115,'Screw It','2017-11-15 10:00','2017-11-20 10:00',2);
insert into orders values (116,'Superflux','2017-11-08 10:00',null,2);

                           

commit;

insert into orderitems values (1002,100,4);
insert into orderitems values (1003,100,4);
insert into orderitems values (1004,100,4);
insert into orderitems values (1006,101,24);
insert into orderitems values (1007,101,24);
insert into orderitems values (1010,102,12);
insert into orderitems values (1019,103,12);
insert into orderitems values (1020,103,12);
insert into orderitems values (1001,104,10);
insert into orderitems values (1008,105,12);
insert into orderitems values (1009,105,12);
insert into orderitems values (1011,106,12);
insert into orderitems values (1012,106,12);
insert into orderitems values (1013,106,12);
insert into orderitems values (1002,107,4);
insert into orderitems values (1003,107,4);
insert into orderitems values (1004,107,4);
insert into orderitems values (1014,108,12);
insert into orderitems values (1015,108,12);
insert into orderitems values (1016,108,6);
insert into orderitems values (1017,108,12);
insert into orderitems values (1018,109,12);
insert into orderitems values (1006,110,24);
insert into orderitems values (1007,110,24);
insert into orderitems values (1002,111,8);
insert into orderitems values (1003,111,8);
insert into orderitems values (1004,111,8);
insert into orderitems values (1010,112,12);
insert into orderitems values (1019,113,12);
insert into orderitems values (1011,114,12);
insert into orderitems values (1012,114,24);
insert into orderitems values (1013,114,12);
insert into orderitems values (1014,115,12);
insert into orderitems values (1015,115,12);
insert into orderitems values (1006,116,24);
insert into orderitems values (1007,116,24);


commit;

insert into store_sales values (100,14.10,'Credit','2017-11-05 12:30',3);
insert into store_sales values (101,26.03,'Debit','2017-11-05 12:35',3);
insert into store_sales values (102,29.43,'Debit','2017-11-05 12:50',3);
insert into store_sales values (103,16.05,'Cash','2017-11-05 13:00',3);

commit;

insert into saleitems values (1000,100,1);
insert into saleitems values (1008,101,3);
insert into saleitems values (1017,102,2);
insert into saleitems values (1006,103,1);
insert into saleitems values (1007,103,1);
insert into saleitems values (1023,103,1);

commit;

insert into reports values (1,'2017-11-05','2017-11-05',1,85.61,0.00,170.00);





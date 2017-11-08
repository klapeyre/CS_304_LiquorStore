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
insert into items values (1022,'Tonic Water',0.15,0.05,1.25,'Canada Dry Tonic Water');
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



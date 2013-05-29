###########DATABASE BETAAPPS################


create table user_group(
id int not null,
name character varying(255),
description character varying(1024),
constraint pk_user_group_id primary key(id));


create table admin_channel(
id bigint not null,
user_group_id int not null,
login character varying (255) not null,
pwd character varying (255) not null,
address character varying(255),
additional_info1 character varying(255),
additional_info2 character varying(255),
additional_info3 character varying(255),
constraint pk_admin_channel primary key(id),
constraint fk_user_group_id foreign key(user_group_id) references user_group(id));

create table merchant(
id bigint not null,
name character varying(255),
inquiry_url character varying(2048),
payment_url character varying(2048),
plugin character varying(2048),
status boolean,
constraint pk_merchant_id primary key(id));

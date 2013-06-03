###########DATABASE BETAAPPS################


create table user_group(
id int not null,
name character varying(255),
description character varying(1024),
constraint pk_user_group_id primary key(id));


CREATE SEQUENCE seq_user_group_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


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


CREATE SEQUENCE seq_admin_channel_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

create table merchant(
id bigint not null,
name character varying(255),
inquiry_url character varying(2048),
payment_url character varying(2048),
plugin character varying(2048),
status boolean,
constraint pk_merchant_id primary key(id));



CREATE SEQUENCE seq_merchant_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


create table transactions(
id bigint not null,
merchant_id bigint not null,
admin_channel_id bigint not null,
type character varying(3),
terminal character varying(30),
customer character varying(30),
datetime timestamp,
status character varying(1),
state character varying(1),
sequence_id character varying(7),
amount numeric(12,2),
co_amount numeric(12,2),
promo character varying(1),
transaction_id character varying(17),
constraint pk_transcation_id primary key(id),
constraint fk_merchant_id foreign key (merchant_id) references merchant(id),
constraint fk_admin_channel_id foreign key(admin_channel_id) references admin_channel(id));


CREATE SEQUENCE seq_transactions_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


create table transaction_log(
id bigint not null,
transaction_id bigint not null,
status character varying(1),
datetime timestamp,
constraint pk_transaction_log_id primary key(id),
constraint fk_transaction_id foreign key(transaction_id) references transactions(id)
);


CREATE SEQUENCE seq_transaction_log_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




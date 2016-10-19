/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/10/20 0:04:47                           */
/*==============================================================*/


drop table if exists tb_camps;

drop table if exists tb_camps_category_relation;

drop table if exists tb_camps_trace;

drop table if exists tb_category;

drop table if exists tb_coupon_code;

drop table if exists tb_logic_order;

drop table if exists tb_message;

drop table if exists tb_news;

drop table if exists tb_order;

drop table if exists tb_order_person;

drop table if exists tb_surround_product;

drop table if exists tb_user_country_relation;

drop table if exists tb_user_info;

/*==============================================================*/
/* Table: tb_camps                                              */
/*==============================================================*/
create table tb_camps
(
   camps_id             varchar(30) not null,
   camps_name           varchar(100),
   camps_title          varchar(200),
   camps_desc           varchar(300),
   camps_images         varchar(500),
   camps_locale         varchar(100),
   oriented_people      varchar(200),
   feature              varchar(200),
   service_support      varchar(200),
   course_desc          varchar(500),
   activity_desc        varchar(500),
   camps_food_desc      varchar(500),
   camps_foods_photos   varchar(500),
   camps_hotel_desc     varchar(500),
   camps_hotel_photos   varchar(500),
   fee_desc             varchar(300),
   status               int,
   total_price          decimal(10,2),
   done_count           decimal(10),
   deadline_date        date,
   departure_date       date,
   start_date           date,
   duration_time        datetime,
   create_time          datetime,
   update_time          datetime,
   primary key (camps_id)
)
type = InnoDB;

alter table tb_camps comment '活动营地表';

/*==============================================================*/
/* Table: tb_camps_category_relation                            */
/*==============================================================*/
create table tb_camps_category_relation
(
   camps_id             varchar(30),
   category_id          varchar(30)
)
type = InnoDB;

alter table tb_camps_category_relation comment '营地分类关联表';

/*==============================================================*/
/* Table: tb_camps_trace                                        */
/*==============================================================*/
create table tb_camps_trace
(
   trace_id             varchar(30) not null,
   campus_id            varchar(30),
   trace_name           varchar(100),
   trace_desc           varchar(500),
   trace_weight         int,
   trace_photos         varchar(500),
   primary key (trace_id)
)
type = InnoDB;

alter table tb_camps_trace comment '营地行程';

/*==============================================================*/
/* Table: tb_category                                           */
/*==============================================================*/
create table tb_category
(
   category_id          varchar(30) not null,
   category_name        varchar(50),
   category_url         varchar(255),
   category_type        int,
   primary key (category_id)
)
type = InnoDB;

alter table tb_category comment '分类表';

/*==============================================================*/
/* Table: tb_coupon_code                                        */
/*==============================================================*/
create table tb_coupon_code
(
   code_id              varchar(30) not null,
   code_value           varchar(30),
   code_amount          decimal(10,2),
   create_time          datetime,
   start_time           datetime,
   end_time             datetime,
   status               int,
   use_count            int,
   category_ids         varchar(500),
   primary key (code_id)
)
type = InnoDB;

alter table tb_coupon_code comment '兑换码表';

/*==============================================================*/
/* Table: tb_logic_order                                        */
/*==============================================================*/
create table tb_logic_order
(
   logic_order_id       varchar(30) not null,
   account_id           varchar(30),
   create_time          datetime,
   update_time          datetime,
   order_ip             varchar(30),
   pay_type             int,
   pay_status           int,
   pay_time             datetime,
   platform_order_id    varchar(30),
   primary key (logic_order_id)
)
type = InnoDB;

alter table tb_logic_order comment '逻辑订单';

/*==============================================================*/
/* Table: tb_message                                            */
/*==============================================================*/
create table tb_message
(
   message_id           varchar(30) not null,
   account_id           varchar(30),
   read_status          int,
   message_title        varchar(200),
   message_content      varchar(500),
   create_time          datetime,
   update_time          datetime,
   primary key (message_id)
)
type = InnoDB;

alter table tb_message comment '个人消息';

/*==============================================================*/
/* Table: tb_news                                               */
/*==============================================================*/
create table tb_news
(
   news_id              varchar(30) not null,
   news_title           varchar(100),
   news_content         varchar(500),
   create_time          datetime,
   update_time          datetime,
   primary key (news_id)
)
type = InnoDB;

alter table tb_news comment '咨询';

/*==============================================================*/
/* Table: tb_order                                              */
/*==============================================================*/
create table tb_order
(
   order_id             varchar(30) not null,
   account_id           varchar(30),
   logic_order_id       varchar(30),
   code_id              varchar(30),
   status               int,
   creat_time           datetime,
   update_time          datetime,
   camps_id             varchar(30),
   total_price          decimal(10,2),
   code_price           decimal(10,2),
   code_status          int,
   pay_price            decimal(10,2),
   total_count          int,
   contact_name         varchar(50),
   contact_email        varchar(50),
   contact_phone        varchar(30),
   order_ip             varchar(30),
   primary key (order_id)
)
type = InnoDB;

alter table tb_order comment '订单表';

/*==============================================================*/
/* Table: tb_order_person                                       */
/*==============================================================*/
create table tb_order_person
(
   order_id             varchar(30),
   person_name          varchar(50),
   person_phone         varchar(30),
   person_idno          varchar(30),
   person_address       varchar(200)
)
type = InnoDB;

alter table tb_order_person comment '出行人信息';

/*==============================================================*/
/* Table: tb_surround_product                                   */
/*==============================================================*/
create table tb_surround_product
(
   product_id           varchar(30) not null,
   product_name         varchar(100),
   product_desc         varchar(500),
   product_photos       varchar(500),
   product_price        decimal(10,2),
   primary key (product_id)
)
type = InnoDB;

alter table tb_surround_product comment '周边产品';

/*==============================================================*/
/* Table: tb_user_country_relation                              */
/*==============================================================*/
create table tb_user_country_relation
(
   account_id           varchar(30),
   category_id          varchar(30)
)
type = InnoDB;

alter table tb_user_country_relation comment '用户喜好';

/*==============================================================*/
/* Table: tb_user_info                                          */
/*==============================================================*/
create table tb_user_info
(
   account_id           varchar(20) not null,
   nick_name            varchar(30),
   email                varchar(80),
   email_active_status  int,
   mobile               varchar(20),
   gender               int,
   create_ip            varchar(20),
   create_time          datetime,
   birth_time           datetime,
   credit               decimal(10,2),
   photo_url            varchar(200),
   update_time          datetime,
   primary key (account_id)
)
type = InnoDB;

alter table tb_user_info comment '用户信息表';


CREATE TABLE  tb_user  (
   username  char(50) NOT NULL,
   password  char(50) NOT NULL,
   remark  char(100) ,
   name  char(50),
  PRIMARY KEY ( username )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  tb_power  (
   power_id  char(50) NOT NULL,
   power_name  char(50) NOT NULL,
   power_url char(100),
   is_menu int(1) default 0 not null,
   PRIMARY KEY ( power_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_user_power_relation (
   username  char(50) NOT NULL,
   power_id  char(50) NOT NULL,
  UNIQUE KEY  user_power_relation_index  ( userName , power_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
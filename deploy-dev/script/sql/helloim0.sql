create database if not exists helloim0;

use helloim0;

create table im_group
(
    group_id bigint(20) not null default 0 comment 'group_id',
    primary key (group_id)
) engine InnoDB
  default charset utf8mb4 comment '群聊信息';

create table im_group_user
(
    id       bigint(20) not null default 0 comment 'id',
    uid      bigint(20) not null default 0 comment 'uid',
    group_id bigint(20) not null default 0 comment 'groupid',
    primary key (id)
) engine InnoDB
  default charset utf8mb4 comment '群聊用户';


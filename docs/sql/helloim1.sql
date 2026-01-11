create database if not exists helloim1;

use helloim1;

create table if not exists im_chat
(
    id               bigint(20) auto_increment comment '主键id',
    user_id          bigint(20) not null default 0 comment 'userId',
    chat_id          bigint(20) not null default 0 comment '会话id',
    chat_type        tinyint(4) not null default 0 comment '会话类型',
    chat_top         tinyint(4) not null default 0 comment '会话置顶',
    chat_del         tinyint(4) not null default 0 comment '会话删除',
    update_timestamp timestamp  not null default current_timestamp comment '会话的更新时间',
    del_timestamp    timestamp  not null default current_timestamp comment '会话的删除时间',
    chat_mute        tinyint(4) not null default 0 comment '会话静默: 1-静默',
    sub_status           tinyint(4) not null default 0 comment '会话子状态',
    join_group_timestamp timestamp  not null default current_timestamp comment '加入群聊的时间',
    created_at       timestamp  not null default current_timestamp comment '创建时间',
    updated_at       timestamp  not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique index uniq_idx_uid_chat (user_id, chat_id)
) engine InnoDB
  default charset utf8mb4 comment '会话信息表';


create table if not exists im_message
(
    msg_id         bigint(20)     not null default 0 comment '消息ID,雪花算法',
    msg_from       bigint(20)     not null default 0 comment '消息发送者',
    from_user_type int(4)         not null default 0 comment '发送者用户类型',
    msg_to         bigint(20)     not null default 0 comment '消息接收者',
    to_user_type   int(4)         not null default 0 comment '接收者用户类型类型',
    msg_seq        int(11)        not null default 0 comment '客户端seq',
    msg_content    varchar(10000) not null default '' comment '消息内容',
    content_type   int(11)        not null default 0 comment '消息类型',
    cmd_id         int(11)        not null default 0 comment '信令号',
    send_time      timestamp      not null default current_timestamp comment '消息的发送时间',
    server_seq     bigint(20)     not null default 0 comment '服务端seq',
    receipt_status tinyint(4)     not null default 0 comment '已读回执状态: 0-未读, 1-已读',
    created_at     timestamp      not null default current_timestamp comment '创建时间',
    updated_at     timestamp      not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (msg_id),
    index idx_from_to (msg_from, msg_to, server_seq) comment '查询消息的场景'
) engine InnoDB
  default charset utf8mb4 comment '单聊消息表';


create table if not exists im_message_group
(
    msg_id         bigint(20)     not null default 0 comment '消息id',
    msg_from       bigint(20)     not null default 0 comment '消息发送者',
    from_user_type int(4)         not null default 0 comment '发送者的用户类型',
    group_id       bigint(20)     not null default 0 comment '群聊Id',
    msg_seq        int(11)        not null default 0 comment '客户端seq',
    msg_content    varchar(10000) not null default 0 comment '消息内容',
    content_type   int(11)        not null default 0 comment '消息类型',
    cmd_id         int(11)        not null default 0 comment '信令号',
    send_time      timestamp      not null default current_timestamp comment '发送时间',
    server_seq     bigint(20)     not null default 0 comment '服务端seq',
    created_at     timestamp      not null default current_timestamp comment '创建时间',
    updated_at     timestamp      not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (msg_id),
    index idx_group_msg_send (group_id, msg_id, send_time),
    index idx_group_from (group_id, msg_from)
) engine InnoDB
  default charset utf8mb4 comment '群聊消息表';
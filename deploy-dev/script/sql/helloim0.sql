create database if not exists helloim0;

use helloim0;


create table if not exists im_user
(
    user_id     bigint(20)   not null default 0 comment 'user_id, 使用雪花算法',
    user_type   int(4)       not null default 0 comment '用户类型',
    user_name   varchar(256) not null default '' comment '用户名称',
    icon        varchar(256) not null default '' comment '头像',
    mobile      varchar(50)  not null default '' comment '加密手机号',
    device_id   varchar(50)  not null default '' comment '设备ID',
    extra       varchar(512) not null default '' comment '扩展属性',
    user_status tinyint(4)   not null default 0 comment '用户状态: 0-正常, 1-禁用',
    created_at  timestamp    not null default current_timestamp comment '创建时间',
    updated_at  timestamp    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (user_id),
    unique index uniq_idx_user_id (user_id) comment 'IM侧userId的唯一索引'
) engine InnoDB
  default charset utf8mb4 comment 'IM用户信息表';

create table if not exists im_group
(
    group_id         bigint(20)    not null default 0 comment '群聊ID,使用雪花算法',
    group_name       varchar(256)  not null default '' comment '群聊名称',
    group_icon       varchar(256)  not null default '' comment '群头像',
    create_user      bigint(20)    not null default 0 comment '群聊创建者的userId',
    create_user_type int(4)    not null default 0 comment '创建群聊的用户的用户类型',
    owner_user       bigint(20)    not null default 0 comment '群主的userId',
    owner_user_type  int(4)    not null default 0 comment '群主的用户类型',
    announcement     varchar(4000) not null default '' comment '群公告',
    atall_enable     tinyint(4)    not null default 0 comment '是否开启@所有人',
    mute_status      tinyint(4)    not null default 0 comment '禁言状态：0-未禁言, 1-禁言',
    group_type       tinyint(4)    not null default 0 comment '群聊类型',
    group_version    bigint(20)    not null default 0 comment '群版本号',
    extra            varchar(512)  not null default '' comment '扩展信息',
    created_at       timestamp     not null default current_timestamp comment '创建时间',
    updated_at       timestamp     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (group_id),
    index idx_group_id (group_id) comment '群聊ID'
) engine InnoDB
  default charset utf8mb4 comment '群聊信息';

create table if not exists im_group_user
(
    group_id    bigint(20)   not null default 0 comment 'group_id',
    group_name  varchar(256) not null default '' comment '群聊名称',
    group_icon  varchar(256) not null default '' comment '群头像',
    user_id     bigint(20)   not null default 0 comment 'user_id',
    user_type   int(4)   not null default 0 comment '用户类型',
    nick_name   varchar(256) not null default '' comment '群昵称',
    join_time   timestamp    not null default current_timestamp comment '入群时间',
    exit_time   timestamp    not null default current_timestamp comment '退群时间',
    role        int(4)       not null default 0 comment '用户角色: 0-群成员, 1-群主',
    mute_status int(4)       not null default 0 comment '群成员禁言状态: 1-禁言',
    del_status  tinyint(4)   not null default 0 comment '逻辑删除',
    created_at  timestamp    not null default current_timestamp comment '创建时间',
    updated_at  timestamp    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (group_id, user_id, user_type)
) engine InnoDB
  default charset utf8mb4 comment '群成员表';

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


insert into im_user(user_id, user_type, user_name)
values
    (1, 0, 'user1'),
    (2, 0, 'user2'),
    (3, 0, 'user3'),
    (4, 0, 'user4'),
    (5, 0, 'user5'),
    (6, 0, 'user6'),
    (7, 0, 'user7'),
    (8, 0, 'user8'),
    (9, 0, 'user9'),
    (10, 0, 'user10'),
    (11, 0, 'user11'),
    (12, 0, 'user12'),
    (13, 0, 'user13'),
    (14, 0, 'user14'),
    (15, 0, 'user15');
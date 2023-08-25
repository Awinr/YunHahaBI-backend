-- 创建库
create database if not exists db_bi;

-- 切换库
use db_bi;

-- 用户表
create table if not exists user
(
    id          bigint auto_increment comment 'id' primary key,
    username    varchar(128)                          not null comment '账号',
    password    varchar(128)                          not null comment '密码',
    realName    varchar(128)                          not null comment '真实姓名',
    userAvatar  varchar(256)                          null comment '用户头像',
    userProfile varchar(256)                          null comment '用户简介',
    userRole    varchar(10) default 'user'            not null comment '用户角色：user/admin/ban',
    accessKey   varchar(128)                          null comment 'accessKey',
    sercetKey   varchar(128)                          null comment 'sercetKey',
    createTime  datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint     default 0                 not null comment '是否删除',
    index idx_unionId (username)
) comment '用户' collate = utf8mb4_unicode_ci;

-- admin/admin123
INSERT INTO user (username, password, realName, userRole, userAvatar, accessKey, sercetKey)
VALUES ('admin', 'a82948e42bddade00955bde2b13c4058', '超级管理员', 'admin',
        'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
        'a82948e42bddade00955bde2b13c4058', 'a82948e42bddade00955bde2b13c4058');

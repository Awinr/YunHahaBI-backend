-- 数据字典表
create table if not exists dict
(
    id         bigint auto_increment comment 'id' primary key,
    dictType   varchar(20) null comment '字典类型',
    dictName   varchar(50) null comment '字典名称',
    dictLabel  varchar(20) null comment '字典label（中文）',
    dictValue  varchar(50) null comment '字典值',
    dictSort   int         not null default 0 comment '排序号',
    remark     varchar(50) null comment '备注',
    fid        bigint      not null default 0 comment '父类id',
    status     varchar(10) not null default 'enable' comment 'enable,disable',
    userId     bigint      null comment '创建用户 id',
    createTime datetime             default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime             default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint              default 0 not null comment '是否删除'
) comment '数据字典表' collate = utf8mb4_unicode_ci;

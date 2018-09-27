
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_base
-- 用户基础信息表
-- by Tommy
-- ----------------------------
-- DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `id` bigint NOT NULL COMMENT '用户id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `mobile` varchar(20) NOT NULL COMMENT '手机号码',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `wx_openid` varchar(50) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT NULL COMMENT '微信OpenID',
  `wx_unionid` varchar(50) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT NULL COMMENT '微信UnionID',
  `user_password` varchar(50) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT NULL COMMENT '用户密码MD5',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
  `icon_url` varchar(255) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT NULL COMMENT '头像链接地址',
  `user_state` smallint(6) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `user_status` smallint(6) NOT NULL DEFAULT '0' COMMENT '用户等级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mobile` (`mobile`) COMMENT '手机号索引',
  UNIQUE KEY `uk_user_name` (`user_name`) COMMENT '用户名索引',
  UNIQUE KEY `uk_email` (`email`) COMMENT '电子邮箱索引',
  UNIQUE KEY `uk_wx_openid` (`wx_openid`) COMMENT '微信OpenID索引',
  UNIQUE KEY `uk_wx_unionid` (`wx_unionid`) COMMENT '微信UnionID索引',
  KEY `idx_user_password` (`user_password`(20)) COMMENT '用户密码索引',
  KEY `idx_nick_name` (`nick_name`(20)) COMMENT '用户昵称索引',
  KEY `idx_user_state` (`user_state`) COMMENT '用户状态索引',
  KEY `idx_user_status` (`user_status`) COMMENT '用户等级索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础信息表';

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- 用户基础信息表
-- by Tommy
-- ----------------------------
-- DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
    `id` bigint(20) NOT NULL COMMENT '用户id',
    `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
    `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
    `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `user_password` varchar(50) CHARACTER SET ascii COLLATE ascii_general_ci NULL DEFAULT NULL COMMENT '用户密码MD5',
    `user_state` smallint(6) NOT NULL DEFAULT 0 COMMENT '用户状态',
    `password_modified` datetime(0) NOT NULL COMMENT '用户修改密码的时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_mobile`(`mobile`) USING BTREE COMMENT '手机号索引',
    UNIQUE INDEX `uk_user_name`(`user_name`) USING BTREE COMMENT '用户名索引',
    INDEX `idx_user_password`(`user_password`(20)) USING BTREE COMMENT '用户密码索引',
    INDEX `idx_user_state`(`user_state`) USING BTREE COMMENT '用户状态索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基础信息表' ROW_FORMAT = Dynamic;
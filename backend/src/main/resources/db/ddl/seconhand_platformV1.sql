/*
 Navicat Premium Dump SQL

 Source Server         : my connection
 Source Server Type    : MySQL
 Source Server Version : 80041 (8.0.41)
 Source Host           : localhost:3306
 Source Schema         : seconhand_platform

 Target Server Type    : MySQL
 Target Server Version : 80041 (8.0.41)
 File Encoding         : 65001

 Date: 15/05/2026 15:41:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity_banner
-- ----------------------------
DROP TABLE IF EXISTS `activity_banner`;
CREATE TABLE `activity_banner`  (
  `banner_id` bigint NOT NULL AUTO_INCREMENT COMMENT '海报ID',
  `banner_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动标题',
  `banner_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '海报图片URL',
  `banner_link` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跳转链接（可选）',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间（必填）',
  `is_active` tinyint NOT NULL DEFAULT 1 COMMENT '是否启用：1-启用 0-禁用',
  PRIMARY KEY (`banner_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动轮播海报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_banner
-- ----------------------------
INSERT INTO `activity_banner` VALUES (1, '开学季数码专场', 'https://picsum.photos/id/1076/1920/400', '/product/list', '2026-04-11 02:00:00', '2026-07-01 02:00:00', '2026-04-11 18:56:07', 1);
INSERT INTO `activity_banner` VALUES (2, '毕业季闲置特惠', 'https://picsum.photos/id/1077/1920/400', '/product/list', '2026-04-11 02:00:00', '2026-07-01 02:00:00', '2026-04-11 18:56:07', 1);
INSERT INTO `activity_banner` VALUES (3, '校园二手交易活动', 'https://picsum.photos/id/1078/1920/400', '/product/list', '2026-04-11 02:00:00', '2026-07-01 02:00:00', '2026-04-11 18:56:07', 1);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `admin_id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `role` tinyint NOT NULL DEFAULT 1 COMMENT '角色：1-普通管理员 2-超级管理员',
  `admin_status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-正常 2-禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 'admin', '123', '温涛', NULL, 1, 1, '2026-04-14 17:59:03', '2026-04-17 16:47:27');

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `brand_id` int NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
  `brand_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名称',
  `brand_logo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌LOGO',
  `sort` int NULL DEFAULT 0 COMMENT '展示排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`brand_id`) USING BTREE,
  UNIQUE INDEX `idx_brand_name`(`brand_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品牌表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (2, '华为', '/upload/brand/huawei.jpg', 0, '2026-04-13 17:03:21');
INSERT INTO `brand` VALUES (3, 'Apple', '/upload/brand/apple.png', 0, '2026-04-14 14:43:30');
INSERT INTO `brand` VALUES (5, '乐视', '', 0, '2026-05-05 19:46:23');

-- ----------------------------
-- Table structure for category_brand_relation
-- ----------------------------
DROP TABLE IF EXISTS `category_brand_relation`;
CREATE TABLE `category_brand_relation`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `category_id` int NOT NULL COMMENT '品类ID',
  `brand_id` int NOT NULL COMMENT '品牌ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_brand`(`category_id` ASC, `brand_id` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_brand_id`(`brand_id` ASC) USING BTREE,
  CONSTRAINT `category_brand_relation_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`category_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `category_brand_relation_ibfk_2` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`brand_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '品类-品牌关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category_brand_relation
-- ----------------------------
INSERT INTO `category_brand_relation` VALUES (10, 3, 2, '2026-04-14 16:10:23');
INSERT INTO `category_brand_relation` VALUES (12, 3, 3, '2026-04-14 22:23:50');

-- ----------------------------
-- Table structure for chat_contact
-- ----------------------------
DROP TABLE IF EXISTS `chat_contact`;
CREATE TABLE `chat_contact`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_one` bigint NOT NULL COMMENT '当前用户ID',
  `user_two` bigint NOT NULL COMMENT '对方用户ID',
  `last_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后一条消息',
  `status` int NULL DEFAULT 1 COMMENT '1正常 0删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_pair`(`user_one` ASC, `user_two` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '聊天会话表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_contact
-- ----------------------------
INSERT INTO `chat_contact` VALUES (1, 2, 3, 'hey', 1, '2026-04-29 03:11:22', '2026-05-04 04:44:24');
INSERT INTO `chat_contact` VALUES (23, 3, 6, 'thanks, i i\'ll memorize you 10 lives🐣🐣', 1, '2026-05-02 09:02:33', '2026-05-05 07:01:49');
INSERT INTO `chat_contact` VALUES (24, 2, 6, NULL, 1, '2026-05-02 09:02:59', '2026-05-02 09:02:59');
INSERT INTO `chat_contact` VALUES (25, 3, 3, '干嘛', 1, '2026-05-05 22:28:38', '2026-05-06 00:31:42');

-- ----------------------------
-- Table structure for chat_message
-- ----------------------------
DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `contact_id` bigint NOT NULL COMMENT '会话ID',
  `from_user_id` bigint NOT NULL COMMENT '发送人ID',
  `to_user_id` bigint NOT NULL COMMENT '接收人ID',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2051701382306557955 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '聊天消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_message
-- ----------------------------
INSERT INTO `chat_message` VALUES (2051034640395751426, 23, 6, 3, 'hi, what\'s your name?', '2026-05-04 04:22:19', '2026-05-04 04:22:19');
INSERT INTO `chat_message` VALUES (2051034700009394177, 23, 3, 6, 'my name\'s wentao', '2026-05-04 04:22:33', '2026-05-04 04:22:33');
INSERT INTO `chat_message` VALUES (2051034802933420034, 1, 3, 2, 'i loveyou', '2026-05-04 04:22:58', '2026-05-04 04:22:58');
INSERT INTO `chat_message` VALUES (2051037489657741314, 1, 3, 2, 'fuck off', '2026-05-04 04:33:38', '2026-05-04 04:33:38');
INSERT INTO `chat_message` VALUES (2051037619836354562, 1, 2, 3, 'ok', '2026-05-04 04:34:09', '2026-05-04 04:34:09');
INSERT INTO `chat_message` VALUES (2051040197328437249, 1, 3, 2, 'hey', '2026-05-04 04:44:24', '2026-05-04 04:44:24');
INSERT INTO `chat_message` VALUES (2051435930200276994, 23, 3, 6, 'i am very good to see u', '2026-05-05 06:56:54', '2026-05-05 06:56:54');
INSERT INTO `chat_message` VALUES (2051436499346358273, 23, 6, 3, 'Can you lend me some cash? for like.. 2 Billions?🥰🥰', '2026-05-05 06:59:12', '2026-05-05 06:59:09');
INSERT INTO `chat_message` VALUES (2051436771175006209, 23, 3, 6, 'ok, here you are>>😶‍🌫️', '2026-05-05 07:00:14', '2026-05-05 07:00:14');
INSERT INTO `chat_message` VALUES (2051437167322824705, 23, 6, 3, 'thanks, i i\'ll memorize you 10 lives🐣🐣', '2026-05-05 07:01:51', '2026-05-05 07:01:49');
INSERT INTO `chat_message` VALUES (2051699764852912130, 25, 3, 3, 'het', '2026-05-06 00:25:17', '2026-05-06 00:25:17');
INSERT INTO `chat_message` VALUES (2051700142680010753, 25, 3, 3, '吃', '2026-05-06 00:26:47', '2026-05-06 00:26:47');
INSERT INTO `chat_message` VALUES (2051701382306557954, 25, 3, 3, '干嘛', '2026-05-06 00:31:42', '2026-05-06 00:31:42');

-- ----------------------------
-- Table structure for delivery_address
-- ----------------------------
DROP TABLE IF EXISTS `delivery_address`;
CREATE TABLE `delivery_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单ID',
  `school_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校内地址',
  `detail_address` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发货地址记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of delivery_address
-- ----------------------------
INSERT INTO `delivery_address` VALUES (1, 67, 'as', 'sa', NULL, NULL);

-- ----------------------------
-- Table structure for delivery_address_for_display
-- ----------------------------
DROP TABLE IF EXISTS `delivery_address_for_display`;
CREATE TABLE `delivery_address_for_display`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `school_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '校内地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发货地址记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of delivery_address_for_display
-- ----------------------------
INSERT INTO `delivery_address_for_display` VALUES (2, 'as', '2026-05-13 11:15:39', '2026-05-13 11:15:39');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '新闻ID，自增',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '新闻标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '新闻内容',
  `create_user_id` int NOT NULL COMMENT '创建用户id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` int NOT NULL COMMENT '1展示0不展示',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (2, '4.26更新', '更新了页面布局', 3, '2026-04-26 00:30:47', 1, NULL);
INSERT INTO `news` VALUES (3, 'dd', 'dddd', 3, '2026-04-26 00:55:37', 1, 'admin');
INSERT INTO `news` VALUES (4, '张雪机车车手德比斯退赛', '当地时间5月2日，在2026世界超级摩托车锦标赛（WSBK）匈牙利站WorldSSP组别第一回合正赛中，中国摩托车制造商“张雪机车”的法国车手瓦伦丁·德比斯在最后一圈上演精彩逆转，一举超越前两位车手，夺得冠军。3日举行的第二回合正赛中，德比斯因机械故障选择退赛。当天，瓦伦丁·德比斯接受了记者采访。', 3, '2026-05-05 06:36:27', 1, 'admin');
INSERT INTO `news` VALUES (5, '奶茶卖到188元/杯？🥲😅', '蓝鲸新闻5月3日讯（记者 赵凯）奶茶价格的天花板究竟在哪里？\n最近这个问题又有了新的答案。\n5月1日，一家名为“蘭誦茶事”在北京首店开业，菜单中单价188元一杯的奶茶再次成为了舆论热议的焦点。\n什么样的奶茶卖到188元一杯？\n近日，不少网友晒单在北京喝到了188元一杯的奶茶。蓝鲸新闻记者查询发现，这款单价创新高的奶茶是奶茶品牌“蘭誦茶事”所推出的“东方至臻”系列，该系列共有三款，其中两款为188元，一款为168元。', 3, '2026-05-05 06:38:13', 1, 'admin');
INSERT INTO `news` VALUES (6, '国乒男团为何输给瑞典队？🍛🍖🥩', '伦敦世乒赛小组赛A阶段，国乒男团2-3负于瑞典队，遭遇小组赛两连败。各盘比分为王楚钦3-0安东·卡尔伯格；林诗栋2-3伊莱亚斯·兰弗利（9-11，6-11，11-3，11-6，9-11）；梁靖崑2-3莫雷加德（7-11，11-9，9-11，11-3，10-12）；王楚钦3-0伊莱亚斯·兰弗利（12-10，11-6，11-4）；林诗栋1-3安东·卡尔伯格（10-12，12-10，8-11，8-11）。', 3, '2026-05-05 06:40:30', 1, 'admin');

-- ----------------------------
-- Table structure for order_comment
-- ----------------------------
DROP TABLE IF EXISTS `order_comment`;
CREATE TABLE `order_comment`  (
  `comment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `user_id` bigint NOT NULL COMMENT '评价用户ID',
  `seller_id` bigint NOT NULL COMMENT '被评价卖家ID',
  `score` tinyint NOT NULL DEFAULT 5 COMMENT '评分：1-5星',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评价内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`) USING BTREE,
  UNIQUE INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `order_comment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_main` (`order_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_comment
-- ----------------------------
INSERT INTO `order_comment` VALUES (1, 54, 75, 3, 3, 5, '很好的卖家', '2026-05-06 00:15:27');

-- ----------------------------
-- Table structure for order_main
-- ----------------------------
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '买家ID',
  `seller_id` bigint NOT NULL COMMENT '卖家ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称（快照）',
  `product_price` decimal(10, 2) NOT NULL COMMENT '成交价格（快照）',
  `order_status` tinyint NOT NULL DEFAULT 1 COMMENT '订单状态：1-待付款 2-待发货 3-待收货 4-已完成 5-已取消',
  `pay_type` tinyint NULL DEFAULT NULL COMMENT '支付方式：1-微信 2-支付宝',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '付款时间',
  `delivery_type` tinyint NULL DEFAULT NULL COMMENT '配送方式：1-快递 2-自提',
  `delivery_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快递单号/自提码',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE INDEX `order_no`(`order_no` ASC) USING BTREE,
  INDEX `seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_user_seller`(`user_id` ASC, `seller_id` ASC) USING BTREE,
  CONSTRAINT `order_main_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_main_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `order_main_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_main
-- ----------------------------
INSERT INTO `order_main` VALUES (7, 'ORD-20260421023538-9335', 3, 2, 63, '111', 1.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-21 02:35:38', '2026-04-21 02:35:38');
INSERT INTO `order_main` VALUES (8, 'ORD-20260421023840-2384', 3, 2, 63, '111', 1.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-21 02:38:40', '2026-04-21 02:38:40');
INSERT INTO `order_main` VALUES (49, 'ORD-20260424024642-6936', 3, 2, 66, '小米13', 23.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-24 02:46:42', '2026-04-24 02:46:42');
INSERT INTO `order_main` VALUES (50, 'ORD-20260424024857-4676', 3, 2, 66, '小米13', 23.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-24 02:48:57', '2026-04-24 02:48:57');
INSERT INTO `order_main` VALUES (51, 'ORD-20260424025021-5608', 3, 2, 66, '小米13', 23.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-24 02:50:21', '2026-04-24 02:50:21');
INSERT INTO `order_main` VALUES (52, 'ORD-20260424025113-7424', 3, 2, 66, '小米13', 23.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-24 02:51:13', '2026-04-24 02:51:13');
INSERT INTO `order_main` VALUES (53, 'ORD-20260424025317-5297', 3, 2, 66, '小米13', 23.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-24 02:53:17', '2026-04-24 02:53:17');
INSERT INTO `order_main` VALUES (54, 'ORD-20260425132101-6682', 3, 3, 75, 'p1', 123.00, 4, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-25 13:21:01', '2026-05-04 06:21:24');
INSERT INTO `order_main` VALUES (60, 'ORD-20260428040650-2913', 3, 2, 76, '测试商品', 123.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-28 04:06:50', '2026-04-28 04:07:02');
INSERT INTO `order_main` VALUES (61, 'ORD-20260428040735-9348', 3, 2, 76, '测试商品', 123.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-04-28 04:07:35', '2026-04-28 04:07:35');
INSERT INTO `order_main` VALUES (62, 'ORD-20260505222841-7356', 3, 3, 78, '乐视显示器TSH2HZ', 245.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-05 22:28:41', '2026-05-05 22:28:41');
INSERT INTO `order_main` VALUES (63, 'ORD-20260505222900-9015', 3, 3, 78, '乐视显示器TSH2HZ', 245.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-05 22:29:00', '2026-05-05 22:29:00');
INSERT INTO `order_main` VALUES (64, 'ORD-20260505222904-4148', 3, 3, 78, '乐视显示器TSH2HZ', 245.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-05 22:29:04', '2026-05-05 22:29:04');
INSERT INTO `order_main` VALUES (65, 'ORD-20260505224952-5985', 3, 3, 75, 'p1', 123.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-05 22:49:52', '2026-05-05 22:49:52');
INSERT INTO `order_main` VALUES (66, 'ORD-20260506004851-8307', 3, 3, 78, '乐视显示器TSH2HZ', 245.00, 1, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-06 00:48:51', '2026-05-06 00:48:51');
INSERT INTO `order_main` VALUES (67, 'ORD-20260506005011-8294', 3, 3, 78, '乐视显示器TSH2HZ', 245.00, 3, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-06 00:50:11', '2026-05-13 12:51:55');
INSERT INTO `order_main` VALUES (68, 'ORD-20260506005257-4214', 3, 3, 75, 'p1', 123.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, '2026-05-06 00:52:57', '2026-05-06 00:53:06');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `seller_id` bigint NOT NULL COMMENT '卖家ID',
  `category_id` int NOT NULL COMMENT '品类ID',
  `brand_id` int NOT NULL COMMENT '品牌ID',
  `item_condition` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品成色',
  `functional_damage` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '功能损失点',
  `appearance_damage` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '外观损失点',
  `official_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '官方价',
  `selling_price` decimal(10, 2) NOT NULL COMMENT '售价',
  `product_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品描述',
  `product_status` tinyint NOT NULL DEFAULT 1 COMMENT '商品状态：1-上架 2-下架 3-已售出 4-审核中 5-驳回',
  `reject_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `collect_count` int NULL DEFAULT 0 COMMENT '收藏数',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `brand_id`(`brand_id` ASC) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_category_brand`(`category_id` ASC, `brand_id` ASC) USING BTREE,
  INDEX `idx_product_status`(`product_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (61, 'ds', 2, 1, 2, '全新', 'ds', 'ds', 232.00, 123.00, 'sa', 1, NULL, 3, 0, '2026-04-15 14:19:32', '2026-05-05 23:18:51');
INSERT INTO `product` VALUES (62, 'dw', 2, 1, 2, '全新', 'w', 'w', 34.00, 43.00, '323', 1, NULL, 3, 0, '2026-04-15 14:26:30', '2026-05-05 23:18:51');
INSERT INTO `product` VALUES (63, '111', 2, 2, 3, '全新', '无', 'no', 10000.00, 1.00, 'no', 3, NULL, 5, 0, '2026-04-15 15:10:53', '2026-05-05 23:42:53');
INSERT INTO `product` VALUES (64, '11', 2, 1, 3, '九成新', 'we', '', 12312.00, 32.00, 'ewq', 1, NULL, 3, 0, '2026-04-15 19:08:18', '2026-05-05 23:18:51');
INSERT INTO `product` VALUES (65, '33333', 2, 1, 2, '九成新', '3333', '3333', 3333.00, 33333.00, 'ddsd', 1, NULL, 3, 0, '2026-04-15 19:11:56', '2026-05-05 23:18:51');
INSERT INTO `product` VALUES (66, '小米13', 2, 1, 2, '九成新', '无', '无', 345.00, 23.00, '无', 1, NULL, 3, 0, '2026-04-16 10:49:41', '2026-05-05 23:18:51');
INSERT INTO `product` VALUES (74, 'New BIN', 3, 1, 2, '九成新', '', '', NULL, 380.00, '', 1, NULL, 5, 0, '2026-04-23 21:48:40', '2026-05-13 09:23:24');
INSERT INTO `product` VALUES (75, 'p1', 3, 2, 3, '全新', '', '', NULL, 123.00, '', 3, NULL, 11, 0, '2026-04-25 13:17:05', '2026-05-06 00:54:41');
INSERT INTO `product` VALUES (76, '测试商品', 2, 1, 3, '全新', '', '', NULL, 123.00, '', 3, NULL, 3, 0, '2026-04-28 03:14:25', '2026-05-05 23:18:51');
INSERT INTO `product` VALUES (77, '测试图片', 3, 1, 2, '全新', '', '', NULL, 111.00, '', 2, NULL, 12, 2, '2026-04-28 15:29:17', '2026-05-06 00:31:35');
INSERT INTO `product` VALUES (78, '乐视显示器TSH2HZ', 3, 1, 5, '九成新', '无', '右边框有一处磨损，对功能无影响。', 414.00, 245.00, '乐视 24英寸2K100hz\n超高清高刷台式电脑电竞游戏超薄显示器', 3, NULL, 9, 0, '2026-05-05 20:04:17', '2026-05-13 09:48:42');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '品类ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品类名称',
  `parent_id` int NULL DEFAULT 0 COMMENT '父品类ID（0=顶级）作用：归属',
  `level` int NOT NULL COMMENT '品类层级（1=顶级，2=二级）作用：层级',
  `sort` int NULL DEFAULT 0 COMMENT '展示排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1859092482 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (-1773174782, '床上用品', 4, 1, 0, '2026-05-05 06:32:37', '2026-05-05 06:32:37');
INSERT INTO `product_category` VALUES (-1253081086, '图书', -414220287, 1, 0, '2026-05-05 06:33:38', '2026-05-05 06:33:38');
INSERT INTO `product_category` VALUES (-1144029182, '食品', 0, 1, 0, '2026-05-05 06:32:02', '2026-05-05 06:32:02');
INSERT INTO `product_category` VALUES (-414220287, '纸质物品', 0, 1, 0, '2026-05-05 06:33:29', '2026-05-05 06:33:29');
INSERT INTO `product_category` VALUES (-292585471, '生活家电', 1, 1, 0, '2026-05-05 06:33:10', '2026-05-05 06:33:10');
INSERT INTO `product_category` VALUES (1, '电器', 0, 1, 0, '2026-04-13 16:51:33', '2026-04-13 16:51:33');
INSERT INTO `product_category` VALUES (2, '数码产品', 1, 2, 0, '2026-04-13 16:59:44', '2026-04-13 17:01:53');
INSERT INTO `product_category` VALUES (3, '手机通讯', 2, 3, 0, '2026-04-13 17:02:16', '2026-04-14 11:38:38');
INSERT INTO `product_category` VALUES (4, '生活用品', 0, 1, 0, '2026-04-14 11:37:26', '2026-04-14 11:37:26');
INSERT INTO `product_category` VALUES (332365826, '数学', 1859092481, 1, 0, '2026-05-05 06:34:55', '2026-05-05 06:34:55');
INSERT INTO `product_category` VALUES (1859092481, '教科书', -1253081086, 1, 0, '2026-05-05 06:33:53', '2026-05-05 06:33:53');

-- ----------------------------
-- Table structure for product_collection
-- ----------------------------
DROP TABLE IF EXISTS `product_collection`;
CREATE TABLE `product_collection`  (
  `collect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `user_id` bigint NOT NULL COMMENT '收藏用户ID',
  `collect_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`collect_id`) USING BTREE,
  UNIQUE INDEX `idx_product_user`(`product_id` ASC, `user_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `product_collection_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `product_collection_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 124 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_collection
-- ----------------------------
INSERT INTO `product_collection` VALUES (8, 63, 3, '2026-04-21 02:41:54');
INSERT INTO `product_collection` VALUES (108, 74, 6, '2026-05-02 17:36:12');
INSERT INTO `product_collection` VALUES (109, 64, 2, '2026-05-04 04:48:55');
INSERT INTO `product_collection` VALUES (110, 65, 2, '2026-05-04 04:49:03');
INSERT INTO `product_collection` VALUES (116, 78, 3, '2026-05-05 22:49:32');
INSERT INTO `product_collection` VALUES (121, 77, 3, '2026-05-05 23:39:35');
INSERT INTO `product_collection` VALUES (122, 77, 6, '2026-05-05 23:40:21');

-- ----------------------------
-- Table structure for product_image
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image`  (
  `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片访问地址',
  `image_sort` int NULL DEFAULT 0 COMMENT '排序序号（1=主图）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_image
-- ----------------------------
INSERT INTO `product_image` VALUES (1, 73, '/upload/product/addda950-b38c-42f4-9b6c-e31eddfc1682_24057.jpg', 1, '2026-04-16 12:15:29');
INSERT INTO `product_image` VALUES (2, 61, 'https://picsum.photos/800/800?random=61', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (3, 62, 'https://picsum.photos/800/800?random=62', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (4, 63, 'https://picsum.photos/800/800?random=63', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (5, 64, 'https://picsum.photos/800/800?random=64', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (6, 65, 'https://picsum.photos/800/800?random=65', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (7, 66, 'https://picsum.photos/800/800?random=66', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (8, 67, 'https://picsum.photos/800/800?random=67', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (9, 68, 'https://picsum.photos/800/800?random=68', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (10, 69, 'https://picsum.photos/800/800?random=69', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (11, 70, 'https://picsum.photos/800/800?random=70', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (12, 71, 'https://picsum.photos/800/800?random=71', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (13, 72, 'https://picsum.photos/800/800?random=72', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (14, 73, 'https://picsum.photos/800/800?random=73', 1, '2026-04-20 23:31:20');
INSERT INTO `product_image` VALUES (15, 74, '/upload/product/e1b0a823-234a-48e3-a89b-70635dd260dd_IMG_0447.JPG', 1, '2026-04-23 21:48:40');
INSERT INTO `product_image` VALUES (16, 75, '/upload/product/04180f9e-255f-4308-8924-76af384420e6_24057.jpg', 1, '2026-04-25 13:17:05');
INSERT INTO `product_image` VALUES (17, 76, '/upload/product/73c06cde-6ed5-4718-905b-e755fc4113fc_Weixin Image_20251215145745_119_214.jpg', 1, '2026-04-28 03:14:25');
INSERT INTO `product_image` VALUES (18, 77, '/upload/product/081ab977-b4e4-45c2-8eeb-cdec7b83e122_IMG_0447.JPG', 1, '2026-04-28 15:29:17');
INSERT INTO `product_image` VALUES (19, 77, '/upload/product/d2fa095f-efa1-46f6-9a9f-51ac97c7c3c2_IMG_0447.JPG', 2, '2026-04-28 15:29:17');
INSERT INTO `product_image` VALUES (20, 77, '/upload/product/d34dd67a-737a-4ab1-b307-e7d506573bcf_IMG_0447.JPG', 3, '2026-04-28 15:29:17');
INSERT INTO `product_image` VALUES (21, 77, '/upload/product/9f3b7900-f1ce-4863-8af5-ace91d7b4a00_Weixin Image_20251215145745_119_214.jpg', 4, '2026-04-28 15:29:17');
INSERT INTO `product_image` VALUES (22, 77, '/upload/product/66714e54-749b-431f-8566-6fd497bb98dd_Snipaste_2026-02-15_18-01-04.png', 5, '2026-04-28 15:29:17');
INSERT INTO `product_image` VALUES (23, 78, '/upload/product/b1cece65-e415-4691-ad7b-85b0281e7888_IMG_20260505_195853.jpg', 1, '2026-05-05 20:04:16');
INSERT INTO `product_image` VALUES (24, 78, '/upload/product/bb2d3e4d-198a-4681-ad88-05d34b67ffb9_IMG_20260505_195901.jpg', 2, '2026-05-05 20:04:16');
INSERT INTO `product_image` VALUES (25, 78, '/upload/product/c1f0f2d6-df66-47e2-a8e8-d36d5fd2adb8_IMG_20260505_195909.jpg', 3, '2026-05-05 20:04:16');
INSERT INTO `product_image` VALUES (26, 78, '/upload/product/e6b81bb1-3e69-4c01-b810-f2e24418fe81_IMG_20260505_200144.jpg', 4, '2026-05-05 20:04:16');

-- ----------------------------
-- Table structure for refund_record
-- ----------------------------
DROP TABLE IF EXISTS `refund_record`;
CREATE TABLE `refund_record`  (
  `refund_id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '申请人ID',
  `refund_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '退款原因',
  `refund_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '退款说明',
  `refund_image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '凭证图片',
  `refund_status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-待审核 2-同意 3-拒绝 4-已退款',
  `refund_amount` decimal(10, 2) NOT NULL COMMENT '退款金额',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`refund_id`) USING BTREE,
  UNIQUE INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `refund_record_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_main` (`order_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `refund_record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '退款记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of refund_record
-- ----------------------------

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`  (
  `region_id` int NOT NULL AUTO_INCREMENT COMMENT '地区ID',
  `region_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区名称',
  `parent_id` int NULL DEFAULT 0 COMMENT '父地区ID（0=顶级省份）',
  `level` int NOT NULL COMMENT '层级 1=省 2=市 3=区县 4=街道乡镇',
  `sort` int NULL DEFAULT 0 COMMENT '展示排序',
  `area_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '行政区划编码',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`region_id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 412 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '省市区街道地区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES (1, '北京市', 0, 1, 1, '110000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (2, '天津市', 0, 1, 2, '120000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (3, '河北省', 0, 1, 3, '130000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (4, '山西省', 0, 1, 4, '140000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (5, '内蒙古自治区', 0, 1, 5, '150000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (6, '辽宁省', 0, 1, 6, '210000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (7, '吉林省', 0, 1, 7, '220000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (8, '黑龙江省', 0, 1, 8, '230000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (9, '上海市', 0, 1, 9, '310000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (10, '江苏省', 0, 1, 10, '320000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (11, '浙江省', 0, 1, 11, '330000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (12, '安徽省', 0, 1, 12, '340000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (13, '福建省', 0, 1, 13, '350000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (14, '江西省', 0, 1, 14, '360000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (15, '山东省', 0, 1, 15, '370000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (16, '河南省', 0, 1, 16, '410000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (17, '湖北省', 0, 1, 17, '420000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (18, '湖南省', 0, 1, 18, '430000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (19, '广东省', 0, 1, 19, '440000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (20, '广西壮族自治区', 0, 1, 20, '450000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (21, '海南省', 0, 1, 21, '460000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (22, '重庆市', 0, 1, 22, '500000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (23, '四川省', 0, 1, 23, '510000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (24, '贵州省', 0, 1, 24, '520000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (25, '云南省', 0, 1, 25, '530000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (26, '西藏自治区', 0, 1, 26, '540000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (27, '陕西省', 0, 1, 27, '610000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (28, '甘肃省', 0, 1, 28, '620000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (29, '青海省', 0, 1, 29, '630000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (30, '宁夏回族自治区', 0, 1, 30, '640000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (31, '新疆维吾尔自治区', 0, 1, 31, '650000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (32, '香港特别行政区', 0, 1, 32, '810000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (33, '澳门特别行政区', 0, 1, 33, '820000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (34, '台湾省', 0, 1, 34, '710000', '2026-04-29 23:11:04', '2026-04-29 23:11:04');
INSERT INTO `region` VALUES (35, '北京市', 1, 2, 1, '110100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (36, '天津市', 2, 2, 1, '120100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (37, '石家庄市', 3, 2, 1, '130100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (38, '唐山市', 3, 2, 2, '130200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (39, '秦皇岛市', 3, 2, 3, '130300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (40, '邯郸市', 3, 2, 4, '130400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (41, '邢台市', 3, 2, 5, '130500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (42, '保定市', 3, 2, 6, '130600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (43, '张家口市', 3, 2, 7, '130700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (44, '承德市', 3, 2, 8, '130800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (45, '沧州市', 3, 2, 9, '130900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (46, '廊坊市', 3, 2, 10, '131000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (47, '衡水市', 3, 2, 11, '131100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (48, '太原市', 4, 2, 1, '140100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (49, '大同市', 4, 2, 2, '140200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (50, '阳泉市', 4, 2, 3, '140300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (51, '长治市', 4, 2, 4, '140400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (52, '晋城市', 4, 2, 5, '140500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (53, '朔州市', 4, 2, 6, '140600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (54, '晋中市', 4, 2, 7, '140700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (55, '运城市', 4, 2, 8, '140800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (56, '忻州市', 4, 2, 9, '140900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (57, '临汾市', 4, 2, 10, '141000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (58, '吕梁市', 4, 2, 11, '141100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (59, '呼和浩特市', 5, 2, 1, '150100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (60, '包头市', 5, 2, 2, '150200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (61, '乌海市', 5, 2, 3, '150300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (62, '赤峰市', 5, 2, 4, '150400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (63, '通辽市', 5, 2, 5, '150500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (64, '鄂尔多斯市', 5, 2, 6, '150600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (65, '呼伦贝尔市', 5, 2, 7, '150700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (66, '巴彦淖尔市', 5, 2, 8, '150800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (67, '乌兰察布市', 5, 2, 9, '150900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (68, '兴安盟', 5, 2, 10, '152200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (69, '锡林郭勒盟', 5, 2, 11, '152500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (70, '阿拉善盟', 5, 2, 12, '152900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (71, '沈阳市', 6, 2, 1, '210100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (72, '大连市', 6, 2, 2, '210200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (73, '鞍山市', 6, 2, 3, '210300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (74, '抚顺市', 6, 2, 4, '210400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (75, '本溪市', 6, 2, 5, '210500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (76, '丹东市', 6, 2, 6, '210600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (77, '锦州市', 6, 2, 7, '210700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (78, '营口市', 6, 2, 8, '210800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (79, '阜新市', 6, 2, 9, '210900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (80, '辽阳市', 6, 2, 10, '211000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (81, '盘锦市', 6, 2, 11, '211100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (82, '铁岭市', 6, 2, 12, '211200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (83, '朝阳市', 6, 2, 13, '211300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (84, '葫芦岛市', 6, 2, 14, '211400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (85, '长春市', 7, 2, 1, '220100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (86, '吉林市', 7, 2, 2, '220200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (87, '四平市', 7, 2, 3, '220300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (88, '辽源市', 7, 2, 4, '220400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (89, '通化市', 7, 2, 5, '220500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (90, '白山市', 7, 2, 6, '220600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (91, '松原市', 7, 2, 7, '220700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (92, '白城市', 7, 2, 8, '220800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (93, '延边朝鲜族自治州', 7, 2, 9, '222400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (94, '哈尔滨市', 8, 2, 1, '230100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (95, '齐齐哈尔市', 8, 2, 2, '230200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (96, '鸡西市', 8, 2, 3, '230300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (97, '鹤岗市', 8, 2, 4, '230400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (98, '双鸭山市', 8, 2, 5, '230500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (99, '大庆市', 8, 2, 6, '230600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (100, '伊春市', 8, 2, 7, '230700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (101, '佳木斯市', 8, 2, 8, '230800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (102, '七台河市', 8, 2, 9, '230900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (103, '牡丹江市', 8, 2, 10, '231000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (104, '黑河市', 8, 2, 11, '231100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (105, '绥化市', 8, 2, 12, '231200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (106, '大兴安岭地区', 8, 2, 13, '232700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (107, '上海市', 9, 2, 1, '310100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (108, '南京市', 10, 2, 1, '320100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (109, '无锡市', 10, 2, 2, '320200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (110, '徐州市', 10, 2, 3, '320300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (111, '常州市', 10, 2, 4, '320400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (112, '苏州市', 10, 2, 5, '320500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (113, '南通市', 10, 2, 6, '320600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (114, '连云港市', 10, 2, 7, '320700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (115, '淮安市', 10, 2, 8, '320800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (116, '盐城市', 10, 2, 9, '320900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (117, '扬州市', 10, 2, 10, '321000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (118, '镇江市', 10, 2, 11, '321100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (119, '泰州市', 10, 2, 12, '321200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (120, '宿迁市', 10, 2, 13, '321300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (121, '杭州市', 11, 2, 1, '330100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (122, '宁波市', 11, 2, 2, '330200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (123, '温州市', 11, 2, 3, '330300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (124, '嘉兴市', 11, 2, 4, '330400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (125, '湖州市', 11, 2, 5, '330500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (126, '绍兴市', 11, 2, 6, '330600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (127, '金华市', 11, 2, 7, '330700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (128, '衢州市', 11, 2, 8, '330800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (129, '舟山市', 11, 2, 9, '330900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (130, '台州市', 11, 2, 10, '331000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (131, '丽水市', 11, 2, 11, '331100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (132, '合肥市', 12, 2, 1, '340100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (133, '芜湖市', 12, 2, 2, '340200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (134, '蚌埠市', 12, 2, 3, '340300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (135, '淮南市', 12, 2, 4, '340400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (136, '马鞍山市', 12, 2, 5, '340500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (137, '淮北市', 12, 2, 6, '340600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (138, '铜陵市', 12, 2, 7, '340700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (139, '安庆市', 12, 2, 8, '340800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (140, '黄山市', 12, 2, 9, '341000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (141, '滁州市', 12, 2, 10, '341100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (142, '阜阳市', 12, 2, 11, '341200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (143, '宿州市', 12, 2, 12, '341300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (144, '六安市', 12, 2, 13, '341500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (145, '亳州市', 12, 2, 14, '341600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (146, '池州市', 12, 2, 15, '341700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (147, '宣城市', 12, 2, 16, '341800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (148, '福州市', 13, 2, 1, '350100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (149, '厦门市', 13, 2, 2, '350200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (150, '莆田市', 13, 2, 3, '350300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (151, '三明市', 13, 2, 4, '350400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (152, '泉州市', 13, 2, 5, '350500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (153, '漳州市', 13, 2, 6, '350600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (154, '南平市', 13, 2, 7, '350700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (155, '龙岩市', 13, 2, 8, '350800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (156, '宁德市', 13, 2, 9, '350900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (157, '南昌市', 14, 2, 1, '360100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (158, '景德镇市', 14, 2, 2, '360200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (159, '萍乡市', 14, 2, 3, '360300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (160, '九江市', 14, 2, 4, '360400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (161, '新余市', 14, 2, 5, '360500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (162, '鹰潭市', 14, 2, 6, '360600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (163, '赣州市', 14, 2, 7, '360700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (164, '吉安市', 14, 2, 8, '360800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (165, '宜春市', 14, 2, 9, '360900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (166, '抚州市', 14, 2, 10, '361000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (167, '上饶市', 14, 2, 11, '361100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (168, '济南市', 15, 2, 1, '370100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (169, '青岛市', 15, 2, 2, '370200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (170, '淄博市', 15, 2, 3, '370300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (171, '枣庄市', 15, 2, 4, '370400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (172, '东营市', 15, 2, 5, '370500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (173, '烟台市', 15, 2, 6, '370600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (174, '潍坊市', 15, 2, 7, '370700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (175, '济宁市', 15, 2, 8, '370800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (176, '泰安市', 15, 2, 9, '370900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (177, '威海市', 15, 2, 10, '371000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (178, '日照市', 15, 2, 11, '371100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (179, '临沂市', 15, 2, 12, '371300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (180, '德州市', 15, 2, 13, '371400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (181, '聊城市', 15, 2, 14, '371500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (182, '滨州市', 15, 2, 15, '371600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (183, '菏泽市', 15, 2, 16, '371700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (184, '郑州市', 16, 2, 1, '410100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (185, '开封市', 16, 2, 2, '410200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (186, '洛阳市', 16, 2, 3, '410300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (187, '平顶山市', 16, 2, 4, '410400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (188, '安阳市', 16, 2, 5, '410500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (189, '鹤壁市', 16, 2, 6, '410600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (190, '新乡市', 16, 2, 7, '410700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (191, '焦作市', 16, 2, 8, '410800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (192, '濮阳市', 16, 2, 9, '410900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (193, '许昌市', 16, 2, 10, '411000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (194, '漯河市', 16, 2, 11, '411100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (195, '三门峡市', 16, 2, 12, '411200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (196, '南阳市', 16, 2, 13, '411300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (197, '商丘市', 16, 2, 14, '411400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (198, '信阳市', 16, 2, 15, '411500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (199, '周口市', 16, 2, 16, '411600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (200, '驻马店市', 16, 2, 17, '411700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (201, '济源市', 16, 2, 18, '419001', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (202, '武汉市', 17, 2, 1, '420100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (203, '黄石市', 17, 2, 2, '420200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (204, '十堰市', 17, 2, 3, '420300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (205, '宜昌市', 17, 2, 4, '420500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (206, '襄阳市', 17, 2, 5, '420600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (207, '鄂州市', 17, 2, 6, '420700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (208, '荆门市', 17, 2, 7, '420800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (209, '孝感市', 17, 2, 8, '420900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (210, '荆州市', 17, 2, 9, '421000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (211, '黄冈市', 17, 2, 10, '421100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (212, '咸宁市', 17, 2, 11, '421200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (213, '随州市', 17, 2, 12, '421300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (214, '恩施土家族苗族自治州', 17, 2, 13, '422800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (215, '仙桃市', 17, 2, 14, '429004', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (216, '潜江市', 17, 2, 15, '429005', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (217, '天门市', 17, 2, 16, '429006', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (218, '神农架林区', 17, 2, 17, '429021', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (219, '长沙市', 18, 2, 1, '430100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (220, '株洲市', 18, 2, 2, '430200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (221, '湘潭市', 18, 2, 3, '430300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (222, '衡阳市', 18, 2, 4, '430400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (223, '邵阳市', 18, 2, 5, '430500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (224, '岳阳市', 18, 2, 6, '430600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (225, '常德市', 18, 2, 7, '430700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (226, '张家界市', 18, 2, 8, '430800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (227, '益阳市', 18, 2, 9, '430900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (228, '郴州市', 18, 2, 10, '431000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (229, '永州市', 18, 2, 11, '431100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (230, '怀化市', 18, 2, 12, '431200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (231, '娄底市', 18, 2, 13, '431300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (232, '湘西土家族苗族自治州', 18, 2, 14, '433100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (233, '广州市', 19, 2, 1, '440100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (234, '韶关市', 19, 2, 2, '440200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (235, '深圳市', 19, 2, 3, '440300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (236, '珠海市', 19, 2, 4, '440400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (237, '汕头市', 19, 2, 5, '440500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (238, '佛山市', 19, 2, 6, '440600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (239, '江门市', 19, 2, 7, '440700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (240, '湛江市', 19, 2, 8, '440800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (241, '茂名市', 19, 2, 9, '440900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (242, '肇庆市', 19, 2, 10, '441200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (243, '惠州市', 19, 2, 11, '441300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (244, '梅州市', 19, 2, 12, '441400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (245, '汕尾市', 19, 2, 13, '441500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (246, '河源市', 19, 2, 14, '441600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (247, '阳江市', 19, 2, 15, '441700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (248, '清远市', 19, 2, 16, '441800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (249, '东莞市', 19, 2, 17, '441900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (250, '中山市', 19, 2, 18, '442000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (251, '潮州市', 19, 2, 19, '445100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (252, '揭阳市', 19, 2, 20, '445200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (253, '云浮市', 19, 2, 21, '445300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (254, '南宁市', 20, 2, 1, '450100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (255, '柳州市', 20, 2, 2, '450200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (256, '桂林市', 20, 2, 3, '450300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (257, '梧州市', 20, 2, 4, '450400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (258, '北海市', 20, 2, 5, '450500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (259, '防城港市', 20, 2, 6, '450600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (260, '钦州市', 20, 2, 7, '450700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (261, '贵港市', 20, 2, 8, '450800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (262, '玉林市', 20, 2, 9, '450900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (263, '百色市', 20, 2, 10, '451000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (264, '贺州市', 20, 2, 11, '451100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (265, '河池市', 20, 2, 12, '451200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (266, '来宾市', 20, 2, 13, '451300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (267, '崇左市', 20, 2, 14, '451400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (268, '海口市', 21, 2, 1, '460100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (269, '三亚市', 21, 2, 2, '460200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (270, '三沙市', 21, 2, 3, '460300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (271, '儋州市', 21, 2, 4, '460400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (272, '重庆市', 22, 2, 1, '500100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (273, '成都市', 23, 2, 1, '510100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (274, '自贡市', 23, 2, 2, '510300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (275, '攀枝花市', 23, 2, 3, '510400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (276, '泸州市', 23, 2, 4, '510500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (277, '德阳市', 23, 2, 5, '510600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (278, '绵阳市', 23, 2, 6, '510700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (279, '广元市', 23, 2, 7, '510800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (280, '遂宁市', 23, 2, 8, '510900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (281, '内江市', 23, 2, 9, '511000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (282, '乐山市', 23, 2, 10, '511100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (283, '南充市', 23, 2, 11, '511300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (284, '眉山市', 23, 2, 12, '511400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (285, '宜宾市', 23, 2, 13, '511500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (286, '广安市', 23, 2, 14, '511600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (287, '达州市', 23, 2, 15, '511700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (288, '雅安市', 23, 2, 16, '511800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (289, '巴中市', 23, 2, 17, '511900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (290, '资阳市', 23, 2, 18, '512000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (291, '阿坝藏族羌族自治州', 23, 2, 19, '513200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (292, '甘孜藏族自治州', 23, 2, 20, '513300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (293, '凉山彝族自治州', 23, 2, 21, '513400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (294, '贵阳市', 24, 2, 1, '520100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (295, '六盘水市', 24, 2, 2, '520200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (296, '遵义市', 24, 2, 3, '520300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (297, '安顺市', 24, 2, 4, '520400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (298, '毕节市', 24, 2, 5, '520500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (299, '铜仁市', 24, 2, 6, '520600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (300, '黔西南布依族苗族自治州', 24, 2, 7, '522300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (301, '黔东南苗族侗族自治州', 24, 2, 8, '522600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (302, '黔南布依族苗族自治州', 24, 2, 9, '522700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (303, '昆明市', 25, 2, 1, '530100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (304, '曲靖市', 25, 2, 2, '530300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (305, '玉溪市', 25, 2, 3, '530400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (306, '保山市', 25, 2, 4, '530500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (307, '昭通市', 25, 2, 5, '530600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (308, '丽江市', 25, 2, 6, '530700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (309, '普洱市', 25, 2, 7, '530800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (310, '临沧市', 25, 2, 8, '530900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (311, '楚雄彝族自治州', 25, 2, 9, '532300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (312, '红河哈尼族彝族自治州', 25, 2, 10, '532500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (313, '文山壮族苗族自治州', 25, 2, 11, '532600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (314, '西双版纳傣族自治州', 25, 2, 12, '532800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (315, '大理白族自治州', 25, 2, 13, '532900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (316, '德宏傣族景颇族自治州', 25, 2, 14, '533100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (317, '怒江傈僳族自治州', 25, 2, 15, '533300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (318, '迪庆藏族自治州', 25, 2, 16, '533400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (319, '拉萨市', 26, 2, 1, '540100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (320, '日喀则市', 26, 2, 2, '540200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (321, '昌都市', 26, 2, 3, '540300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (322, '林芝市', 26, 2, 4, '540400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (323, '山南市', 26, 2, 5, '540500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (324, '那曲市', 26, 2, 6, '540600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (325, '阿里地区', 26, 2, 7, '542500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (326, '西安市', 27, 2, 1, '610100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (327, '铜川市', 27, 2, 2, '610200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (328, '宝鸡市', 27, 2, 3, '610300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (329, '咸阳市', 27, 2, 4, '610400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (330, '渭南市', 27, 2, 5, '610500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (331, '延安市', 27, 2, 6, '610600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (332, '汉中市', 27, 2, 7, '610700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (333, '榆林市', 27, 2, 8, '610800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (334, '安康市', 27, 2, 9, '610900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (335, '商洛市', 27, 2, 10, '611000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (336, '兰州市', 28, 2, 1, '620100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (337, '嘉峪关市', 28, 2, 2, '620200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (338, '金昌市', 28, 2, 3, '620300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (339, '白银市', 28, 2, 4, '620400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (340, '天水市', 28, 2, 5, '620500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (341, '武威市', 28, 2, 6, '620600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (342, '张掖市', 28, 2, 7, '620700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (343, '平凉市', 28, 2, 8, '620800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (344, '酒泉市', 28, 2, 9, '620900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (345, '庆阳市', 28, 2, 10, '621000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (346, '定西市', 28, 2, 11, '621100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (347, '陇南市', 28, 2, 12, '621200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (348, '临夏回族自治州', 28, 2, 13, '622900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (349, '甘南藏族自治州', 28, 2, 14, '623000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (350, '西宁市', 29, 2, 1, '630100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (351, '海东市', 29, 2, 2, '630200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (352, '海北藏族自治州', 29, 2, 3, '632200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (353, '黄南藏族自治州', 29, 2, 4, '632300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (354, '海南藏族自治州', 29, 2, 5, '632500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (355, '果洛藏族自治州', 29, 2, 6, '632600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (356, '玉树藏族自治州', 29, 2, 7, '632700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (357, '海西蒙古族藏族自治州', 29, 2, 8, '632800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (358, '银川市', 30, 2, 1, '640100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (359, '石嘴山市', 30, 2, 2, '640200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (360, '吴忠市', 30, 2, 3, '640300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (361, '固原市', 30, 2, 4, '640400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (362, '中卫市', 30, 2, 5, '640500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (363, '乌鲁木齐市', 31, 2, 1, '650100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (364, '克拉玛依市', 31, 2, 2, '650200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (365, '吐鲁番市', 31, 2, 3, '650400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (366, '哈密市', 31, 2, 4, '650500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (367, '昌吉回族自治州', 31, 2, 5, '652300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (368, '博尔塔拉蒙古自治州', 31, 2, 6, '652700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (369, '巴音郭楞蒙古自治州', 31, 2, 7, '652800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (370, '阿克苏地区', 31, 2, 8, '652900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (371, '克孜勒苏柯尔克孜自治州', 31, 2, 9, '653000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (372, '喀什地区', 31, 2, 10, '653100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (373, '和田地区', 31, 2, 11, '653200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (374, '伊犁哈萨克自治州', 31, 2, 12, '654000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (375, '塔城地区', 31, 2, 13, '654200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (376, '阿勒泰地区', 31, 2, 14, '654300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (377, '石河子市', 31, 2, 15, '659001', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (378, '阿拉尔市', 31, 2, 16, '659002', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (379, '图木舒克市', 31, 2, 17, '659003', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (380, '五家渠市', 31, 2, 18, '659004', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (381, '北屯市', 31, 2, 19, '659005', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (382, '铁门关市', 31, 2, 20, '659006', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (383, '双河市', 31, 2, 21, '659007', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (384, '可克达拉市', 31, 2, 22, '659008', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (385, '昆玉市', 31, 2, 23, '659009', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (386, '香港岛', 32, 2, 1, '810100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (387, '九龙', 32, 2, 2, '810200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (388, '新界', 32, 2, 3, '810300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (389, '澳门半岛', 33, 2, 1, '820100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (390, '氹仔', 33, 2, 2, '820200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (391, '路环', 33, 2, 3, '820300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (392, '台北市', 34, 2, 1, '710100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (393, '新北市', 34, 2, 2, '710200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (394, '桃园市', 34, 2, 3, '710300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (395, '台中市', 34, 2, 4, '710400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (396, '台南市', 34, 2, 5, '710500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (397, '高雄市', 34, 2, 6, '710600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (398, '基隆市', 34, 2, 7, '710700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (399, '新竹市', 34, 2, 8, '710800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (400, '嘉义市', 34, 2, 9, '710900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (401, '新竹县', 34, 2, 10, '711000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (402, '苗栗县', 34, 2, 11, '711100', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (403, '彰化县', 34, 2, 12, '711200', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (404, '南投县', 34, 2, 13, '711300', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (405, '云林县', 34, 2, 14, '711400', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (406, '嘉义县', 34, 2, 15, '711500', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (407, '屏东县', 34, 2, 16, '711600', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (408, '宜兰县', 34, 2, 17, '711700', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (409, '花莲县', 34, 2, 18, '711800', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (410, '台东县', 34, 2, 19, '711900', '2026-04-29 23:14:52', '2026-04-29 23:14:52');
INSERT INTO `region` VALUES (411, '澎湖县', 34, 2, 20, '712000', '2026-04-29 23:14:52', '2026-04-29 23:14:52');

-- ----------------------------
-- Table structure for system_message
-- ----------------------------
DROP TABLE IF EXISTS `system_message`;
CREATE TABLE `system_message`  (
  `msg_id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` bigint NOT NULL COMMENT '接收用户ID',
  `msg_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息标题',
  `msg_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `msg_type` tinyint NOT NULL DEFAULT 1 COMMENT '消息类型：1-订单 2-通知 3-系统',
  `is_read` tinyint NOT NULL DEFAULT 0 COMMENT '是否已读：0-未读 1-已读',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `system_message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_message
-- ----------------------------

-- ----------------------------
-- Table structure for trade_record
-- ----------------------------
DROP TABLE IF EXISTS `trade_record`;
CREATE TABLE `trade_record`  (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '涉及用户ID',
  `trade_type` tinyint NOT NULL COMMENT '类型：1-买家付款 2-平台打款 3-退款',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `trade_status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-待处理 2-成功 3-失败',
  `trade_time` datetime NULL DEFAULT NULL COMMENT '交易时间',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `trade_record_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_main` (`order_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `trade_record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '交易流水表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of trade_record
-- ----------------------------

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `address_id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `receiver_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人姓名',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人电话',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '市',
  `district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区',
  `detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_default` tinyint NOT NULL DEFAULT 0 COMMENT '是否默认：0-否 1-是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `user_address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_address
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID（主键）',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名（学号/手机号）',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `student_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `credit_score` tinyint NULL DEFAULT 100 COMMENT '信用分（初始100）',
  `user_status` tinyint NOT NULL DEFAULT 1 COMMENT '账号状态：1-正常 2-禁用 3-冻结',
  `disable_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '禁用原因',
  `disable_time` datetime NULL DEFAULT NULL COMMENT '禁用时间',
  `enable_time` datetime NULL DEFAULT NULL COMMENT '解禁时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所在地',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_student_id`(`student_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (2, 'wentao', '123', '温涛', '2204010111', '19145097704', '/upload/user/profile.jpg', 100, 1, NULL, NULL, NULL, '2026-04-12 21:30:39', '2026-04-28 02:42:30', '湖南');
INSERT INTO `user_info` VALUES (3, 'admin', '123456', NULL, NULL, NULL, '/upload/user/3455e17a-68e4-4a6e-a74d-00506cfc1cde_Weixin Image_20251215145745_119_214.jpg', 100, 1, NULL, NULL, NULL, '2026-04-14 17:48:59', '2026-05-01 21:37:02', '北京市,北京市');
INSERT INTO `user_info` VALUES (6, 'user1', '123', NULL, NULL, '19145097704', '/upload/user/055a305b-4de7-4739-82c6-bdefb21097bd_7 - Copy.jpg', 100, 1, NULL, NULL, NULL, '2026-05-02 09:00:35', '2026-05-02 09:01:48', '四川省,成都市');

-- ----------------------------
-- Table structure for user_report
-- ----------------------------
DROP TABLE IF EXISTS `user_report`;
CREATE TABLE `user_report`  (
  `report_id` bigint NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  `report_user_id` bigint NOT NULL COMMENT '举报用户ID',
  `reported_user_id` bigint NULL DEFAULT NULL COMMENT '被举报用户ID',
  `product_id` bigint NULL DEFAULT NULL COMMENT '被举报商品ID',
  `report_type` tinyint NOT NULL COMMENT '举报类型：1-用户 2-商品',
  `report_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '举报原因',
  `report_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '举报说明',
  `report_status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1-待处理 2-已处理 3-无效',
  `handle_result` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理结果',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`report_id`) USING BTREE,
  INDEX `idx_report_user`(`report_user_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  CONSTRAINT `user_report_ibfk_1` FOREIGN KEY (`report_user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户举报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_report
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

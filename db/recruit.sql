/*
 Navicat Premium Data Transfer

 Source Server         : zhr
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : recruit

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 15/03/2024 14:12:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for applicationinfo
-- ----------------------------
DROP TABLE IF EXISTS `applicationinfo`;
CREATE TABLE `applicationinfo`  (
  `application_id` int(0) NOT NULL AUTO_INCREMENT,
  `post_id` int(0) NOT NULL,
  `user_id` int(0) NOT NULL,
  `application_time` datetime(0) NULL DEFAULT NULL,
  `status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `similarity` int(0) NULL DEFAULT NULL,
  `written_time` datetime(0) NULL DEFAULT NULL,
  `written_score` int(0) NULL DEFAULT NULL,
  `interview_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`application_id`) USING BTREE,
  INDEX `applicationinfo_ibfk_2`(`user_id`) USING BTREE,
  INDEX `applicationinfo_ibfk_1`(`post_id`) USING BTREE,
  CONSTRAINT `applicationinfo_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `jobposition` (`post_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `applicationinfo_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `useraccount` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of applicationinfo
-- ----------------------------
INSERT INTO `applicationinfo` VALUES (16, 123459, 1212, '2024-03-13 10:05:55', '录用', 82, '2024-03-14 10:21:43', 90, '2024-03-15 10:21:50');
INSERT INTO `applicationinfo` VALUES (18, 234568, 1216, '2024-03-13 10:06:42', '初审中', 67, NULL, 0, NULL);
INSERT INTO `applicationinfo` VALUES (19, 345678, 1218, '2024-03-13 10:06:59', '初审中', 74, NULL, 0, NULL);
INSERT INTO `applicationinfo` VALUES (20, 345679, 1219, '2024-03-13 10:07:28', '淘汰', 67, NULL, 0, NULL);
INSERT INTO `applicationinfo` VALUES (21, 345678, 1219, '2024-03-13 10:07:38', '淘汰', 65, NULL, 0, NULL);
INSERT INTO `applicationinfo` VALUES (22, 234568, 1219, '2024-03-13 10:07:40', '淘汰', 73, NULL, 0, NULL);
INSERT INTO `applicationinfo` VALUES (28, 123456, 1234, '2024-03-13 11:48:09', '录用', 84, '2024-03-14 11:49:24', 99, '2024-03-15 11:49:29');
INSERT INTO `applicationinfo` VALUES (34, 123459, 123456, '2024-03-15 13:11:19', '录用', 68, '2024-03-16 13:12:38', 80, '2024-03-19 13:13:38');

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information`  (
  `inform_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `inform_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `inform_time` datetime(0) NULL DEFAULT NULL,
  `inform_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `inform_state` int(0) NULL DEFAULT NULL,
  `post_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`inform_id`) USING BTREE,
  INDEX `information_ibfk_1`(`user_id`) USING BTREE,
  INDEX `information_ibfk_2`(`post_id`) USING BTREE,
  CONSTRAINT `information_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `useraccount` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES (58, 1212, '应聘图书馆管理员申请提交成功，请等待初审结果', '2024-03-13 10:05:55', '应聘消息', 0, 123459);
INSERT INTO `information` VALUES (60, 1216, '应聘辅导员申请提交成功，请等待初审结果', '2024-03-13 10:06:42', '应聘消息', 0, 234568);
INSERT INTO `information` VALUES (61, 1218, '应聘讲师申请提交成功，请等待初审结果', '2024-03-13 10:06:59', '应聘消息', 0, 345678);
INSERT INTO `information` VALUES (62, 1219, '应聘实验室技术员申请提交成功，请等待初审结果', '2024-03-13 10:07:28', '应聘消息', 0, 345679);
INSERT INTO `information` VALUES (63, 1219, '应聘讲师申请提交成功，请等待初审结果', '2024-03-13 10:07:38', '应聘消息', 0, 345678);
INSERT INTO `information` VALUES (64, 1219, '应聘辅导员申请提交成功，请等待初审结果', '2024-03-13 10:07:40', '应聘消息', 0, 234568);
INSERT INTO `information` VALUES (68, 1212, '应聘图书馆管理员初审通过，请在2024-03-14 10:21:43在XXX网站上参加笔试', '2024-03-13 10:21:48', '初审消息', 1, 123459);
INSERT INTO `information` VALUES (69, 1212, '图书馆管理员笔试通过，您的笔试成绩为90,请在2024-03-14 10:21:43在xx楼xx号参加面试', '2024-03-13 10:21:58', '笔试消息', 1, 123459);
INSERT INTO `information` VALUES (70, 1212, '恭喜面试者通过本高校图书馆管理员招聘，请到xx楼xx号登记', '2024-03-13 10:22:00', '面试消息', 1, 123459);
INSERT INTO `information` VALUES (78, 1234, '应聘教授申请提交成功，请等待初审结果', '2024-03-13 11:48:09', '应聘消息', 0, 123456);
INSERT INTO `information` VALUES (80, 1219, '很遗憾应聘者未能通过辅导员的招聘，请申请其他更符合的岗位', '2024-03-13 11:49:10', '淘汰消息', 0, 234568);
INSERT INTO `information` VALUES (81, 1219, '很遗憾应聘者未能通过讲师的招聘，请申请其他更符合的岗位', '2024-03-13 11:49:16', '淘汰消息', 0, 345678);
INSERT INTO `information` VALUES (82, 1219, '很遗憾应聘者未能通过实验室技术员的招聘，请申请其他更符合的岗位', '2024-03-13 11:49:18', '淘汰消息', 0, 345679);
INSERT INTO `information` VALUES (83, 1234, '应聘教授初审通过，请在2024-03-14 11:49:24在XXX网站上参加笔试', '2024-03-13 11:49:26', '初审消息', 0, 123456);
INSERT INTO `information` VALUES (84, 1234, '教授笔试通过，您的笔试成绩为99,请在2024-03-14 11:49:24在xx楼xx号参加面试', '2024-03-13 11:49:36', '笔试消息', 0, 123456);
INSERT INTO `information` VALUES (85, 1234, '恭喜面试者通过本高校教授招聘，请到xx楼xx号登记', '2024-03-13 11:49:38', '面试消息', 0, 123456);
INSERT INTO `information` VALUES (102, 123456, '应聘图书馆管理员申请提交成功，请等待初审结果', '2024-03-15 13:11:19', '应聘消息', 0, 123459);
INSERT INTO `information` VALUES (103, 123456, '应聘图书馆管理员初审通过，请在2024-03-16 13:12:38在XXX网站上参加笔试', '2024-03-15 13:12:44', '初审消息', 0, 123459);
INSERT INTO `information` VALUES (104, 123456, '图书馆管理员笔试通过，您的笔试成绩为80,请在2024-03-16 13:12:38在xx楼xx号参加面试', '2024-03-15 13:13:49', '笔试消息', 0, 123459);
INSERT INTO `information` VALUES (105, 123456, '恭喜面试者通过本高校图书馆管理员招聘，请到xx楼xx号登记', '2024-03-15 13:13:59', '面试消息', 1, 123459);

-- ----------------------------
-- Table structure for jobposition
-- ----------------------------
DROP TABLE IF EXISTS `jobposition`;
CREATE TABLE `jobposition`  (
  `post_id` int(0) NOT NULL AUTO_INCREMENT,
  `post_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age_requirement` int(0) NULL DEFAULT NULL,
  `education_requirement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `major_requirement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `post_state` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `start_time` date NULL DEFAULT NULL,
  `end_time` date NULL DEFAULT NULL,
  `number_requirement` int(0) NULL DEFAULT NULL,
  `post_synopsis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `min_salary` int(0) NULL DEFAULT NULL,
  `max_salary` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 901237 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of jobposition
-- ----------------------------
INSERT INTO `jobposition` VALUES (123456, '教授', '计算机科学与技术学院', 45, '博士', '计算机科学专业', '招聘中', '科研类', '2024-03-01', '2024-03-31', 0, '负责计算机科学领域的研究工作', 20000, 30000);
INSERT INTO `jobposition` VALUES (123459, '图书馆管理员', '图书馆', 35, '本科', '图书情报学专业', '招聘中', '图书馆管理类', '2024-03-01', '2024-03-31', 1, '负责高校图书馆管理工作', 8000, 18000);
INSERT INTO `jobposition` VALUES (234567, '副教授', '数学学院', 40, '硕士', '应用数学专业', '招聘中', '教学类', '2024-02-01', '2024-03-31', 3, '负责应用数学领域的教学和研究工作', 15000, 25000);
INSERT INTO `jobposition` VALUES (234568, '辅导员', '学生事务部', 35, '本科', '教育学专业', '招聘中', '教学类', '2024-03-01', '2024-03-31', 5, '负责学生教育管理工作', 9000, 19000);
INSERT INTO `jobposition` VALUES (345678, '讲师', '物理学院', 35, '硕士', '理论物理专业', '招聘中', '教学类', '2024-01-01', '2024-03-31', 5, '负责理论物理领域的教学和研究工作', 12000, 22000);
INSERT INTO `jobposition` VALUES (345679, '实验室技术员', '实验室与设备管理部', 30, '专科生', '实验室技术与管理专业', '招聘中', '实验室管理类', '2024-03-01', '2024-03-15', 3, '负责实验室设备管理和技术支持工作', 7000, 17000);
INSERT INTO `jobposition` VALUES (456788, '招生宣传员', '招生办公室', 25, '大专', '市场营销专业', '招聘中', '教学类', '2024-03-01', '2024-03-31', 5, '负责高校招生宣传工作', 5000, 15000);
INSERT INTO `jobposition` VALUES (456789, '助教', '化学学院', 30, '本科', '有机化学专业', '招聘中', '教学类', '2024-03-01', '2024-03-31', 4, '协助教授进行有机化学领域的教学和研究工作', 8000, 18000);
INSERT INTO `jobposition` VALUES (567890, '研究员', '生命科学学院', 45, '博士', '生物信息学专业', '招聘中', '科研类', '2024-02-15', '2024-03-25', 1, '负责生物信息学领域的研究工作', 18000, 28000);
INSERT INTO `jobposition` VALUES (678901, '副研究员', '地球科学学院', 40, '硕士', '地质学专业', '招聘中', '科研类', '2024-03-01', '2024-03-15', 2, '协助研究员进行地质学领域的研究工作', 14000, 24000);
INSERT INTO `jobposition` VALUES (789012, '助理研究员', '环境科学与工程学院', 35, '硕士', '环境工程专业', '招聘中', '科研类', '2024-03-01', '2024-03-31', 3, '协助副研究员进行环境工程领域的研究工作', 11000, 21000);
INSERT INTO `jobposition` VALUES (890123, '实验员', '材料科学与工程学院', 30, '本科', '材料科学与工程专业', '招聘中', '实验室管理类', '2024-03-01', '2024-03-31', 0, '负责材料科学与工程领域的实验工作', 6000, 16000);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `news_id` int(0) NOT NULL AUTO_INCREMENT,
  `news_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `news_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `department` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 759 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (84, '材料学院展览', '新材料展即将开幕', '2024-03-11 17:21:07', '材料科学与工程学院');
INSERT INTO `news` VALUES (92, '图书馆通知', '新书入库欢迎借阅', '2024-03-07 18:42:51', '图书馆');
INSERT INTO `news` VALUES (127, '生命科学院研讨', '生物科技前沿探讨', '2024-03-05 14:58:03', '生命科学学院');
INSERT INTO `news` VALUES (128, '行政部公告', '行政流程更新提醒', '2024-03-05 19:05:32', '行政事务部');
INSERT INTO `news` VALUES (145, '计算机学院新动态', '最新研究成果发布', '2024-03-01 10:30:45', '计算机科学与技术学院');
INSERT INTO `news` VALUES (205, '辅导员消息', '学生工作重要通知', '2024-03-01 20:11:17', '学生事务部');
INSERT INTO `news` VALUES (231, '数学学院赛事', '数学竞赛即将开启', '2024-02-29 11:15:22', '数学学院');
INSERT INTO `news` VALUES (314, '实验室招聘', '技术员岗位急聘中', '2024-02-28 21:26:04', '实验室与设备管理部');
INSERT INTO `news` VALUES (359, '环境学院活动', '环保项目启动报名', '2024-02-15 16:17:39', '环境科学与工程学院');
INSERT INTO `news` VALUES (365, '物理学院讲座', '专家系列讲座之一', '2024-02-07 12:27:18', '物理学院');

-- ----------------------------
-- Table structure for normaluser
-- ----------------------------
DROP TABLE IF EXISTS `normaluser`;
CREATE TABLE `normaluser`  (
  `user_id` int(0) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `place` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `education` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `major` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `academy` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `birthdate` date NULL DEFAULT NULL,
  `politics_status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `other_Info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `normaluser_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `useraccount` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of normaluser
-- ----------------------------
INSERT INTO `normaluser` VALUES (1212, '张佛罗', '男', '110101199003075896', '北京', '13800138000', 33, '博士', '计算机科学专业', '清华大学', '1990-03-07', '中共党员', '**********');
INSERT INTO `normaluser` VALUES (1216, '赵六', '女', '110101199306105898', '深圳', '13600136000', 30, '大专', '电子工程专业', '华南理工大学', '1993-06-10', '中共党员', '**********');
INSERT INTO `normaluser` VALUES (1218, '孙七', '男', '110101199407115899', '成都', '13500135000', 29, '硕士', '机械工程专业', '四川大学', '1994-07-11', '群众', '**********');
INSERT INTO `normaluser` VALUES (1219, '周八', '女', '110101199508125890', '重庆', '13400134000', 28, '本科', '土木工程专业', '重庆大学', '1995-08-12', '共青团员', '**********');
INSERT INTO `normaluser` VALUES (1221, '吴九', '男', '110101199609135891', '杭州', '13300133000', 27, '博士', '化学工程专业', '浙江大学', '1996-09-13', '中共党员', '**********');
INSERT INTO `normaluser` VALUES (1222, '卫十四', '女', '110101200102185896', '济南', '12800128000', 22, '大专', '数学专业', '山东大学', '2001-02-18', '共青团员', '**********');
INSERT INTO `normaluser` VALUES (1223, '郑十', '女', '110101199710145892', '南京', '13200132000', 26, '大专', '环境工程专业', '南京大学', '1997-10-14', '群众', '**********');
INSERT INTO `normaluser` VALUES (1234, '张三', '男', '500365412369874562', '重庆', '13296584563', 46, '博士', '计算机科学专业', '重庆交通大学', '1978-01-01', '中共党员', '**********');
INSERT INTO `normaluser` VALUES (1245, '李四', '女', '11010119910408589X', '上海', '13900139000', 32, '硕士', '软件工程专业', '复旦大学', '1991-04-08', '群众', '**********');
INSERT INTO `normaluser` VALUES (1261, '冯十一', '男', '110101199811155893', '武汉', '13100131000', 25, '硕士', '生物工程专业', '华中科技大学', '1998-11-15', '共青团员', '**********');
INSERT INTO `normaluser` VALUES (1263, '褚十三', '男', '110101200001175895', '天津', '12900129000', 23, '博士', '物理学专业', '南开大学', '2000-01-17', '群众', '**********');
INSERT INTO `normaluser` VALUES (1264, '陈十二', '女', '110101199912165894', '西安', '13000130000', 24, '本科', '材料科学与工程专业', '西北工业大学', '1999-12-16', '中共党员', '**********');
INSERT INTO `normaluser` VALUES (123456, '李鸿飞', NULL, '444444444444444444', '甘肃灵台', '18919331724', 23, '本科', '计算机', '重庆交通大学', '2001-02-01', '共青团员', '无');

-- ----------------------------
-- Table structure for useraccount
-- ----------------------------
DROP TABLE IF EXISTS `useraccount`;
CREATE TABLE `useraccount`  (
  `user_id` int(0) NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` enum('super-admin','admin','user') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of useraccount
-- ----------------------------
INSERT INTO `useraccount` VALUES (666, '123456', 'admin');
INSERT INTO `useraccount` VALUES (999, '123456', 'super-admin');
INSERT INTO `useraccount` VALUES (1212, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1216, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1218, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1219, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1221, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1222, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1223, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1234, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1245, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1261, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1263, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (1264, 'qqqqqqqq1.', 'user');
INSERT INTO `useraccount` VALUES (123456, 'qqqqqqqq1.', 'user');

-- ----------------------------
-- Procedure structure for update_state
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_state`;
delimiter ;;
CREATE PROCEDURE `update_state`()
BEGIN
    UPDATE jobposition SET post_state = '未开始' WHERE start_time>CURDATE();
    UPDATE jobposition SET post_state = '招聘中' WHERE start_time<CURDATE();
		UPDATE jobposition SET post_state = '已结束' WHERE end_time<CURDATE();
END
;;
delimiter ;

-- ----------------------------
-- Event structure for update_event
-- ----------------------------
DROP EVENT IF EXISTS `update_event`;
delimiter ;;
CREATE EVENT `update_event`
ON SCHEDULE
EVERY '5' SECOND STARTS '2024-03-08 15:32:57'
DO CALL update_state()
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;

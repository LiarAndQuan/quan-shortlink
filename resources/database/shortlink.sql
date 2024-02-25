-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shortlink
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_group_0`
--

DROP TABLE IF EXISTS `t_group_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_0` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_1`
--

DROP TABLE IF EXISTS `t_group_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_1` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_10`
--

DROP TABLE IF EXISTS `t_group_10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_10` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_11`
--

DROP TABLE IF EXISTS `t_group_11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_11` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_12`
--

DROP TABLE IF EXISTS `t_group_12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_12` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_13`
--

DROP TABLE IF EXISTS `t_group_13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_13` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_14`
--

DROP TABLE IF EXISTS `t_group_14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_14` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_15`
--

DROP TABLE IF EXISTS `t_group_15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_15` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_2`
--

DROP TABLE IF EXISTS `t_group_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_2` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_3`
--

DROP TABLE IF EXISTS `t_group_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_3` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_4`
--

DROP TABLE IF EXISTS `t_group_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_4` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_5`
--

DROP TABLE IF EXISTS `t_group_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_5` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_6`
--

DROP TABLE IF EXISTS `t_group_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_6` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_7`
--

DROP TABLE IF EXISTS `t_group_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_7` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_8`
--

DROP TABLE IF EXISTS `t_group_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_8` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_group_9`
--

DROP TABLE IF EXISTS `t_group_9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_group_9` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT NULL COMMENT '分组标识',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `username` varchar(256) DEFAULT NULL COMMENT '创建分组用户名',
  `sort_order` int DEFAULT NULL COMMENT '分组排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username_gid` (`gid`,`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_0`
--

DROP TABLE IF EXISTS `t_link_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_0` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_1`
--

DROP TABLE IF EXISTS `t_link_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_1` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_10`
--

DROP TABLE IF EXISTS `t_link_10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_10` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_11`
--

DROP TABLE IF EXISTS `t_link_11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_11` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_12`
--

DROP TABLE IF EXISTS `t_link_12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_12` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_13`
--

DROP TABLE IF EXISTS `t_link_13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_13` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_14`
--

DROP TABLE IF EXISTS `t_link_14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_14` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_15`
--

DROP TABLE IF EXISTS `t_link_15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_15` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_2`
--

DROP TABLE IF EXISTS `t_link_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_2` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_3`
--

DROP TABLE IF EXISTS `t_link_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_3` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_4`
--

DROP TABLE IF EXISTS `t_link_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_4` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_5`
--

DROP TABLE IF EXISTS `t_link_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_5` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_6`
--

DROP TABLE IF EXISTS `t_link_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_6` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_7`
--

DROP TABLE IF EXISTS `t_link_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_7` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_8`
--

DROP TABLE IF EXISTS `t_link_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_8` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_9`
--

DROP TABLE IF EXISTS `t_link_9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_9` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `domain` varchar(128) DEFAULT NULL COMMENT '域名',
  `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `origin_url` varchar(1024) DEFAULT NULL COMMENT '原始链接',
  `click_num` int DEFAULT '0' COMMENT '点击量',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `favicon` varchar(256) DEFAULT NULL COMMENT '网站图标',
  `enable_status` tinyint(1) DEFAULT NULL COMMENT '启用标识 0：启用 1：未启用',
  `created_type` tinyint(1) DEFAULT NULL COMMENT '创建类型 0：接口创建 1：控制台创建',
  `valid_date_type` tinyint(1) DEFAULT NULL COMMENT '有效期类型 0：永久有效 1：自定义',
  `valid_date` datetime DEFAULT NULL COMMENT '有效期',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `total_pv` int DEFAULT NULL COMMENT '历史PV',
  `total_uv` int DEFAULT NULL COMMENT '历史UV',
  `total_uip` int DEFAULT NULL COMMENT '历史UIP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_time` bigint DEFAULT '0' COMMENT '删除时间戳',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`del_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_access_logs`
--

DROP TABLE IF EXISTS `t_link_access_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_access_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
  `user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户信息',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'IP',
  `locale` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '浏览器',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
  `device` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `network` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_access_stats`
--

DROP TABLE IF EXISTS `t_link_access_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_access_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分组标识',
  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '完整短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `pv` int DEFAULT NULL COMMENT '访问量',
  `uv` int DEFAULT NULL COMMENT '独立访问数',
  `uip` int DEFAULT NULL COMMENT '独立IP数',
  `hour` int DEFAULT NULL COMMENT '小时',
  `weekday` int DEFAULT NULL COMMENT '星期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识：0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_unique_access_stats` (`full_short_url`,`gid`,`date`,`hour`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_browser_stats`
--

DROP TABLE IF EXISTS `t_link_browser_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_browser_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
  `date` date DEFAULT NULL COMMENT '日期',
  `cnt` int DEFAULT NULL COMMENT '访问量',
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`,`gid`,`date`,`browser`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短链接监控操作系统访问状态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_device_stats`
--

DROP TABLE IF EXISTS `t_link_device_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_device_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `date` date DEFAULT NULL COMMENT '日期',
  `cnt` int DEFAULT NULL COMMENT '访问量',
  `device` varchar(64) DEFAULT NULL COMMENT '访问设备',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`device`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_0`
--

DROP TABLE IF EXISTS `t_link_goto_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_0` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_1`
--

DROP TABLE IF EXISTS `t_link_goto_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_1` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_10`
--

DROP TABLE IF EXISTS `t_link_goto_10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_10` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_11`
--

DROP TABLE IF EXISTS `t_link_goto_11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_11` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_12`
--

DROP TABLE IF EXISTS `t_link_goto_12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_12` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_13`
--

DROP TABLE IF EXISTS `t_link_goto_13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_13` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_14`
--

DROP TABLE IF EXISTS `t_link_goto_14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_14` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_15`
--

DROP TABLE IF EXISTS `t_link_goto_15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_15` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_2`
--

DROP TABLE IF EXISTS `t_link_goto_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_2` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_3`
--

DROP TABLE IF EXISTS `t_link_goto_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_3` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_4`
--

DROP TABLE IF EXISTS `t_link_goto_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_4` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_5`
--

DROP TABLE IF EXISTS `t_link_goto_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_5` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_6`
--

DROP TABLE IF EXISTS `t_link_goto_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_6` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_7`
--

DROP TABLE IF EXISTS `t_link_goto_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_7` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_8`
--

DROP TABLE IF EXISTS `t_link_goto_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_8` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_goto_9`
--

DROP TABLE IF EXISTS `t_link_goto_9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_goto_9` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL,
  `gid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_locale_stats`
--

DROP TABLE IF EXISTS `t_link_locale_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_locale_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
  `date` date DEFAULT NULL COMMENT '日期',
  `cnt` int DEFAULT NULL COMMENT '访问量',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份名称',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市名称',
  `adcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市编码',
  `country` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国家标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`,`gid`,`date`,`adcode`,`province`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_network_stats`
--

DROP TABLE IF EXISTS `t_link_network_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_network_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '完整短链接',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `date` date DEFAULT NULL COMMENT '日期',
  `cnt` int DEFAULT NULL COMMENT '访问量',
  `network` varchar(64) DEFAULT NULL COMMENT '访问网络',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_browser_stats` (`full_short_url`,`gid`,`date`,`network`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_os_stats`
--

DROP TABLE IF EXISTS `t_link_os_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_os_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `full_short_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '完整短链接',
  `gid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组标识',
  `date` date DEFAULT NULL COMMENT '日期',
  `cnt` int DEFAULT NULL COMMENT '访问量',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作系统',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0表示删除 1表示未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_locale_stats` (`full_short_url`,`gid`,`date`,`os`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短链接监控操作系统访问状态';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_0`
--

DROP TABLE IF EXISTS `t_link_stats_today_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_0` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_1`
--

DROP TABLE IF EXISTS `t_link_stats_today_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_1` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_10`
--

DROP TABLE IF EXISTS `t_link_stats_today_10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_10` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_11`
--

DROP TABLE IF EXISTS `t_link_stats_today_11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_11` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_12`
--

DROP TABLE IF EXISTS `t_link_stats_today_12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_12` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_13`
--

DROP TABLE IF EXISTS `t_link_stats_today_13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_13` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_14`
--

DROP TABLE IF EXISTS `t_link_stats_today_14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_14` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_15`
--

DROP TABLE IF EXISTS `t_link_stats_today_15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_15` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_2`
--

DROP TABLE IF EXISTS `t_link_stats_today_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_2` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_3`
--

DROP TABLE IF EXISTS `t_link_stats_today_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_3` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_4`
--

DROP TABLE IF EXISTS `t_link_stats_today_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_4` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_5`
--

DROP TABLE IF EXISTS `t_link_stats_today_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_5` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_6`
--

DROP TABLE IF EXISTS `t_link_stats_today_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_6` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_7`
--

DROP TABLE IF EXISTS `t_link_stats_today_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_7` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_8`
--

DROP TABLE IF EXISTS `t_link_stats_today_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_8` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_link_stats_today_9`
--

DROP TABLE IF EXISTS `t_link_stats_today_9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_link_stats_today_9` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `gid` varchar(32) DEFAULT 'default' COMMENT '分组标识',
  `full_short_url` varchar(128) DEFAULT NULL COMMENT '短链接',
  `date` date DEFAULT NULL COMMENT '日期',
  `today_pv` int DEFAULT '0' COMMENT '今日PV',
  `today_uv` int DEFAULT '0' COMMENT '今日UV',
  `today_uip` int DEFAULT '0' COMMENT '今日IP数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_full-short-url` (`full_short_url`,`gid`,`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_0`
--

DROP TABLE IF EXISTS `t_user_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_0` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_1`
--

DROP TABLE IF EXISTS `t_user_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_1` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_10`
--

DROP TABLE IF EXISTS `t_user_10`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_10` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_11`
--

DROP TABLE IF EXISTS `t_user_11`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_11` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_12`
--

DROP TABLE IF EXISTS `t_user_12`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_12` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_13`
--

DROP TABLE IF EXISTS `t_user_13`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_13` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_14`
--

DROP TABLE IF EXISTS `t_user_14`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_14` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_15`
--

DROP TABLE IF EXISTS `t_user_15`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_15` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_2`
--

DROP TABLE IF EXISTS `t_user_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_2` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_3`
--

DROP TABLE IF EXISTS `t_user_3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_3` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_4`
--

DROP TABLE IF EXISTS `t_user_4`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_4` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_5`
--

DROP TABLE IF EXISTS `t_user_5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_5` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_6`
--

DROP TABLE IF EXISTS `t_user_6`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_6` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_7`
--

DROP TABLE IF EXISTS `t_user_7`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_7` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_8`
--

DROP TABLE IF EXISTS `t_user_8`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_8` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_9`
--

DROP TABLE IF EXISTS `t_user_9`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_9` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(256) DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `deletion_time` bigint DEFAULT NULL COMMENT '注销时间戳',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除 1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-25 12:49:45

DROP DATABASE IF EXISTS db_im;
CREATE DATABASE IF NOT EXISTS db_im;
USE db_im;

/*单聊消息表*/
DROP TABLE IF EXISTS tb_message;
CREATE TABLE tb_message(
msg_id BIGINT NOT NULL,
msg_from BIGINT NOT NULL,
msg_to BIGINT NOT NULL,
msg_content VARCHAR(1000),
msg_type INT NOT NULL,
msg_send_time DATETIME NOT NULL,
msg_status TINYINT NOT NULL,
primary key(msg_id)
)ENGINE=InnoDb DEFAULT CHARSET=utf8mb4;

/*用户表*/
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user(
user_id BIGINT AUTO_INCREMENT NOT NULL,
user_password VARCHAR(50) NOT NULL,
user_salt VARCHAR(50) NOT NULL,
user_email VARCHAR(200) NOT NULL,
user_avatar VARCHAR(200),
user_displayName VARCHAR(20) NOT NULL,
user_add_time DATETIME NOT NULL,
user_update_time DATETIME,
primary key(user_id)
)ENGINE=InnoDb DEFAULT CHARSET=utf8mb4;

/*好友关系表*/
DROP TABLE IF EXISTS tb_friendship;
CREATE TABLE tb_friendship(
friendship_id BIGINT NOT NULL AUTO_INCREMENT,
friendship_owner_id BIGINT NOT NULL,
friendship_friend_id BIGINT NOT NULL,
friendship_name VARCHAR(20) NOT NULL,
PRIMARY KEY(friendship_id)
)ENGINE=InnoDb DEFAULT CHARSET=utf8mb4;

/*群组管理暂时不使用*/
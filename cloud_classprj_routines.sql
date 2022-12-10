CREATE DATABASE  IF NOT EXISTS `cloud_classprj` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cloud_classprj`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cloud_classprj
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping events for database 'cloud_classprj'
--

--
-- Dumping routines for database 'cloud_classprj'
--
/*!50003 DROP PROCEDURE IF EXISTS `insert_content` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`MySQL_server_jhk`@`%` PROCEDURE `insert_content`(
param_book_id varchar(20),
param_book_content text
)
BEGIN
declare param_book_page int;
declare param_content_limit int;
declare param_book_page_idx int;
declare returnMsg varchar(100);

/*페이지당 텍스트 길이 제한*/
set param_content_limit = 396;
/*최초등록의 경우*/
if((select exists(select book_page from book_content where book_id = param_book_id))=0) then
	set param_book_page = 1;
    set param_book_page_idx = 1;
else
	/*책 젤 뒷페이지 가져오기 */
	set param_book_page = (select book_page from book_content where book_id = param_book_id order by book_page desc limit 1);
	/*책 페이지 텍스트 길이 제한 넘으면 페이지++*/
	if (param_content_limit < ((select sum(content_length) from book_content where book_id = param_book_id and book_page = param_book_page)+char_length(param_book_content))) then
		set param_book_page = param_book_page+1;
		set param_book_page_idx = 1;
	else
		set param_book_page_idx = (select book_page_idx from book_content where book_id = param_book_id and book_page = param_book_page order by book_page_idx desc limit 1)+1 ;
	end if;
end if;

insert into book_content(book_id,book_page,book_content,book_page_idx,content_length) 
values(param_book_id,param_book_page,param_book_content, param_book_page_idx,char_length(param_book_content));
delete from book_content where book_content = '.';

set returnMsg = "success";
select returnMsg,param_book_id,param_book_page,param_book_content,param_book_page_idx,char_length(param_book_content);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `loopInsert` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`dsu_cloud_prj`@`%` PROCEDURE `loopInsert`()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE id varchar(100);
    WHILE i <= 100 DO /*더미데이터 갯수*/
		set id = concat('testid',i);
        INSERT INTO member(userName , userID, userPassword, userEmail, token)
          VALUES(id,id,id,concat(id,'@naver.com'),md5(id));
        SET i = i + 1;
    END WHILE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `Register` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`dsu_cloud_prj`@`%` PROCEDURE `Register`(
param_username varchar(20),
param_userid varchar(45),
param_userpassword varchar(45),
param_useremail varchar(45))
BEGIN
	declare returnMsg varchar(100);
    declare email_overlap varchar(100);
    declare id_overlap varchar(100);
    
    set email_overlap = (select if(exists(select userID from member where userEmail=param_useremail),'true','false'));
    set id_overlap = (select if(exists(select userID from member where userID= param_userid),'true','false'));
    
	if(id_overlap = 'true') then
		set returnMsg = 'ID_already_exists';
    elseif (email_overlap = 'true') then
		set returnMsg = 'Email_already_exists';
	else
		insert into member(userName,userID,userPassword,userEmail) values(param_username,param_userid,param_userpassword,param_useremail);
        set returnMsg = 'success';
    end if;
    
    select returnMsg;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-10 20:27:02

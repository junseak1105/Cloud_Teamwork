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
-- Table structure for table `book_content`
--

DROP TABLE IF EXISTS `book_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_content` (
  `book_id` varchar(45) NOT NULL,
  `book_page` int NOT NULL,
  `book_content` text NOT NULL,
  `content_length` int NOT NULL,
  `book_page_idx` int NOT NULL,
  KEY `book_id_idx` (`book_id`),
  CONSTRAINT `book_id` FOREIGN KEY (`book_id`) REFERENCES `book_list` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_content`
--

LOCK TABLES `book_content` WRITE;
/*!40000 ALTER TABLE `book_content` DISABLE KEYS */;
INSERT INTO `book_content` VALUES ('집으로 가는길',1,'전쟁에 대해 별의별 소문이 다 돌았지만 우리와 상관없는 머나먼 딴 세상 이야기로만 들렸다.',50,1),('집으로 가는길',1,' 우리 마을을 지나가는 피난민들이 하나 둘씩 눈에 띄면서부터 비로소 우리 나라에서 진짜로 전쟁이 벌어지고 있구나 싶었다.',67,2),('원미동 사람들',1,'들어올 때 그랬던 것처럼, 폭이 좁은 문을 빠져나오는 사이 장롱의 옆구리가 또 동전만큼 뜯겨나가고 말았다.',59,1),('원미동 사람들',1,' 농의 한쪽을 부여잡고 그 무게로 숨이 턱에 닿고 있던 그는 새로 생긴 흠집을 자세히 들여다볼 수는 없었다.',60,2),('자유의 비극',1,'부자가된 가난한 집 맏아들의 도덕적 의무를 다룬 책 [가난한 집 맏아들] 이 출간된 지 5년이 지났다.',57,1),('자유의 비극',1,' 나름 베스트셀러 반열에도 올랐다.',19,2),('자유의 비극',1,' 우리 사회의 재벌과 부자들이 읽었으면 했다.',25,3),('자유의 비극',1,' 그러나 정작 재벌과 부자들은 \"그래서 뭐.',24,4),('자유의 비극',1,' 나는 그런것에 관심 없어.',15,5),('자유의 비극',1,' 신경도 쓰지않아\" 라고 말하는 것 같았다.',24,6),('원미동 사람들',1,' ‘원미동’으로 상징되는 소시민들의 일상! ‘양귀자 소설’의 진수를 보여주는 연작소설집 『원미동 사람들』.',59,3),('원미동 사람들',1,' 1986년 3월부터 1987년 8월까지 발표되었던 11편의 소설이 담겨 있으며, 경기도 부천시 원미동을 무대로 1980년대 소시민들의 삶을 그려냈다.',84,4),('원미동 사람들',1,' 1987년 초판이 발행되었고 현재까지 총 111쇄를 기록하며 우리 시대의 고전으로 자리매김하고 있다.',57,5),('원미동 사람들',1,' 이번 4판은 한층 가독성 있는 편집과 새로운 모습으로 선보인다.',36,6),('원미동 사람들',2,' 1980년대의 원미동은 서울이라는 꿈의 도시로 편입하려는 사람들, 혹은 서울에서 숱한 밤을 악몽으로 지새운 사람들이 모여 사는 동네였다.',77,1),('원미동 사람들',2,' 여기에 실린 소설들은 절망의 고개를 넘고 있는 사람들의 쓸쓸한 자화상을 담고 있다.',47,2),('원미동 사람들',2,' 치열했던 당시 소시민들의 삶의 풍속도가 생생하게 펼쳐진다.',33,3),('원미동 사람들',2,' 그러면서도 작가 특유의 박진감 있는 문체와 깊은 성찰을 담은 문장들, 재기발랄한 유머와 소소한 반전으로 읽는 재미를 선사한다.',71,4),('집으로 가는길',1,'영화 「집으로 가는 길」의 모티프가 된 실제 사건의 주인공이 낱낱이 고백하는 가슴 먹먹한 756일간의 기록 2004년 10월 30일, 대한민국의 평범한 주부인 장미정 씨는 파리 오를리 공항에서 코카인 17킬로그램이 든 트렁크를 소지하고 있다가 공항 경찰로부터 적발되었다.',151,3),('집으로 가는길',1,' 가방을 부탁한 지인에게 속아 금광 원석이 들어 있는 줄로만 알고 있던 그녀는 마약 현행범으로 낙인 찍힌 채 재판도 받지 못하고 파리 외곽의 프렌 구치소에 투옥되고 만다.',95,4),('집으로 가는길',2,' 프랑스 교도소로 강제 이송되면서도 그녀는 변변한 발언의 기회조차 얻지 못했다.',44,1),('집으로 가는길',2,' 프랑스 법정에서 국선변호인을 지정해주었지만, 말 한마디 통하지 않는 프랑스인 변호사와의 소통은 불가능했다.',60,2),('집으로 가는길',2,' 1년 4개월의 수감생활 후 임시석방되었지만, 법원 관할 아파트에서 보호감찰을 받으며 재판을 기다려야 했다.',60,3),('집으로 가는길',2,' 그는 집세를 낼 돈도, 끼니를 이을 돈도 없었다.',28,4),('집으로 가는길',2,' 한국에서 남편이 부쳐오는 생활비에 전적으로 의존하면서 언제 풀려날 수 있을지 기약도 없이 고통스런 나날을 이어가던 그는 진실을 기록하기 위해 펜을 들었다.',87,5),('집으로 가는길',2,' 이 책은 저자가 혼자 힘으로 감당할 수 없던 사건의 진실과 자신의 목소리를 있는 그대로 담아낸 당시의 실제 일기를 바탕으로 한 회고록이다.',78,6),('집으로 가는길',3,' 그가 이 아픈 기록을 세상에 공개하는 까닭은 누구도 자신과 같은 어리석은 선택을 하지 말라고, 또 어떤 이유로든 가족과 헤어지는 일은 없어야 한다고 이야기하고 싶기 때문이었다.',99,1),('집으로 가는길',3,' 자신이 왜 대서양 외딴섬에서 이런 악몽 같은 나날을 보내는지, 무엇이 어디에서부터 잘못되었는지, 사건이 일어난 뒤 프랑스 검찰청에서 그리고 법정에서 외치고 싶었던 말들을 숨김 없이 노트에 적어 내려갔다.',114,2),('집으로 가는길',3,' 엄마 없이 하루가 다르게 커가고 있을 어린 딸아이와 사랑하는 남편에 대한 그리움도 피눈물로 써내려갔다.',58,3);
/*!40000 ALTER TABLE `book_content` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-10 20:27:01

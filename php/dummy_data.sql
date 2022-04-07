DELIMITER $$
DROP PROCEDURE IF EXISTS loopInsert$$

CREATE PROCEDURE loopInsert()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 202 DO /*더미데이터 입력 반복 횟수*/
        INSERT INTO post(title , content, date, hit, recommend_Y, recommend_N,writer_id) /*통상적인 insert 문*/
          VALUES(concat('제목',i), concat('내용',i), now(), 0, 0, 0,'jay'); /* concat('','') << 두개 합치는 함수, ex| concat('제목',i) = 제목1...제목2...제목3.....제목202
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER $$

-- 
-- call loopInsert(); <<프로시저 작성 후 새 쿼리 창에서 쓰시면 실행됩니다.

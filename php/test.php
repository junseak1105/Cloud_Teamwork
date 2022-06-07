<?php
$book_id = '자유의 비극';
$book_content = '부자가된 가난한 집 맏아들의 도덕적 의무를 다룬 책 [가난한 집 맏아들] 이 출간된 지 5년이 지났다. 나름 베스트셀러 반열에도 올랐다. 우리 사회의 재벌과 부자들이 읽었으면 했다. 그러나 정작 재벌과 부자들은 "그래서 뭐. 나는 그런것에 관심 없어. 신경도 쓰지않아" 라고 말하는 것 같았다.';
//sql 책 내용 입력 시작
$book_content_arr = explode(".", $book_content);
foreach($book_content_arr as $value){
    include("db.php");
    $sql = "call insert_content('".$book_id."','".$value.".')";
    $conn->query($sql);
    // $storedProc = 'call insert_content(?,?)';
    // $statement = mysqli_prepare($conn,$storedProc);
    // mysqli_stmt_bind_param($statement,'ss',$book_id,$value);
    // mysqli_stmt_execute($statement);
    mysqli_close($conn);
}
?>
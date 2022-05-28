<?php

//error_reporting(E_ALL);
//ini_set('display_errors', '1');
include('db.php');

$arr = array();

$sql = "SELECT book_id,ifnull((select book_page from book_content where book_id = a.book_id order by book_page desc limit 1),0) as book_page from book_list a;";
$result = mysqli_query($conn, $sql);

while ($row = mysqli_fetch_array($result)) {
    array_push($arr,array('book_id'=>$row[0],'book_page'=>$row[1]));
}

//$responseJson = json_encode($arr,JSON_UNESCAPED_SLASHES);
//echo str_replace('\\', '', json_encode($arr,JSON_UNESCAPED_SLASHES+JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE));
echo json_encode(array("result"=>$arr));

?>
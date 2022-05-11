<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

include("db.php");

$book_id = $_POST["book_id"];
$book_page = $_POST["book_page"];
$book_content = $_POST["book_content"];
$content_length = $_POST["content_length"];
$book_page_idx = $_POST["book_page_idx"];

$statement = mysqli_prepare($conn, "SELECT book_id, book_page, book_content, content_length, book_page_idx FROM book_content");
mysqli_stmt_bind_param($statement, "sssss", $book_id, $book_page, $book_content, $content_length, $book_page_idx);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $book_id, $book_page, $book_content, $content_length, $book_page_idx);

$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($statement)) {
    $response["success"] = true;
    $response["book_id"] = $book_id;
    $response["book_page"] = $book_page;
    $response["book_content"] = $book_content;
    $response["content_length"] = $content_length;
    $response["book_page_idx"] = $book_page_idx;
}
mysqli_close($conn);
echo json_encode($response);

?>

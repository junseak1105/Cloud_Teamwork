<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

include("db.php");
$sql = "SELECT book_id, book_page, book_content, content_length, book_page_idx FROM book_content";

$statement = mysqli_prepare($conn, $sql);
$res = mysqli_query($conn, $sql);
$result = array();
while ($row = mysqli_fetch_array($res)) {
    array_push($result, array('book_id' => $row[0], 'book_page' => $row[1], 'book_content' => $row[2], 'content_length' => $row[3], 'book_page_idx' => $row[4]));
}
echo json_encode($result, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);

?>

<!--, JSON_UNESCAPED_UNICODE|JSON_PRETTY_PRINT|JSON_UNESCAPED_SLASHES -->
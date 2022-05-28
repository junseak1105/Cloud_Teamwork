<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    
    $book_id = $_GET["book_id"];
    $book_content = $_GET["book_content"];
    
    $book_content_arr = explode(".", $book_content);
    foreach($book_content_arr as $value){
        include("db.php");
        $sql = "call insert_content('".$book_id."','".$value.".')";
        echo $sql;
        $conn->query($sql);
        mysqli_close($conn);
    }
    
?>
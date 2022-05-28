<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    
    include("db.php");

    $book_name = $_POST["book_name"];


    //$statement = mysqli_prepare($con, "INSERT INTO member(userName,userID,userPassword,userEmail) VALUES (?,?,?,?)");
    //mysqli_stmt_bind_param($statement, "ssss", $userName, $userID, $userPassword, $userEmail);
    //mysqli_stmt_execute($statement);

    $sql = "delete from book_list where book_id = '$book_name'";
    if(mysqli_query($conn,$sql)){
        echo "success";
    }else{
        echo "fail";
    }
    mysqli_close($conn);
   
    

?>
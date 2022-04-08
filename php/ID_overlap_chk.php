<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    
    include("db.php");

    $userID = $_POST["userID"];

    $statement = mysqli_prepare($conn, "select if(exists(select userID from member where userID='$userID'),'false','true')as result");
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$result);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["overlap"] = $result;
    }


    mysqli_close($conn);
    echo json_encode($response);
    

?>

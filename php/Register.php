<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    
    include("db.php");

    $userName = $_POST["userName"];
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userEmail = $_POST["userEmail"];


    //$statement = mysqli_prepare($con, "INSERT INTO member(userName,userID,userPassword,userEmail) VALUES (?,?,?,?)");
    //mysqli_stmt_bind_param($statement, "ssss", $userName, $userID, $userPassword, $userEmail);
    //mysqli_stmt_execute($statement);

    $storedProc = 'call Register(?,?,?,?)';
    $statement = mysqli_prepare($conn,$storedProc);
    mysqli_stmt_bind_param($statement,'ssss',$userName,$userID,$userPassword,$userEmail);
    mysqli_stmt_execute($statement);
    mysqli_stmt_bind_result($statement,$returnMsg);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["returnMsg"] = $returnMsg;
    }

    mysqli_close($conn);
    echo json_encode($response);
   
    

?>
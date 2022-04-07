<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    
    $con = mysqli_connect("jhk.n-e.kr", "dsu_cloud_prj", "cloud_prj_jhk", "cloud_classprj","3306");
    mysqli_query($con,'SET NAMES utf8');

    $userName = $_POST["userName"];
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userEmail = $_POST["userEmail"];


    //$statement = mysqli_prepare($con, "INSERT INTO member(userName,userID,userPassword,userEmail) VALUES (?,?,?,?)");
    //mysqli_stmt_bind_param($statement, "ssss", $userName, $userID, $userPassword, $userEmail);
    //mysqli_stmt_execute($statement);

    $storedProc = 'call Register(?,?,?,?)';
    $statement = mysqli_prepare($con,$storedProc);
    mysqli_stmt_bind_param($statement,'ssss',$userName,$userID,$userPassword,$userEmail);
    mysqli_stmt_execute($statement);
    mysqli_stmt_bind_result($statement,$returnMsg);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["returnMsg"] = $returnMsg;
    }

    mysqli_close($con);
    echo json_encode($response);
   
    

?>
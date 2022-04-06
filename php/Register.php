<?php
    //error_reporting(E_ALL);
    //ini_set('display_errors', '1');
    
    $con = mysqli_connect("jhk.n-e.kr", "dsu_cloud_prj", "cloud_prj_jhk", "cloud_classprj","3306");
    mysqli_query($con,'SET NAMES utf8');

    $userName = $_POST["userName"];
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userEmail = $_POST["userEmail"];

    $statement = mysqli_prepare($con, "INSERT INTO member(userName,userID,userPassword,userEmail) VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssss", $userName, $userID, $userPassword, $userEmail);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;


    echo json_encode($response);
    mysqli_close($con);
    

?>
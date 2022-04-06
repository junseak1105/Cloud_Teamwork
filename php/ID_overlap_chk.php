<?php
    //error_reporting(E_ALL);
    //ini_set('display_errors', '1');
    
    $con = mysqli_connect("jhk.n-e.kr", "dsu_cloud_prj", "cloud_prj_jhk", "cloud_classprj","3306");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];

    $statement = mysqli_prepare($con, "select if(exists(select userID from member where userID='?'),'success','fail')as result");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;


    echo json_encode($response);
    mysqli_close($con);
    

?>
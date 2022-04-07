<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    
    $con = mysqli_connect("jhk.n-e.kr", "dsu_cloud_prj", "cloud_prj_jhk", "cloud_classprj","3306");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];

    $statement = mysqli_prepare($con, "select if(exists(select userID from member where userID='$userID'),'false','true')as result");
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement,$result);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["overlap"] = $result;
    }


    mysqli_close($con);
    echo json_encode($response);
    

?>

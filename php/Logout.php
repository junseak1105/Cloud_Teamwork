<!-- 로그인 php 가져옴 -->
<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');

    include("db.php");

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];

    $statement = mysqli_prepare($conn, "SELECT userID,userPassword FROM member WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword,$token);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["token"] = $token;
    }
    mysqli_close($conn);
    echo json_encode($response);



?>

<!-- 로그아웃 버튼 연결 필요 -->
<?php
 
        @session_start();
        $result = session_destroy(); //세션을 닫아준다 
?>
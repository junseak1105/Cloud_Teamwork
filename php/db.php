<?php
error_reporting(E_ALL);
ini_set('display_errors', '1');

$host="jhk.n-e.kr"; //자신이 사용하는 호스트 ip로 입력해도됌
$user="dsu_cloud_prj";      
$pass="cloud_prj_jhk";       
$dbname= "cloud_classprj"; //자신이 지금 사용하는 dbname 모르면 show databases; 확인
$dbport="3306"

$conn=mysqli_connect($host,$user,$pass,$dbname,$dbport);
//한글 입력 하게
mysqli_set_charset($conn,"utf8");

 if($conn){
        echo "db성공";
}else{
       echo "db실패";
}
?>
<?php
$conn = mysqli_connect(
    'jhk.n-e.kr', // localhost
    'dsu_cloud_prj', // 아이디
    'cloud_prj_jhk', // 비밀번호
    'cloud_classprj' // 데이터베이스 스키마
);
// $conn = mysqli_connect(
//     'localhost',
//     'root',
//     '',
//     'dgnr'
// );

mysqli_query($conn, "set session character_set_connection=utf8;");
mysqli_query($conn, "set session character_set_results=utf8;");
mysqli_query($conn, "set session character_set_client=utf8;");
?>
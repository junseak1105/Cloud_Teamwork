<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');
    include("db.php");
    $book_id = $_POST["book_id"];
    $book_page = $_POST["book_page"];

    $client_secret = "bm53elJtZllrbWh2ZWpZTmN2WmRHb253WXJjRWZWcFQ=";
        $url = "https://dfi2dqet3q.apigw.ntruss.com/custom/v1/15134/28c52046370d75aa67de6e9b6d87c43e0fe56a0e2ce859c1911b99f48ac90e8f/general";
    $image_file = "http://visualoft.kr/wp-content/uploads/2016/02/screenshot_1-1-710x355.png";
    //$image_url = "";
    //객체생성
    class params{
        public $version;
        public $requestId;
        public $timestamp;
        public $images;
    }
    class image{
        public $format;
        public $name;
        public $url;
    }

    $params = new params();
    $image = new image();

    $params->version = "V1";
    $params->requestId = uniqid();
    $params->timestamp = time();
    $image->format = "png";
    $image->name = "screenshot_1-1-710x355";
    $image->url = $image_file;
    $images = array($image);
    $params->images = $images;
    $json = json_encode($params);
    //echo "<pre>".$json."</pre>";

    $boundary = uniqid();
    $is_post = true;
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, $is_post);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $post_form = array("message" => $json, "file" => new CURLFILE($image_file));
    curl_setopt($ch, CURLOPT_POSTFIELDS, $post_form);
    $headers = array();
    $headers[] = "X-OCR-SECRET: ".$client_secret;
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    $response = curl_exec($ch);
    $err = curl_error($ch);
    $status_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close ($ch);

    //echo $status_code;
    if($status_code == 200) {
        echo $response;//OCR 결과
        //json 파싱 시작
        $arr = json_decode($response,true);
        $book_content ="";
        foreach($arr["images"] as $arr2) {
            foreach($arr2["fields"] as $text) {
                $book_content = $book_content."&nbsp".$text["inferText"];
            }
        }
        //json 파싱 끝
        
        //$book_content = "안녕하세요 제 이름은 김재훈입니다! 이걸 나누면 점단위로 분해? 되어야 하는데 이게 잘. 되나 모르겠네요";
        //$book_id = "test";
        //sql 책 내용 입력 시작
        $book_content_arr = preg_split('/([^.:!?]+[.:!?]+)/', $book_content, -1, PREG_SPLIT_DELIM_CAPTURE | PREG_SPLIT_NO_EMPTY);
        // for($i=0;$i<count($book_content_arr);$i++){
        //     echo $book_content_arr[$i];
        //     echo "<br>";
        // }
        for($i=0;$i<count($book_content_arr);$i++){
            $storedProc = 'call insert_content(?,?)';
            $statement = mysqli_prepare($conn,$storedProc);
            mysqli_stmt_bind_param($statement,'ss',$book_id,$book_content_arr[$i]);
            mysqli_stmt_execute($statement);
            mysqli_stmt_bind_result($statement,$returnMsg);
            $response = array();
            $response["success"] = false;

            while(mysqli_stmt_fetch($statement)) {
                $response["success"] = true;
                $response["returnMsg"] = $returnMsg;
            }
        }
        echo json_encode($response);
        //sql 책 내용 입력 끝
    } else {
        //echo "ERROR: ".$response;
        echo json_encode($response);
    }

        
?>
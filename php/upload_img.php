<?php
  
    $book_id = $_GET['book_id'];
    $file_path = "images/";
    $file_path = $file_path . basename( $_FILES['uploaded_file']['name']);

    if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        $client_secret = "bm53elJtZllrbWh2ZWpZTmN2WmRHb253WXJjRWZWcFQ=";
        $url = "https://dfi2dqet3q.apigw.ntruss.com/custom/v1/15134/28c52046370d75aa67de6e9b6d87c43e0fe56a0e2ce859c1911b99f48ac90e8f/general";
        $image_file = "http://jhk.n-e.kr:8080/images/".$_FILES['uploaded_file']['name'];
        //$image_url = "http://jhk.n-e.kr:8080/".$file_path;
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
        $image->format = "jpg";
        $image->name = "image";
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
            //echo $response;//OCR 결과
            //json 파싱 시작
            $arr = json_decode($response,true);
            $book_content ="";
            foreach($arr["images"] as $arr2) {
                foreach($arr2["fields"] as $text) {
                    $book_content = $book_content." ".$text["inferText"];
                }
            }
            //json 파싱 끝
            //파일 삭제
            unlink("./images/".$_FILES['uploaded_file']['name']);

            //sql 책 내용 입력 시작
            $book_content_arr = explode(".", $book_content);
            foreach($book_content_arr as $value){
                include("db.php");
                $sql = "call insert_content('".$book_id."','".$value."')";
                $conn->query($sql);
                // $storedProc = 'call insert_content(?,?)';
                // $statement = mysqli_prepare($conn,$storedProc);
                // mysqli_stmt_bind_param($statement,'ss',$book_id,$value);
                // mysqli_stmt_execute($statement);
                mysqli_close($conn);
            }
            echo "success";
        } 
    }else{
        echo "fail";
    }
 ?>

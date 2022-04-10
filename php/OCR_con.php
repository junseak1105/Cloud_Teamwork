<?php
    error_reporting(E_ALL);
    ini_set('display_errors', '1');

    $client_secret = "bm53elJtZllrbWh2ZWpZTmN2WmRHb253WXJjRWZWcFQ=";
        $url = "https://dfi2dqet3q.apigw.ntruss.com/custom/v1/15134/28c52046370d75aa67de6e9b6d87c43e0fe56a0e2ce859c1911b99f48ac90e8f/general";
    $image_file = "http://visualoft.kr/wp-content/uploads/2016/02/screenshot_1-1-710x355.png";
    //$image_url = "";
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
        echo $response;
    } else {
    echo "ERROR: ".$response;
    }
?>
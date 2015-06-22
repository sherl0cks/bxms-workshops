<?php

if(isset($_POST['c_name'])){
    
    $res['sendstatus'] = 1;
    $res['message'] = 'Form Submission Succesful';
    echo json_encode($res);
}

?>
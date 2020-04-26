<?php
	require_once 'dbconnect.php';

	$username = $_POST["username"];
	$password = $_POST["password"];

	$password = md5($password);

	$query = "SELECT * FROM User_table as u WHERE u.uname='$username' AND u.passwd = '$password'";

	$result = mysqli_query($con,$query);
	if (!$result) {
	    printf("Error: %s\n", mysqli_error($con));
	    exit();
	}
	$numResults = mysqli_num_rows($result);
	if($numResults == 0){
		echo "Sorry! Wrong Credentials\n";
		echo "Hello hi";
	}
	else{
		echo "Login Successful!!";
	}

	mysqli_close($con);
?>
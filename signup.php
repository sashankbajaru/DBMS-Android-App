<?php
	
	require_once 'dbconnect.php';

	$name = $_POST["username"];
	$email = $_POST["email"];
	$password = $_POST["password"];
	$repassword = $_POST["rePassword"];


	$password = md5($password);

	$query = "(SELECT * FROM User_table WHERE emailid='$email') union (SELECT * FROM User_table WHERE uname='$name')";
	$result = mysqli_query($con, $query);
	$numResults = mysqli_num_rows($result);
	if (!$result) {
	    printf("Error: %s\n", mysqli_error($con));
	    exit();
	}
	#mysqli_close($con);
	if($numResults == 1)
	{
		echo "Already registered!";
		exit();
		// echo "Already registered!";
	}
	else
	{
		$query = "INSERT INTO User_table VALUES('$name', '$password', '$email')";
		if(mysqli_query($con, $query) == true){
			//header("Location: login.php");
			echo "Successfully registered!";
		}
		else{
			echo strlen($password);
			echo "Error in inserting!!";
		}
	}
?>
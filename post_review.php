<?php
	require_once 'dbconnect.php';

	$rating = $_POST['rating'];
	$review = $_POST['review'];
	$movie_id = $_POST['midforreview'];
	$username = $_POST['username'];
	//echo "hello world\n";


	if($rating == 0){
		//$_SESSION['nullrating'] = true;
		echo "Rating should be between 1 and 10\n";
		//header("Location: post_review.php?mid=".$_SESSION['midforreview']);
		exit();
	}

	if($review==''){
		echo "Please write some review\n";
		exit();
	}

	$query = "SELECT * FROM User_movie_review_table WHERE uname='$username' and mid= $movie_id";
	$result = mysqli_query($con, $query);
	$numResults = mysqli_num_rows($result);
	if (!$result) {
	    printf("Error: %s\n", mysqli_error($con));
	    exit();
	}
	#mysqli_close($con);
	if($numResults >= 1)
	{
		echo "You have already rated this movie!\n";
		exit();
		// echo "Already registered!";
	}

	// $query = "DECLARE @rid int;";
	$review_query = "INSERT INTO Review_table(rating,review) VALUES($rating,'$review')";
	if( mysqli_query($con, $review_query) == false){
		echo "FAILED1 ".mysqli_error($con);
	}
	$rid = mysqli_insert_id($con);

	// $query .= "SET @rid = SCOPE_IDENTITY();";
	$query = "INSERT INTO User_movie_review_table VALUES('$username',$rid, $movie_id)";
	if( mysqli_query($con, $query) == false){
		echo "FAILED2 ".mysqli_error($con);
		//echo "<br>$query";
	}

	$numrev_update_query = "UPDATE Movie_table
	SET num_reviews = num_reviews + 1
	WHERE mid = ".$movie_id;
	if( mysqli_query($con, $numrev_update_query) == false){
		echo "FAILED3 ".mysqli_error($con);
	}

	$rating_update_query = "UPDATE Movie_table
	SET rating = IF(num_reviews = 1,$rating, (rating*(num_reviews-1)+$rating))/num_reviews
	WHERE mid = $movie_id";
	if( mysqli_query($con, $rating_update_query) == false){
		echo "FAILED4 ".mysqli_error($con);
	}

	echo "Rating updated succesfully";
	mysqli_close($con);
	exit();

?>
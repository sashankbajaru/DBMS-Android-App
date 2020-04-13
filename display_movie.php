<?php
	require_once 'dbconnect.php';

	$movie_id = $_GET["mid"];

	$query = "SELECT mid,title,yearofrelease,language,budget,boxoffice,runningtime,M.country,director,cinematographer,music,screenwriter,M.img,rating,genre,
	Dir.first_name as dir_fname, Dir.last_name as dir_lname,
	Cin.first_name as cin_fname, Cin.last_name as cin_lname,
	Mus.first_name as mus_fname, Mus.last_name as mus_lname,
	Scr.first_name as scr_fname, Scr.last_name as scr_lname
	FROM Movie_table M, Person_table Dir, Person_table Cin, Person_table Mus, Person_table Scr
	WHERE mid = $movie_id and director = Dir.pid and cinematographer = Cin.pid and music = Mus.pid and screenwriter = Scr.pid";

	$result = mysqli_query($con, $query);

	// if (!$result) {
	// 	printf("Error: %s\n", mysqli_error($con));
	// 	exit();
	// }

	header('Content-Type:Application/json');

	$row = mysqli_fetch_assoc($result);

	$array[] = $row;
	$return_value = json_encode($array);



	$actor_query = "SELECT P.pid, first_name,last_name
	FROM Movie_table M, Actor_table A, Person_table P
	WHERE M.mid = $movie_id and M.mid = A.mid and A.pid = P.pid";

	$actor_result = mysqli_query($con,$actor_query);
	$num_results = mysqli_num_rows($actor_result);

	if($num_results == 0){
		$return_value = $return_value.json_encode(json_decode("[{}]"));
	}
	else{
		while($actor_row = mysqli_fetch_assoc($actor_result)){
			$act_array[] = $actor_row;
		}

		$return_value = $return_value.json_encode($act_array);
	}





	$producer_query = "SELECT P.pid, first_name,last_name
	FROM Movie_table M, Producer_table Pr, Person_table P
	WHERE M.mid = $movie_id and M.mid = Pr.mid and Pr.pid = P.pid";

	$producer_result = mysqli_query($con,$producer_query);
	$num_results = mysqli_num_rows($producer_result);

	if($num_results == 0){
		$return_value = $return_value.json_encode(json_decode("[{}]"));
	}
	else{
		while($producer_row = mysqli_fetch_assoc($producer_result)){
			$pro_array[] = $producer_row;
		}

		$return_value = $return_value.json_encode($pro_array);
	}




	$review_query = "SELECT rating,review,uname
	FROM Review_table R,User_movie_review_table U
	WHERE mid = $movie_id and U.rid = R.rid";

	$review_result = mysqli_query($con, $review_query);
	$num_results = mysqli_num_rows($review_result);

	if($num_results == 0){
		$return_value = $return_value.json_encode(json_decode("[{}]"));
	}
	else{
		while($review_row = mysqli_fetch_assoc($review_result)){
			$rev_array[] = $review_row;		
		}

		$return_value = $return_value.json_encode($rev_array);
	}

	echo $return_value;

	mysqli_close($con);
?>
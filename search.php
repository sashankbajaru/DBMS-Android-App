<?php
	require_once 'dbconnect.php';

	$search_key = $_POST["search_key"];

	$query = "(SELECT mid,title,yearofrelease, img, rating
	FROM Movie_table
	WHERE lower(title) like '%$search_key%')
	union
	(SELECT mid, title, yearofrelease, M.img, rating
	FROM Movie_table M, Person_table P
	WHERE (lower(first_name) like '%$search_key%' or lower(last_name) like '%$search_key%') and(director = pid or cinematographer = pid or music = pid or screenwriter = pid)
	order by rating desc)
	union
	(SELECT M.mid, title,yearofrelease, M.img, rating
	FROM Person_table P, Actor_table A, Movie_table M
	WHERE (lower(first_name) like '%$search_key%' or lower(last_name) like '%$search_key%') and A.pid = P.pid and A.mid = M.mid
	order by rating desc)
	union
	(SELECT M.mid, title,yearofrelease, M.img, rating
	FROM Person_table P, Producer_table Pr, Movie_table M
	WHERE (lower(first_name) like '%$search_key%' or lower(last_name) like '%$search_key%') and Pr.pid = P.pid and Pr.mid = M.mid
	order by rating desc)";

	$result = mysqli_query($con,$query);
	$numResults = mysqli_num_rows($result);
	if($numResults == 0){
		echo "no results";
	}
	else{
		while ($row = mysqli_fetch_assoc($result)) {
			$array[] = $row;
		}

		header('Content-Type:Application/json');

		echo json_encode($array);

	}

	mysqli_free_result($result);

	mysqli_close($con);
?>



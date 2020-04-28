<?php
	require_once 'dbconnect.php'

	header('Content-Type:Application/json');

	$person_id = $_GET["pid"];

	$query = "SELECT * FROM Person_table P where pid = $person_id";

	$result = mysqli_query($con, $query);

	$row=mysqli_fetch_assoc($result);

	$array[]=$row;
	$return_value=json_encode($array);

	//movie query
	'''
	$movie_query = "(SELECT M.mid, title, M.img, rating, yearofrelease
	FROM Movie_table M, Person_table P, Actor_table A
	WHERE M.mid = A.mid and A.pid = P.pid and A.pid = $person_id
	ORDER BY rating DESC)
	union
	(SELECT M.mid, title, M.img, rating, yearofrelease
	FROM Movie_table M, Person_table P, Producer_table Pr
	WHERE M.mid = Pr.mid and Pr.pid = P.pid and Pr.pid = $person_id
	ORDER BY rating DESC)
	union
	(SELECT mid,title,img, rating,yearofrelease
	FROM Movie_table
	WHERE director = $person_id or cinematographer = $person_id or music = $person_id or screenwriter = $person_id
	ORDER BY rating DESC)";

	$movie_result=mysql_query($con,$movie_query);
	'''

	echo $return_value;

	mysqli_close($con);
	
?>
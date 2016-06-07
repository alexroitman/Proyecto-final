<?php
$con=mysqli_connect("localhost","u241352082_alexr","123456","u241352082_bdfin");
if (mysqli_connect_errno()) {
	die("Failed to connect to MySQL" . mysqli_connect_error());
}
$string = file_get_contents('php://input'); 
$objeto = json_decode($string, true);
echo $objeto;
$query = "INSERT INTO Subidas (IdSubida,LatLong,IdLinea,Horasubida) values (?, ?, ? ,?)";
$stmt = $con->prepare($query);
$stmt->bind_param(
	'ssss',
	$objeto["IdSubida"],
	$objeto["LatLong"],
	$objeto["IdLinea"],
	$objeto["Horasubida"]
);
$stmt->execute();
//$res = $stmt->get_result();
mysqli_close($con);
?>
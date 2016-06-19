<?php
$con=mysqli_connect("localhost","u241352082_alexr","123456","u241352082_bdfin");
if (mysqli_connect_errno()) {
	die("Failed to connect to MySQL" . mysqli_connect_error());
}
$string = file_get_contents('php://input'); 
$objeto = json_decode($string, true);
echo $objeto;
$query = "INSERT INTO Subidas (LatLong,IdLinea,Horasubida) values (?, ? ,?)";
$stmt = $con->prepare($query);
$stmt->bind_param(
	'sss',
	
	$objeto["LatLong"],
	$objeto["IdLinea"],
	$objeto["Horasubida"]
);
$stmt->execute();


$query2 = "SELECT MAX(IdSubida) AS ultimo FROM Subidas";
$result = mysqli_query($con, $query2);
$objetos = array();
while($row = mysqli_fetch_array($result)) 
{ 
	$Id=$row['ultimo'];
    	$objeto = array('IdSubida'=> $Id);	
    	$objetos[] = $objeto;
	
}
mysqli_close($con);
header("Content-Type: application/json");
$json_string = json_encode($objetos,JSON_PRETTY_PRINT);
echo $json_string;

?>


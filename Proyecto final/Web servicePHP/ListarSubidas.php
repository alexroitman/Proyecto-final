<?php
$con=mysqli_connect("localhost","u241352082_alexr","123456","u241352082_bdfin");
if (mysqli_connect_errno()) {
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$query ="SELECT * FROM Subidas WHERE IdLinea=?";

$objetos = array();
$stmt = $con->prepare($query);
$stmt->bind_param(
	'i',
	$_GET["IdLinea"]
	);
$result = mysqli_query($con, $query);

while($row = mysqli_fetch_array($result)) 
{ 
	$IdSubida=$row['IdSubida'];
	$LatLong=$row['LatLong'];
	$IdLinea=$row['IdLinea'];	
	$Horasubida=$row['Horasubida'];	
	$objeto = array('IdSubida'=> $IdSubida, 'LatLong'=> $LatLong, 'IdLinea'=> $IdLinea, 'Horasubida'=> $Horasubida);	
    	$objetos[] = $objeto;
	
}
$close = mysqli_close($con) 
or die("Ha sucedido un error inesperado en la desconexion de la base de datos");
header("Content-Type: application/json");
$json_string = json_encode($objetos,JSON_PRETTY_PRINT);
echo $json_string;
?>
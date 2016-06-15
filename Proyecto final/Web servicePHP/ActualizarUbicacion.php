<?php
$con=mysqli_connect("localhost","u241352082_alexr","123456","u241352082_bdfin");
if (mysqli_connect_errno()) {
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$UltimaUbicacion=$_GET["UltimaUbicacion"];
$query ="UPDATE Subidas SET UltimaUbicacion='545;45' WHERE IdSubida='$UltimaUbicacion'";

$objetos = array();
$result = mysqli_query($con, $query);
$stmt = $con->prepare($query);

$stmt->execute();
$close = mysqli_close($con) 
or die("Ha sucedido un error inesperado en la desconexion de la base de datos");
header("Content-Type: application/json");
$json_string = json_encode($objetos,JSON_PRETTY_PRINT);
echo $json_string;
?>
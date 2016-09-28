<?php

function distance($Origen, $Destino) {

  $lat1=split(',',$Origen)[0];
 $lat2=split(',',$Destino)[0];
 $lon1=split(',',$Origen)[1];
 $lon2=split(',',$Destino)[1];
  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  

  return ($miles * 1.609344*1000);
}  
$con=mysqli_connect("localhost","u241352082_alexr","123456","u241352082_bdfin");
if (mysqli_connect_errno()) {
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$myLoc=$_GET["Loc"];
$id=$_GET["Linea"];
$query ="SELECT LatLong FROM Paradas WHERE IdLinea='$id'";
$distancias=array();
$objetos = array();
$result = mysqli_query($con, $query);

while($row = mysqli_fetch_array($result)) 
{ 
$LatLong=$row['LatLong'];	
	$objetos[] = array('Distancia' =>distance($LatLong,$myLoc),'LatLong' =>$LatLong) ;
	
}
$i=0;
foreach($objetos as $a)
{
$distancias[]=$a["Distancia"];
$i++;
}
$elegido=array();
$minimo=min($distancias);
foreach($objetos as $b)
{

     if($b["Distancia"]==$minimo)
   {
      $elegido= array('Distancia' =>$minimo,'LatLong' =>$b["LatLong"]) ;
   }
}

$close = mysqli_close($con) 
or die("Ha sucedido un error inesperado en la desconexion de la base de datos");
header("Content-Type: application/json");
$json_string = json_encode($elegido,JSON_PRETTY_PRINT);
echo $json_string;
	
echo distance(32.9697, -96.80322, 29.46786, -98.53506) . " Miles<br>";

?>	
<?php 

$host = "23.91.70.152";
$user = "luminou2_poller";
$pass = "stgPOLL@!";
$db = "luminou2_email_poller";

$conn = new mysqli($host, $user, $pass, $db);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$query = "SELECT * FROM user u, photo p where u.id = p.userid";
$result = $conn->query($query);

?>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>STG Photography Contest</title>
	<link rel="icon" type="image/x-icon" href="data:image/x-icon;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQEAYAAABPYyMiAAAABmJLR0T///////8JWPfcAAAACXBIWXMAAABIAAAASABGyWs+AAAAF0lEQVRIx2NgGAWjYBSMglEwCkbBSAcACBAAAeaR9cIAAAAASUVORK5CYII=" />

	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<link href='http://fonts.googleapis.com/css?family=Lato:400,400i' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="styles.css">
</head>
<body class="container">
<h1>STG Photo Contest Entries</h1>
<?php
$count = 0; 
while ($row = $result->fetch_assoc()) { 
	$count++;
	?>
	<div class="row">
		<div class="col-sm-3">
			<img src="<?=$row["imageURL"]?>" data-toggle="modal" data-target="#modal<?=$count?>" />
			<button class="btn btn-primary btn-block" data-toggle="modal" data-target="#modal<?=$count?>">Full Size</button>
		</div>
		<div class="col-sm-9">
			<h2><?=$row["name"]?> <span><?=$row["email"]?></span></h2>
			<div class="text">
				<h3><?=$row["subject"]?></h3>
				<p><?=$row["text"]?></p>
			</div>
		</div>
	</div>
<!-- Modal -->
<div id="modal<?=$count?>" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><?=$row["subject"]?></h4>
      </div>
      <div class="modal-body">
        <img src="<?=$row["imageURL"]?>" data-toggle="modal" data-target="#myModal" />
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<?php
}

?>
</body>
</html>
<div class="container auth">
	<h1 class="text-center">
		Bootstrap form theme <span>It's nice!</span>
	</h1>
	<div class="row">
		<div class="col-md-6">
			<div id="local-decision-form" class="">
				<form>
					<fieldset>

						<legend>Car Insurance Request</legend>

						<div class="form-group">
							<label class="control-label" for="name">Driver's Name</label>
							<div class="">
								<input id="name-input" name="name" value="Jane Doe" class="form-control input-md" type="text">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label" for="age">Driver's Age</label>
							<div class="">
								<input id="age-input" name="age" value="25" class="form-control input-md" type="number">
							</div>
						</div>

						<div class="form-group">
							<label class="control-label" for="make">Vehicle Make</label>
							<div class="">
								<select id="make" name="make" class="form-control">
									<option value="BMW">BMW</option>
									<option value="Honda">Honda</option>
								</select>
							</div>
						</div>

						<button type="submit" class="btn btn-primary btn-large" formmethod="post">Post Request</button>

					</fieldset>
				</form>
			</div>

		</div>
		<div class="col-md-6"></div>
	</div>

</div>
<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ClientAddressChangeTableSeeder extends Seeder {

	public function run()
	{
		$clients = Client::all();
		foreach ($clients as $index => $client) {
			$client->direccion = preg_replace( "/\r|\n/", "", $client->direccion);
			$client->save();
		}
	}

}
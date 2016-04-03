<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ClientUpdateTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		$clients = Client::all();
		foreach ($clients as $index => $client) {
			$client['cod_cliente'] = substr(md5(uniqid(rand(), true)),0,6);
			$client['latitud'] = $faker->latitude;
			$client['longitud'] = $faker->longitude;
			$client['razon_social'] = $faker->name;
			$client->save();
		}
	}

}
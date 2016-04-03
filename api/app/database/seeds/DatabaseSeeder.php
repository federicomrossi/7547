<?php

class DatabaseSeeder extends Seeder {

	/**
	 * Run the database seeds.
	 *
	 * @return void
	 */
	public function run()
	{
		Eloquent::unguard();
		$this->call('ClientTableSeeder');
		$this->call('ProductsTableSeeder');
		$this->call('CategoriesTableSeeder');
		$this->call('ClientUpdateTableSeeder');
		$this->call('ProductsUrlImagesTableSeeder');
		$this->call('UpdateClientsAndProductsTableSeeder');
		$this->call('CategoriesTableSeeder2');
		$this->call('ClientAddressChangeTableSeeder');
		// $this->call('UserTableSeeder');
	}

}

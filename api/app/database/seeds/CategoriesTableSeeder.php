<?php

use Faker\Factory as Faker;

class CategoriesTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		$categories = $this->getCategoriesToSeed();
		foreach($categories as $category)
		{
			Category::create([
				'nombre' => $category['nombre'],
				'activo' => $category['activo'],
				'descripcion' 	=> $faker->paragraph()
			]);
		}
	}

	private function getCategoriesToSeed()
	{
		return array(
				array('nombre' => 'Tecnología', 'activo' => '1'),
				array('nombre' => 'Indumentaria', 'activo' => '1'),
				array('nombre' => 'Hogar', 'activo' => '1'),
				array('nombre' => 'Música', 'activo' => '1'),
		);
	}

}
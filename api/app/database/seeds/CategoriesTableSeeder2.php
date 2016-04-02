<?php


class CategoriesTableSeeder2 extends Seeder {

	public function run()
	{
		Category::create([
			'nombre' => 'Cocina',
			'activo' => '1',
			'descripcion' 	=> 'Descripción de cocina'
		]);
	}

}
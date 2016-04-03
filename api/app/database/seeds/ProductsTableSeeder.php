<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ProductsTableSeeder extends Seeder {

	public function run()
	{
		$faker = Faker::create();
		$products = $this->getProductsForSeed();
		foreach($products as $product)
		{
			Product::create([
				'nombre'		=> $product['nombre'],
				'marca'			=> $product['marca'],
				'categoria' 	=> $product['categoria'],
				'precio'		=> $product['precio'],
				'stock'			=> $product['stock'],
				'descripcion' 	=> $faker->paragraph()
			]);
		}
	}

	private function getProductsForSeed()
	{
		return array(
				array('nombre' => 'Televisor 32 pulgadas', 'marca' => 'Philips', 'categoria' => 1, 'precio' => 16000, 'stock' => 15),
				array('nombre' => 'Televisor 45 pulgadas smart', 'marca' => 'Philips', 'categoria' => 1, 'precio' => 19000, 'stock' => 5),
				array('nombre' => 'Televisor 40 pulgadas', 'marca' => 'Samsung', 'categoria' => 1, 'precio' => 12000, 'stock' => 7),
				array('nombre' => 'Televisor 39 pulgadas smart', 'marca' => 'Daevoo', 'categoria' => 1, 'precio' => 30000, 'stock' => 8),
				array('nombre' => 'Remera talle 32', 'marca' => 'Nike', 'categoria' => 2, 'precio' => 415, 'stock' => 5),
				array('nombre' => 'Remera talle 40', 'marca' => 'Adidas', 'categoria' => 2, 'precio' => 500, 'stock' => 4),
				array('nombre' => 'Remera talle 41', 'marca' => 'Reebook', 'categoria' => 2, 'precio' => 450, 'stock' => 8),
				array('nombre' => 'Pantalón corto', 'marca' => 'Nike', 'categoria' => 2, 'precio' => 415, 'stock' => 5),
				array('nombre' => 'Pantalón largo de jean', 'marca' => 'Levis', 'categoria' => 2, 'precio' => 800, 'stock' => 5),
				array('nombre' => '4 Sillas', 'marca' => 'Hon', 'categoria' => 3, 'precio' => 2000, 'stock' => 3),
				array('nombre' => 'Mesa de madera', 'marca' => 'Hon', 'categoria' => 3, 'precio' => 2000, 'stock' => 0),
				array('nombre' => 'Mesa de pool', 'marca' => 'Pool', 'categoria' => 3, 'precio' => 2000, 'stock' => 0),
				array('nombre' => 'Amplificador Guitarra', 'marca' => 'Orange', 'categoria' => 4, 'precio' => 6599, 'stock' => 2),
		);
	}

}
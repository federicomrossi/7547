<?php

// Composer: "fzaninotto/faker": "v1.3.0"
use Faker\Factory as Faker;

class ProductsUrlImagesTableSeeder extends Seeder {

	public function run()
	{

		$urlsNormal = array('http://pinkman.mx/wp-content/uploads/2013/12/Apple-TV.png',
						'http://im-nova.com/img/articulos/1327917422.png');
		$urlsMini = array('https://www.walmartonline.com.ar/images/products/img_large/0779696260034L.jpg',
						'https://www.walmartonline.com.ar/images/products/img_large/0779696260399L.jpg',
						'https://www.walmartonline.com.ar/images/products/img_large/0779065305549L.jpg',);
		$this->saveProducts('Tele', $urlsNormal, $urlsMini);

		$urlsNormal = array('http://pinkman.mx/wp-content/uploads/2013/12/Apple-TV.png',
			'http://im-nova.com/img/articulos/1327917422.png');
		$urlsMini = array('https://www.walmartonline.com.ar/images/products/img_large/0779696260034L.jpg',
			'https://www.walmartonline.com.ar/images/products/img_large/0779696260399L.jpg',
			'https://www.walmartonline.com.ar/images/products/img_large/0779065305549L.jpg',);
		$this->saveProducts('Reme', $urlsNormal, $urlsMini);

		$urlsNormal = array('http://www.buenosairesrock.com.ar/Merch/TS32.jpg',
			'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTmSDRVmobWTHmOUgtf6h8yieHSPGAMnEdQATQneQPJOn3jEtkj');
		$urlsMini = array('http://mla-s1-p.mlstatic.com/remeras-musculosas-440811-MLA20634533784_032016-Y.jpg',
			'http://mla-s1-p.mlstatic.com/indumentaria-calzado-rugby-954511-MLA20566988816_012016-Y.jpg',
			'http://mla-s2-p.mlstatic.com/remeras-musculosas-206611-MLA20591623399_022016-Y.jpg');
		$this->saveProducts('Reme', $urlsNormal, $urlsMini);

		$urlsNormal = array('http://mla-s1-p.mlstatic.com/jeans-pantalones-jeans-calzas-201601-MLA20367900958_082015-Y.jpg',
			'http://mla-s1-p.mlstatic.com/jeans-pantalones-jeans-calzas-625011-MLA20451545712_102015-Y.jpg');
		$urlsMini = array('http://mla-s2-p.mlstatic.com/pantalon-biker-simil-cuero-con-spandex-y-bolsillos-activity-959511-MLA20589352928_022016-O.jpg',
			'http://mla-s2-p.mlstatic.com/pantalon-biker-simil-cuero-con-spandex-y-bolsillos-activity-959511-MLA20589352928_022016-O.jpg');
		$this->saveProducts('Pantal', $urlsNormal, $urlsMini);

		$urlsMini = array('http://mla-s1-p.mlstatic.com/juegos-comedor-4-sillas-sala-estar-comedor-155601-MLA20355626128_072015-S.jpg');
		$urlsNormal = array('http://mla-s2-p.mlstatic.com/mesa-y-sillas-comedor-wengue-guatambu-oferta-de-fabrica-240601-MLA20355624447_072015-O.jpg');
		$this->saveProducts('Mesa', $urlsNormal, $urlsMini);


		$urlsMini = array('http://mla-s1-p.mlstatic.com/juegos-comedor-4-sillas-sala-estar-comedor-155601-MLA20355626128_072015-S.jpg');
		$urlsNormal = array('http://mla-s1-p.mlstatic.com/4-silla-vestida-tapizada-en-chenille-390501-MLA20326632713_062015-O.jpg');
		$this->saveProducts('Silla', $urlsNormal, $urlsMini);

		$urlsMini = array('http://mla-s2-p.mlstatic.com/guitarra-amplificadores-710101-MLA20249658383_022015-S.jpg');
		$urlsNormal = array('http://mla-s1-p.mlstatic.com/amplificador-mini-puertos-usb-neoda-16522-MLA20123177787_072014-O.jpg');
		$this->saveProducts('Ampli', $urlsNormal, $urlsMini);


	}

	private function saveProducts($nameLike,$urlsNormal, $urlsMini)
	{
		$products = Product::where('nombre','like', "%".$nameLike.'%')->get();
		foreach ($products as $index => $product) {
			$product->url_image_normal = $urlsNormal[array_rand($urlsNormal)];
			$product->url_image_mini = $urlsMini[array_rand($urlsMini)];
			$product->save();
		}
	}

}
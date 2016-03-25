<?php

class Product extends \Eloquent {
	protected $fillable = ['nombre','descripcion', 'marca', 'categoria', 'stock', 'precio'];

	protected $table = 'productos';

	public static function getCollectionBy($filter,$value)
	{
		return self::where($filter, $value)
				->get();
	}
}
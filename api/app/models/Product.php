<?php

class Product extends Model {
	protected $fillable = ['nombre','descripcion', 'marca', 'categoria', 'stock', 'precio'];

	protected $table = 'productos';

	protected $allowedFilters = array('categoria', 'descripcion', 'marca', 'stock', 'precio','nombre');

	public static function getList($filters)
	{
		$model = new self;
		return $model->_getList($filters);
	}
}
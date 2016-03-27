<?php

class Category extends Model {
	protected $fillable = ['nombre', 'activo'];

	protected $table = 'categorias';

	protected $allowedFilters = array('nombre');

	public static function getList($filters)
	{
		$model = new self;
		return $model->_getList($filters);
	}
}
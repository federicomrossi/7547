<?php

class Client extends Model {
	protected $fillable = ['apenom', 'direccion','telefono', 'observaciones','id_vendedor','fecha_visita'];

	protected $table = "clientes";

	protected $allowedFilters = array('id_vendedor');

	public static function getList($filters)
	{
		$model = new self;
		return $model->_getList($filters);
	}

}
<?php

class Client extends Model {
	protected $fillable = ['apenom', 'direccion','telefono', 'observaciones','id_vendedor','fecha_visita',
							'razon_social', 'cod_cliente'];

	protected $table = "clientes";

	protected $allowedFilters = array('apenom', 'direccion','telefono', 'observaciones','id_vendedor','fecha_visita',
		'razon_social', 'cod_cliente');

	public $timestamps = false;

	public static function getList($filters)
	{
		$model = new self;
		return $model->_getList($filters);
	}

}
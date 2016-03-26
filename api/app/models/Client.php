<?php

class Client extends \Eloquent {
	protected $fillable = ['apenom', 'direccion','telefono', 'observaciones','id_vendedor',
			'fecha_visita'];

	protected $table = "clientes";

	protected static $filters = array(
		'id_vendedor' => '='
	);

	public static function getList($filters = array())
	{
		$clients = new self();
		foreach ($filters as $filter => $value){
			if (isset(self::$filters[$filter])){
				$clients = self::where($filter,self::$filters[$filter], (int)$value);
			}
		}
		return $clients->get();
	}

	public static function getSellerClient()
	{
		return self::getList(array('id_vendedor' => Input::get('s')));
	}

}
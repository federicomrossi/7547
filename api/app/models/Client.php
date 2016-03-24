<?php

class Client extends \Eloquent {
	protected $fillable = ['apenom', 'direccion','telefono', 'observaciones','id_vendedor',
			'fecha_visita'];

	protected $table = "clientes";


}
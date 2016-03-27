<?php

class Category extends \Eloquent {
	protected $fillable = ['nombre', 'activo'];

	protected $table = 'categorias';
}
<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddFieldsToClients extends Migration {

	/**
	 * Run the migrations.
	 *
	 * @return void
	 */
	public function up()
	{
		Schema::table('clientes', function ($table) {
			$table->string('cod_cliente');
			$table->double('latitud');
			$table->double('longitud');
			$table->string('razon_social');
		});
	}

	/**
	 * Reverse the migrations.
	 *
	 * @return void
	 */
	public function down()
	{
		Schema::table('clientes', function ($table) {
			$table->dropColumn('cod_cliente');
			$table->dropColumn('latitud');
			$table->dropColumn('longitud');
			$table->dropColumn('razon_social');
		});
	}

}

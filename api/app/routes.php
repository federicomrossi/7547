<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the Closure to execute when that URI is requested.
|
*/

/* Currently use post and get methods, but get will be deleted in future */
Route::post('/login', 'AuthController@login');
Route::get('/login', 'AuthController@login');
Route::resource('client', 'ClientController');
Route::get('/client/getFromToday/{sellerId}', 'ClientController@getFromToday');
Route::resource('products', 'ProductsController');
Route::resource('categories', 'CategoriesController');
Route::group(array('before' => 'tokenauth'), function()
{
    Route::get('/', 'HomeController@showHome');
});
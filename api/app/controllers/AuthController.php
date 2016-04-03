<?php

use Illuminate\Support\Facades\Auth as Auth;
use \Symfony\Component\HttpFoundation\Response as Response;
use Illuminate\Support\Facades\Validator as Validator;

class AuthController extends \BaseController {

	public function login()
	{
		$rules = [
				'username'    => 'required',
				'password' => 'required',
		];
		$validator = Validator::make(Input::all(), $rules);
		if ($validator->passes()) {
			$credentials = [
					'username'      => Input::get('username'),
					'password'   => Input::get('password'),
			];

			if ( Auth::attempt($credentials)) {
				$user = Auth::user();
				return $user->toJson();
			} else {
				//return Response::make('Unauthorized', 401);
			}
		} else {
			return $validator->messages()->toJson();
		}
	}
}

<?php
use Illuminate\Support\Facades\Auth as Auth;
class HomeController extends BaseController {

	public function showHome()
	{
		$user = Auth::user();

		exit($user->username);
	}

}

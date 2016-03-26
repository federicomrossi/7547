<?php

abstract class Model extends \Eloquent
{

	protected $allowedFilters = array();

	/* List custom method */

	public function _getList($filters = array())
	{
		$model = $this;
		$filtersToCompare =  self::getFilters($filters);
		foreach ($filtersToCompare as $filter){
			if (in_array($filter['name'],$model->allowedFilters)){
				$model = $model->where($filter['name'],$filter['compare'], $filter['value']);
			}
		}
		return $model->get();
	}

	/* prepare filters  */
	protected static function getFilters($data)
	{
		$filters = array();
		foreach ($data as $name => $value){
			if (strpos($name,'>') == false && strpos($name,'<') == false && strpos($name,'%') == false){
				$filters[] = array('name' => $name, 'compare' => "=", 'value' => $value);
				continue;
			}
			if ($name = self::explodeFilter($name, ">")){
				$filters[] = array('name' => $name, 'compare' => ">", 'value' => $value);
				continue;
			}
			if ($name = self::explodeFilter($name, "<")){
				$filters[] = array('name' => $name, 'compare' => "<=", 'value' => $value);
				continue;
			}
			if ($name = self::explodeFilter($name, "%")){
				$filters[] = array('name' => $name, 'compare' => 'like', 'value' => "%" . $value ."%");
				continue;
			}
		}
		return $filters;
	}

	private static function explodeFilter($filter, $compare)
	{
		$resultStrpos = strpos($filter,$compare);
		return $resultStrpos ? substr($filter,0,strlen($filter)-1) : $resultStrpos;
	}

	protected function getModel()
	{

	}
}
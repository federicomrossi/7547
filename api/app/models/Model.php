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
			if (in_array($filter['name'],$this->allowedFilters)){
				$model = $model->where($filter['name'],$filter['compare'], $filter['value']);
			}
		}
		if (isset($filters['orderby'])){
			$orderBy = $filters['orderby'];
			$orientation = isset($filters['orientation']) ? $filters['orientation'] : 'asc';
			$model = $model->orderBy($orderBy,$orientation);
		}
		return $model->get();
	}

	/* prepare filters  */
	protected static function getFilters($data)
	{
		$filters = array();
		foreach ($data as $filterName => $value){
			if (strpos($filterName,'>') == false && strpos($filterName,'<') == false && strpos($filterName,'%') == false){
				$filters[] = array('name' => $filterName, 'compare' => "=", 'value' => $value);
				continue;
			}
			if ($name = self::explodeFilter($filterName, ">")){
				$filters[] = array('name' => $name, 'compare' => ">", 'value' => $value);
				continue;
			}
			if ($name = self::explodeFilter($filterName, "<")){
				$filters[] = array('name' => $name, 'compare' => "<=", 'value' => $value);
				continue;
			}
			if ($name = self::explodeFilter($filterName, "%")){
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
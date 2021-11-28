package com.example.tweentyone

import java.util.*
import kotlin.collections.ArrayList

class Day17 {

    var res = arrayListOf<ArrayList<Int>>()


    //    var objects = ArrayList<List<Int>>()
    fun permute(nums: IntArray?): List<List<Int?>?>? {
        //回溯算法解决全排列
        var path = arrayListOf<Int>()
        // 路径、选择列表
        backTrace(path, nums)
        return null

    }

    private fun backTrace(path: ArrayList<Int>, nums: IntArray?) {
        if (path.size == nums?.size) {
            res.add(ArrayList(path))
//            objects.add(arrayListOf(path))
            return
        }


    }

}
package com.github.johnwr.leetcode

class TwoSumTest : JsonTestCase<TwoSum, List<Int>>() {
    override fun getActual(instance: TwoSum, args: Map<*, *>) = instance.solve(
        args["nums"] as List<Int>,
        args["target"] as Int
    )
}
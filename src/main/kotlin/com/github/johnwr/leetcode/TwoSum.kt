package com.github.johnwr.leetcode

/**
 * https://leetcode.com/problems/two-sum
 */
class TwoSum {
    fun solve(
        nums: IntArray,
        target: Int
    ): IntArray {
        val differences = mutableMapOf<Int, Int>()
        nums.forEachIndexed { index, it ->
            differences[it]?.let {
                return intArrayOf(it, index)
            }
            differences[target - it] = index
        }
        return null!!
    }

    fun solve(
        nums: List<Int>,
        target: Int
    ): List<Int> = solve(nums.toIntArray(), target).toList()
}
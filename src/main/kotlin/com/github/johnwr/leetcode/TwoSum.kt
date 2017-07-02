package com.github.johnwr.leetcode

/**
 * https://leetcode.com/problems/two-sum
 */
class TwoSum {
    fun solve(nums: Array<Int>, target: Int) = onePassHashTable(nums, target)

    /**
     * time: O(n)
     * space: O(n)
     */
    private fun onePassHashTable(
        nums: Array<Int>,
        target: Int
    ) : Array<Int> {
        val differences = mutableMapOf<Int, Int>()
        nums.forEachIndexed { index, it ->
            differences[it]?.let {
                return arrayOf(it, index)
            }
            differences[target - it] = index
        }
        throw IllegalArgumentException("No two sum solution")
    }
}
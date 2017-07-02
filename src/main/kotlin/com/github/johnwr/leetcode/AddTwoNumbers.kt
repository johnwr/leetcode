package com.github.johnwr.leetcode

/**
 * https://leetcode.com/problems/add-two-numbers
 */
class AddTwoNumbers {
    fun solve(
        l1: ListNode,
        l2: ListNode
    ) : ListNode {
        val result = ListNode(0)

        var current = result
        var carry = 0
        var a: ListNode? = l1
        var b: ListNode? = l2

        while ((a != null) || (b != null) || (carry != 0)) {
            val value = (a?.value ?: 0) + (b?.value ?: 0) + carry
            carry = value / 10
            current.next = ListNode(value % 10)
            current = current.next!!
            a = a?.next
            b = b?.next
        }

        return result.next!!
    }
}
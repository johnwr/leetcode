package com.github.johnwr.leetcode

/**
 * https://leetcode.com/problems/symmetric-tree
 */
class SymmetricTree {
    fun solve(root: TreeNode?) : Boolean {
        if (root == null) return true

        return isSymmetric(root.left, root.right)
    }

    private fun isSymmetric(
        left: TreeNode?,
        right: TreeNode?
    ) : Boolean {
        if (left == null) return right == null
        if (left.value != right?.value) return false

        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left)
    }
}
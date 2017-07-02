package com.github.johnwr.leetcode

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.CaseFormat
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import java.util.regex.Pattern

@RunWith(Parameterized::class)
class JsonTestCase {
    companion object {
        const val EXTENSION = "json"
        const val INFO_FILE = "info.$EXTENSION"
        const val METHOD_NAME = "solve"
        const val PACKAGE = "com.github.johnwr.leetcode"

        val mapper = ObjectMapper()

        @JvmField
        @Parameterized.Parameter(0)
        var problem: String = ""

        @JvmField
        @Parameterized.Parameter(1)
        var test: String = ""

        @JvmStatic
        @Parameterized.Parameters(name = "{0} => {1}")
        fun data() = Reflections(null, ResourcesScanner())
            .getResources(Pattern.compile(".*\\.$EXTENSION"))
            .filterNot {
                it.endsWith(INFO_FILE)
            }.map {
                val parts = it.split("/").toTypedArray()
                arrayOf(parts[0], parts[1].removeSuffix(".$EXTENSION"))
            }
    }

    @Test
    fun solve() {
        val className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, problem)
        val problemClass = Class.forName("$PACKAGE.$className")
        val instance = problemClass.newInstance()

        val method = problemClass.methods.filter {
            it.name == METHOD_NAME
        }.single()

        val values = mapper.readValue(
            javaClass.getResourceAsStream("/$problem/$test.$EXTENSION"),
            Map::class.java
        ).toMutableMap()

        val info = mapper.readValue(javaClass.getResourceAsStream("/$problem/$INFO_FILE"), Info::class.java)

        val args = mutableListOf<Any>()

        info.args.forEach {
            args.add(values.remove(it)!!.transform(method.parameterTypes[args.size]))
        }

        val actual = method.invoke(
            instance,
            *args.toTypedArray()
        )

        val expected = values.values.single()!!.transform(method.returnType)

        when (method.returnType) {
            Array<Int>::class.java -> Assert.assertEquals((expected as Array<Int>).toList(), (actual as Array<Int>).toList())
            else -> Assert.assertEquals(expected, actual)
        }
    }

    private fun <T> Any.transform(clazz: Class<T>): T = when (this.javaClass) {
        clazz -> this
        Integer::class.java -> this
        ArrayList::class.java -> when (clazz) {
            Array<Int>::class.java -> (this as ArrayList<Int>).toTypedArray()
            else -> throw RuntimeException("unable to transform, this: $this to clazz: $clazz")
        }
        else -> throw RuntimeException("unable to transform, this: $this to clazz: $clazz")
    } as T

    private data class Info (
        val args: List<String> = emptyList()
    )
}
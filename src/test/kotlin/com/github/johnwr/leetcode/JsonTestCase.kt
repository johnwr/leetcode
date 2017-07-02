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
abstract class JsonTestCase <in PROBLEM, out RESULT> {
    companion object {
        const val TYPE = "json"
        const val PACKAGE = "com.github.johnwr.leetcode"

        val mapper = ObjectMapper()

        @JvmField
        @Parameterized.Parameter(0)
        var problem: String = ""

        @JvmField
        @Parameterized.Parameter(1)
        var test: String = ""

        @JvmStatic
        @Parameterized.Parameters(name = "{1}")
        fun data() = Reflections(null, ResourcesScanner())
            .getResources(Pattern.compile(".*\\.$TYPE"))
            .map {
                val parts = it.split("/").toTypedArray()
                arrayOf(parts[0], parts[1].removeSuffix(".$TYPE"))
            }
    }

    abstract fun getActual(instance: PROBLEM, args: Map<*, *>): RESULT

    @Test
    fun assertEquals() {
        val instance = getInstance()
        val args = mapper.readValue(javaClass.getResourceAsStream("/$problem/$test.$TYPE"), Map::class.java)

        Assert.assertEquals(args["expected"] as RESULT, getActual(instance, args) as RESULT)
    }

    private fun getInstance() : PROBLEM {
        val className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, problem)
        val clazz = Class.forName("$PACKAGE.$className")
        return clazz.newInstance() as PROBLEM
    }
}
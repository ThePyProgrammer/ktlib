package com.thepyprogrammer.ktlib.io

import com.thepyprogrammer.ktlib.io.dummy.str
import java.util.function.Function
import java.util.regex.MatchResult
import java.util.regex.Pattern
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class HTMLManager(html: String) {
    private lateinit var variables: MutableSet<String>
    private lateinit var map: HashMap<String, String>
    private lateinit var html: String
    private lateinit var filePath: String
    private lateinit var file: KFile
    private lateinit var actualFile: KFile
    private val p = Pattern.compile("\\$([a-zA-Z_]\\w*)")
    private val css = Pattern.compile("<link rel=\"stylesheet\" href=\"(.*)\">")
    private val url = Pattern.compile("url\\(\"(.*)\"\\)")
    private val js = Pattern.compile("<script type=\"text/javascript\" src=\"(.*)\"></script>")

    constructor(file: KFile) : this(file.read()) {
        file.close()
        this.file = file.parentFile
        actualFile = file
        val arr = file.absolutePath.split("\\\\").toTypedArray()
        arr[arr.size - 1] = ""
        filePath = java.lang.String.join("\\", *arr.copyOfRange(0, arr.size - 2))
        processFile()
    }


    fun processFile() {
        // replaceItAll(url, "url(\"%s\")", s -> File.relative(this.file, s));
        val m = css.matcher(html)
        html = m.replaceAll { matchResult: MatchResult ->
            val location: String = KFile.relative(file, matchResult.group(1))
            var stylesheet: String = KFile.readFrom(location)
            val loc = KFile(location).parentFile
            val urlMatcher = url.matcher(stylesheet)
            stylesheet =
                urlMatcher.replaceAll { match: MatchResult ->
                    val s = "url(\"" + KFile.relative(KFile(loc), match.group(1)) + "\")"
                    s.replace("\\\\".toRegex(), "\\\\\\\\\\\\\\\\")
                }
            loc.close()
            String.format("<style type=\"text/css\">%n%s%n</style>", stylesheet)
        }
        replaceItAll(js, "<script type=\"text/javascript\">%n%s%n</script>"
        ) { s: String -> KFile.readFrom(file.absolutePath + "\\" + s) }
    }

    fun replaceItAll(p: Pattern, formatter: String, function: Function<String, String>) {
        val m = p.matcher(html)
        html = m.replaceAll { matchResult: MatchResult ->
            String.format(
                formatter, function.apply(matchResult.group(1))
            )
        }
    }

    fun substitute(original: String, newer: String): HTMLManager {
        if (variables.contains(original)) {
            map[original] = newer
        }
        return this
    }

    override fun toString(): String {
        val text = str(html)
        map.forEach { (key: String, value: String?) ->
            text.replaceAll(
                "\\$$key",
                value
            )
        }
        return text.toString()
    }

    init {
        val m = p.matcher(html)
        variables = HashSet()
        map = HashMap()
        while (m.find()) {
            variables.add(m.group(1))
        }
        for (variable in variables) map[variable] = ""
        this.html = html
    }
}
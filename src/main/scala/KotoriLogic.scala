/**
 * Created by masaakif on 2015/02/19.
 */

import util.parsing.combinator.RegexParsers

// '[GUI-1234]' -> '=hyperlink("http://jira/browse/GUI-1234","[GUI-1234]")'                  $e1
class KotoriLogic extends RegexParsers {
	override val skipWhitespace = false
	def jiraID = ("[" ~> """\w+-\d+""".r <~ "]") ^^ {x => "=hyperlink(\"http://jira/browse/"+x+"\",\""+x+"\")"}
	def separator = """ - """.r
	def desc = """ - .+""".r ^^ {_.replaceFirst(" - ","\t")}
  def jiraLine = jiraID | (jiraID ~ desc)
  def eol = "\r" | "\r\n" | "\n"
	def fields = repsep(jiraLine , eol)

	def parse(input: String) = parseAll(fields, input).get.mkString("\t")
}



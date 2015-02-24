import sbtassembly.Plugin._
import AssemblyKeys._

name := "Kotori"

version := "1.0"

scalaVersion := "2.11.5"

resolvers ++= Seq(
	Resolver.url(
		"DefaultMavenRepository", url("http://repo1.maven.org/maven2/"))(Resolver.ivyStylePatterns)
	)

libraryDependencies ++= Seq(
	"org.specs2" %% "specs2-core" % "2.4.15" % "test"
	, "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"
	, ("org.seleniumhq.selenium" % "selenium-htmlunit-driver" % "2.44.0")
		.exclude("org.apache.commons","commons-exec")
		.exclude("xalan","serializer")
		.excludeAll(
		ExclusionRule(organization = "cglib"),
		ExclusionRule(organization = "com.google.code.gson"),
		ExclusionRule(organization = "com.google.guava"),
		ExclusionRule(organization = "org.eclipse.jetty"),
		ExclusionRule(organization = "net.java.dev.jna")
		)
)

// Build exe file
val jar2exe = taskKey[Unit]("Use jsmooth to create exe from jar")

jar2exe := { import scala.sys.process.Process
	val jsmooth = """C:\D-Drive\developments\environments\Java\jsmooth\jsmoothcmd.exe"""
	val jsmoothxml = s"${baseDirectory.value}\\${name.value}.jsmooth"
	Process(s"$jsmooth $jsmoothxml").run()
  println(s"Execute $jsmooth $jsmoothxml}")
	println(s"Finished making ${name.value} exe file by jsmoot.") }

val launch4j = taskKey[Unit]("Use launch4j to create exe from jar")

launch4j := { import scala.sys.process.Process
	val l4j = "\"C:\\Program Files (x86)\\Launch4j\\launch4jc.exe\""
	val l4jxml = s"${baseDirectory.value}\\${name.value}_l4j.xml"
	Process(s"${l4j} ${l4jxml}").run()
	println(s"Execute ${l4j} ${l4jxml}")
	println(s"Finished making ${name.value} exe file by launch4j.") }
// Test
scalacOptions in Test ++= Seq("-Yrangepos")

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)
//testOptions in Test += Tests.Argument("junitxml", "html", "console")

// Assembly
assemblySettings

jarName := name.value + "_fat.jar"

test in assembly := {}

val assemblyAll = taskKey[Unit]("Do assembly then jar2exe")

assemblyAll <<= launch4j dependsOn assembly

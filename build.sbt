import sbtassembly.Plugin._
import AssemblyKeys._

name := "Kotori"

version := "1.0"

scalaVersion := "2.11.5"


resolvers ++= Seq(
	Resolver.url(
		"DefaultMavenRepository", url("http://repo1.maven.org/maven2/"))(Resolver.ivyStylePatterns)
	)

// Build exe file
val jar2exe = inputKey[Unit]("Use jsmooth to create exe from jar")

jar2exe := { import scala.sys.process.Process
	val jsmooth = """C:\D-Drive\developments\environments\Java\jsmooth\jsmoothcmd.exe"""
	Process(s"$jsmooth ${baseDirectory.value}\\${name.value}.jsmooth").run()
  println(s"Execute $jsmooth ${baseDirectory.value}\\${name.value}.jsmooth")
	println(s"Finished making ${name.value} exe file.") }

// Test
libraryDependencies ++= Seq(  "org.specs2" %% "specs2-core" % "2.4.15" % "test"
													,   "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3")

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)
//testOptions in Test += Tests.Argument("junitxml", "html", "console")

// Assembly
assemblySettings

jarName := name.value + "_fat.jar"

test in assembly := {}

val assemblyAll = inputKey[Unit]("Do assembly then jar2exe")

assemblyAll <<= jar2exe dependsOn assembly
package services

import java.io.File
import scala.sys.process.ProcessLogger
import scala.sys.process.Process
import services.CommandCreator

object FileRunner {

  def run(filePath: File): (List[String], List[String], Int) = {
    val process = Process(CommandCreator.createCommand("scala", List(filePath.getAbsolutePath)))
    var outputList = List[String]()
    var errorList = List[String]()
    val exitCode = process ! ProcessLogger((s) => outputList ::= s, (s) => errorList ::= s)
    if(exitCode != 0) {
      println("ExitCode !=0, Error: "+ errorList)
    }

    return (outputList.reverse, errorList.reverse, exitCode)
  }

}

object CommandCreator {

  def createCommand(commandName: String, args: List[String]): String = {
    println("OSSystem: " + System.getProperty("os.name").toLowerCase())
    var cmd = commandName
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      cmd = cmd + ".bat "
    }
    for(arg <- args) {
      cmd = cmd + " " + arg
    }
    println("Return command: " + cmd)

    return cmd;
  }

}
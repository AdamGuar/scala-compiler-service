package services

import java.io.File
import scala.sys.process.ProcessLogger
import scala.sys.process.Process
import model.CodeEntity

object FileRunner {

  def run(codeEntity: CodeEntity , appDir: File): (List[String], List[String], Int)= {

    val targetDir = new File(appDir+"/code-target/"+codeEntity.id)
    val fileName = targetDir.listFiles().filterNot(_.getName.contains("$"))(0).getName
    val process = Process(CommandCreator.createCommand("scala ") +fileName.replace(".class", ""), targetDir)
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

  def createCommand(commandName: String): String = {
    println("OSSystem: " + System.getProperty("os.name").toLowerCase())
    var cmd = commandName
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      cmd = cmd + ".bat "
    }

    return cmd;
  }

}
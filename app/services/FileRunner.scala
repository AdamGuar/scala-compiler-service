package services

import java.io.File
import scala.sys.process.ProcessLogger
import scala.sys.process.Process
import model.CodeEntity


/**
  * Singleton object responsible for running previously compiled CodeEnitity object and returning output
  *
  * @author Karol Skóra
  *
  */

class FileRunner {

  /**
    * Method run previously compiled CodeEnitity object and returns output, errors and exit code
    * @param CodeEntity CodeEntity object which was compiled with success and is ready to run
    * @param appDir File object with application working directory, will be used to create target directory for the process
    * @return (outputList,errorList,exitCode) touple
    */
  def run(codeEntity: CodeEntity , appDir: File): (List[String], List[String], Int)= {

    val targetDir = new File(appDir+"/code-target/"+codeEntity.id)
    val fileName = targetDir.listFiles().filterNot(_.getName.contains("$"))(0).getName
    val process = Process(CommandCreator.createCommand("scala")+fileName.replace(".class", ""), targetDir)
    var outputList = List[String]()
    var errorList = List[String]()
    val exitCode = process ! ProcessLogger((s) => outputList ::= s, (s) => errorList ::= s)
    if (exitCode!=0) {
      println("errorList: " + errorList)
    }
    return (outputList.reverse, errorList.reverse, exitCode)
  }

}

/**
  * Singleton object responsible for creating command depending on user's opeating system
  *
  * @author Karol Skóra
  *
  */
object CommandCreator {

  /**
    * Method takes command string as parameter, checks user's operating system and create full command with or without extension
    * @param commandName String object with command name without any extensions
    * @return full command name
    */
  def createCommand(commandName: String, osName:String = null): String = {
    var os = osName
    if(os == null) {
      os = System.getProperty("os.name")
    }
    if (os.toLowerCase().contains("win")) {
      return commandName + ".bat "
    }else {
      return commandName+" ";
    }
  }

}
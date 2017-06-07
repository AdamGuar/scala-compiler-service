package services
import java.io.File

import model.CodeEntity


/**
 * Class takes a File object, which will be used when running compile method, as constructor param 
 * 
 * @author Adam
 * @param CodeEntity Given entity will be compiled by compile() method
 * @param File Given parameter provides working directory path for compile() method
 */
class CodeCompiler(codeEntityParam: CodeEntity , appWorkingDir: File) {
  var codeEntity: CodeEntity = codeEntityParam
  var workingDir: File = appWorkingDir
  
  
  /**
   * Method compiles given entity given in constructor to .class file
   * It creates directory structure: /WORKING_DIRECTORY/code-target/codeEntity.id where .class file is created
   * 
   * @author Adam
   * @return .class File which can be run using "scala FILE_NAME" command, in case of compilation fail it returns null
   */
  def compile() : File ={
    println("Code entity: "+codeEntity.file.toString())
    val targetDir = new File(workingDir+"/code-target/"+codeEntity.id)
    targetDir.mkdirs()
    println("OSSystem: " + System.getProperty("os.name").toLowerCase())
    val compilationCommad = if (System.getProperty("os.name").toLowerCase().contains("win"))  "scalac.bat " else "scalac "
    println("compilation command: "+compilationCommad)  
    
    val pro1 = sys.process.Process(compilationCommad +codeEntity.file.toString(), targetDir)
    if (pro1.! == 0){
      val matchingFiles = targetDir.listFiles()
      println("Code compiled, returned file compiled class name")
      return matchingFiles(1)
    }
    else
      println("compilation failed")
      return null
  }
  
}
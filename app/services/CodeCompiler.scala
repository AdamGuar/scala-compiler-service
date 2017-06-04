package services
import java.io.File

import model.CodeEntity

class CodeCompiler(codeEntityParam: File) {
  var codeEntity: File = codeEntityParam
  
  def compile(){
    println("Code entity: "+codeEntity.toString())
    //val pro1 = sys.process.Process("scalac "+codeEntity.toString())
    //
    val pro1 = sys.process.Process("scalac.bat " +codeEntity.toString(), new File("D:/scala/target"))
    
    if (pro1.! == 0){
      val pro2 = sys.process.Process("scala.bat HelloWorld",new File("D:/scala/target"))
      println(pro2.!!)  // "Hello World"
    }
    else
      println("compile failed")
  }
  
}
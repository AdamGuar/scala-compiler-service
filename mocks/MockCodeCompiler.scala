package mocks

import java.io.File
import model.CodeEntity
import services.CodeCompiler
/**
  * Mock CodeCompiler
  *
  * @author Karol SKóra

  */
class MockCodeCompiler extends CodeCompiler{


  /**
    * Method fakes compiling given entity given in constructor to .class file
    * It creates directory structure: /WORKING_DIRECTORY/code-target/codeEntity.id where .class file is created
    *
    * @author Adam
    * @return .class File which can be run using "scala FILE_NAME" command, in case of compilation fail it returns null
    */
  def compile(codeEntity: CodeEntity , workingDir: File) : File ={

    if(codeEntity != null && workingDir !=null ) {
      return new File(System.getProperty("user.dir"))
    }else {
      return null
    }
  }

}
package mocks

import java.io.File
import scala.sys.process.ProcessLogger
import scala.sys.process.Process
import model.CodeEntity
import services.FileRunner

/**
  * Mock FileRunenr
  *
  * @author Karol Skóra
  *
  */

class MockFileRunner extends FileRunner{

  /**
    * Method fake run previously compiled CodeEnitity object and returns output, errors and exit code
    * @param CodeEntity CodeEntity object which was compiled with success and is ready to run
    * @param appDir File object with application working directory, will be used to create target directory for the process
    * @return (outputList,errorList,exitCode) touple
    */
  def run(codeEntity: CodeEntity , appDir: File): (List[String], List[String], Int)= {

    if(codeEntity!=null && appDir != null) {
      return (new List(), new List(), 0)
    }else {
      return (new List(), new List(), 1)
    }
  }

}
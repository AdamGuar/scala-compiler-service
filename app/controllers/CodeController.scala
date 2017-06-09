package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import play.api.libs.json._
import play.api.libs.functional.syntax._
import services.FilenameGenerator
import services.CodeCompiler
import model.CodeEntity
import services.FileRunner

@Singleton
class CodeController @Inject() extends Controller{
  
  val CODE_WORKING_DIRECTORY = System.getProperty("user.dir") + "/code/";
  
  def upload = Action(parse.multipartFormData) { request =>
  request.body.file("code").map { code =>
    import java.io.File
    val filename = code.filename
    val contentType = code.contentType
    //code.ref.moveTo(new File(s"/tmp/code/$filename"))
    val generator = new FilenameGenerator
    val fileID = generator.generateFileName()
    val file:File = new File(CODE_WORKING_DIRECTORY+fileID);
    code.ref.moveTo(file)
    val codeEntity = new CodeEntity(fileID,file);
    val compiler = new CodeCompiler(codeEntity,new File(System.getProperty("user.dir")))  
    val returnedFile=compiler.compile()
    if(returnedFile==null) BadRequest("Compilation Fail")
    println("Returned file = " + returnedFile.getAbsolutePath)
    val output = FileRunner.run(returnedFile)
    if(output._3 != 0) {
      BadRequest("Exit code != 0")
    } else {
      Ok("File uploaded, compiled and run. Output:" + output._1)
    }
  }.getOrElse {
    BadRequest("File missing")
  }
}
}

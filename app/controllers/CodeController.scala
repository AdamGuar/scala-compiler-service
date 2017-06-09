package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import play.api.libs.json._
import play.api.libs.functional.syntax._
import services.FilenameGenerator
import services.CodeCompiler
import model.CodeEntity

@Singleton
class CodeController @Inject() extends Controller{
<<<<<<< HEAD

  val CODE_WORKING_DIRECTORY = "D:/scala/code/";

  implicit val rds = (
    (__ \ 'name).read[String]
  )

  def getCode = Action{
    Ok("Hello there")
    }

  def sayHello = Action { request =>
    request.body.asJson.map { json =>
      json.validate[(String)].map{
        case (name) => Ok("Hello " + name)
      }.recoverTotal{
        e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }


=======
  
  val CODE_WORKING_DIRECTORY = System.getProperty("user.dir") + "/code/";
  

  
  
>>>>>>> devel
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
    Ok("File uploaded and compiled")
  }.getOrElse {
    BadRequest("File missing")
  }
}


}

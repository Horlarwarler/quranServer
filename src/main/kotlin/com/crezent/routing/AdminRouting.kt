package com.crezent.routing

import com.crezent.data.repository.QuranInterfaceRepo
import com.crezent.domain.model.ErrorResponse
import com.crezent.domain.model.AyahModel
import com.crezent.domain.model.TokenModel
import com.crezent.domain.model.UserModel
import com.crezent.jwt.JwtClaim
import com.crezent.jwt.JwtConfig
import com.crezent.jwt.JwtInterface
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.adminRouting(
    quranInterfaceRepo: QuranInterfaceRepo,
    jwtInterface: JwtInterface
){


   authenticate {
       put("/update") {
           try {
               val receivedUpdated = call.receive<List<AyahModel>>()
               quranInterfaceRepo.updateQuran(receivedUpdated)
               call.respond(HttpStatusCode.OK)

           }
           catch (e:Exception){
               val errorMessage = e.message?:"Unknown Error "
               call.respond(HttpStatusCode.BadRequest, errorMessage)
           }
       }

       get("/authenticate"){
           call.respond(HttpStatusCode.OK)
       }

       put("/update-version"){
           try{
               quranInterfaceRepo.updateVersion()
               call.respond(HttpStatusCode.OK)
           }
           catch(error:Exception){
               val errorMessage = error.message?:"Unknown Error "
               call.respond(HttpStatusCode.BadRequest, errorMessage)
           }
       }

   }


    post("/login") {
       try {
           val userModel = call.receive<UserModel>()
           val envPassword = System.getenv("PASSWORD")!!
           val envUsername = System.getenv("USER_NAME")!!
           if(userModel.username != envUsername || userModel.password != envPassword){
               call.respond(HttpStatusCode.NotFound , "UserName or Password is Invalid")
               return@post
           }
           val jwtClaim = JwtClaim(
               name = "username",
               value = userModel.username
           )
           val secretKey = System.getenv("SECRET_KEY")!!
           val domain = "https://jwt-provider-domain/"
           val audience = "jwt-audience"
           val issuer = "jwt-issuer"
           val expireTime = 30L * 24L * 60L * 60L * 1000L
           val config = JwtConfig(
               issuer = issuer,
               audience = audience,
               expireTime = expireTime,
               secretKey = secretKey
           )
           val token = jwtInterface.generateJwt(
               jwtClaim = jwtClaim,
               jwtConfig = config
           )
           val tokenModel = TokenModel(
               token = token
           )

           call.respond(HttpStatusCode.OK, tokenModel)
       }
       catch (e:Exception){
           val errorMessage = e.message?:"Unknown Error Message"
               call.respond(HttpStatusCode.BadRequest, ErrorResponse(errorMessage  = errorMessage))
       }
    }

}
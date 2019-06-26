package com.kdpark0723.communityCommon.controllers.auth

import com.kdpark0723.communityCommon.models.ResponseForm
import com.kdpark0723.communityCommon.models.user.dataAccessObject.UserDataAccess
import com.kdpark0723.communityCommon.models.user.dataAccessObject.UserDataAccessAdapterUserRepository
import com.kdpark0723.communityCommon.models.user.dataTransferObject.SignUpElement
import com.kdpark0723.communityCommon.models.user.dataTransferObject.SignUpResponseForm
import com.kdpark0723.communityCommon.models.user.dataTransferObject.SignUpUser
import com.kdpark0723.communityCommon.services.auth.SignUpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import javax.validation.Valid

@Controller
@RequestMapping(path = ["/auth/sign_up"])
class SignUpController {
    @Autowired
    private val userDataAccess: UserDataAccess = UserDataAccessAdapterUserRepository()
    @Autowired
    private val signUpService: SignUpService = SignUpService(userDataAccess)

    @RequestMapping(value = ["/check"], method = [RequestMethod.POST])
    @ResponseBody
    fun checkElementValid(@RequestBody element: SignUpElement): ResponseEntity<ResponseForm> {
        try {
            return checkElementValidUncheckException(element)
        } catch (e: Exception) {
            throw e
        }
    }

    fun checkElementValidUncheckException(element: SignUpElement): ResponseEntity<ResponseForm> {
        signUpService.checkValid(element)

        return ResponseEntity(ResponseForm("This value is valid"), HttpStatus.OK)
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    @ResponseBody
    fun signUp(@Valid @RequestBody signUpUserData: SignUpUser): ResponseEntity<SignUpResponseForm> {
        try {
            return signUpUncheckException(signUpUserData)
        } catch (e: Exception) {
            throw e
        }
    }

    fun signUpUncheckException(signUpUserData: SignUpUser): ResponseEntity<SignUpResponseForm> {
        val signUpResponse = signUpService.signUp(signUpUserData.toUser())

        return ResponseEntity(signUpResponse, HttpStatus.CREATED)
    }

}
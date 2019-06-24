package com.kdpark0723.communityCommon.controllers.auth

import com.kdpark0723.communityCommon.exceptions.UserAlreadySignedException
import com.kdpark0723.communityCommon.models.user.UserRepository
import com.kdpark0723.communityCommon.models.user.dto.SignInData
import com.kdpark0723.communityCommon.models.user.dto.SignInRespoense
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
@RequestMapping(path = ["/auth"])
class AuthController {
    @Autowired
    private val userRepository: UserRepository? = null

    @RequestMapping(value = ["/signin"], method = [RequestMethod.POST])
    @ResponseBody
    fun signIn(@Valid @RequestBody signInUser: SignInData): ResponseEntity<SignInRespoense> {
        val user = signInUser.toUser()

        if (userRepository!!.findById(user.identifier!!).isPresent)
            throw UserAlreadySignedException()

        userRepository.save(user)

        return ResponseEntity(SignInRespoense(user), HttpStatus.CREATED)
    }
}
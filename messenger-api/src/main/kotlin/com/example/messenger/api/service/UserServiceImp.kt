package com.example.messenger.api.service

import com.example.messenger.api.exception.InvalidUserIdException
import com.example.messenger.api.exception.UserStatusEmptyException
import com.example.messenger.api.exception.UsernameUnavailableException
import com.example.messenger.api.models.User
import com.example.messenger.api.repositories.UserRepository
import kotlin.jvm.Throws
import org.springframework.stereotype.Service

@Service
class UserServiceImp(val repository: UserRepository): UserService {

    @Throws(UsernameUnavailableException::class)
    override fun attemptRegistration(userDetails: User): User {
        if(!usernameExist(userDetails.username)){
            val user = User()
            user.username = userDetails.username
            user.phoneNumber = userDetails.phoneNumber
            user.password = userDetails.password
            repository.save(user)
            obscurePassword(user)
            return user
        }
        throw UsernameUnavailableException("The username ${userDetails.username} is unavailable")
    }

    @Throws(UserStatusEmptyException::class)
    fun updateUserStatus(currentUser: User,updateDetails: User): User{
        if(!updateDetails.status.isEmpty()){
            currentUser.status = updateDetails.status
            repository.save(currentUser)
            return currentUser
        }
        throw UserStatusEmptyException()
    }


    override fun listUsers(currentUser: User): List<User> {
        return repository.findAll().mapTo(ArrayList(), {it})
        .filter { it != currentUser }
    }

    override fun retrieveUserData(username: String): User? {
        val user = repository.findByUsername(username)
        obscurePassword(user)
        return user
    }

    @Throws(InvalidUserIdException::class)
    override fun retrieveUserData(id: Long): User {
        val userOptional = repository.findById(id)
        if (userOptional.isPresent){
            val user = userOptional.get()
            obscurePassword(user)
            return user
        }
        throw InvalidUserIdException("A user with an id of ${id} does not exist")
    }

    override fun usernameExist(username: String): Boolean {
        return repository.findByUsername(username) != null
    }

    private fun obscurePassword(user: User?){
        user?.password = "XXX XXX XXX"
    }
}
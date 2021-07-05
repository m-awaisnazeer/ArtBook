package com.communisolve.artbook.util

data class Resource<out T>(
    var stsus: Status,
    val data: T?,
    val message: String?
) {

    companion object{

        fun <T> success(data:T?):Resource<T>{
            return Resource(Status.SUCCESS,data,null)
        }

        fun <T> error(msg:String,data:T?):Resource<T>{
            return Resource(Status.ERROR,data,msg)
        }

        fun <T> loading(data:T?):Resource<T>{
            return Resource(Status.LOADING,data,null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
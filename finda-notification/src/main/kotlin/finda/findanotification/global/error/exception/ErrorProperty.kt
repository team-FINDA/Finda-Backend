package finda.findanotification.global.error.exception

interface ErrorProperty {

    fun status(): Int

    fun message(): String

    fun code(): String
}

package finda.error

interface ErrorProperty {

    fun status(): Int

    fun message(): String

    fun code(): String
}

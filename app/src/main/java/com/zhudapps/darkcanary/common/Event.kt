package com.zhudapps.darkcanary.common

/**
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
class Event<out T>(private val content: T) {

    var hasBeenHanded = false
        private set //immutable allowing external read but no write

    /**
     * Set flag to ensure that we only "handle" on instance of LiveData at a time
     */
    fun getContentIfNotHandled(): T? {
        return if(hasBeenHanded) {
            null
        } else {
            hasBeenHanded = true
            content
        }
    }

    /**
     * Returns content, if it's already been handled
     */
    fun peekContent() : T = content
}
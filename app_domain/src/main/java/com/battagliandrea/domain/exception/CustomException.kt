package com.battagliandrea.domain.exception


/**
 * @param errorCode could be:
 *  0 --> generic error
 *  1 --> server error
 *  2 --> no internet connection
 *  3--> database error
 */
class CustomException : Exception {

    var errorCode: Int = 0

    constructor(errorCode: Int) : super() {
        this.errorCode = errorCode
    }
}
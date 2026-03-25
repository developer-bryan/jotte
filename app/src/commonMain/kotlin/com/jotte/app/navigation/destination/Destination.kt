package com.jotte.app.navigation.destination

/**
 * Used to mark a function as containing a destination.
 * This will be used by code-analysis to suppress certain issues such as
 * 'FunctionNaming'
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Destination

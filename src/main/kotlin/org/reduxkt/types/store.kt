package org.reduxkt.types

typealias Dispatch = (Action) -> Unit
typealias Subscription<State> = (State, Dispatch) -> Unit
typealias Unsubscribe = () -> Unit

interface Store<State> {
    fun subscribe(subscription: Subscription<State>): Unsubscribe
}
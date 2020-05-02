package org.reduxkt.types

typealias Subscription<State> = (State) -> Unit

interface Store<State> {
    fun getState(): State
    fun dispatch(action: Action)
    fun subscribe(subscription: Subscription<State>)
    fun unsubscribe(subscription: Subscription<State>)
}
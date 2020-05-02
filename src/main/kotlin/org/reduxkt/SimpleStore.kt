package org.reduxkt

import org.reduxkt.types.Action
import org.reduxkt.types.Reducer
import org.reduxkt.types.Store
import org.reduxkt.types.Subscription

abstract class SimpleStore<State>(
    private val initialState: State,
    private val reducers: List<Reducer<State>>
): Store<State> {
    private var currState: State = initialState
    private val subscriptions = arrayListOf<Subscription<State>>()

    override fun getState() = currState

    override fun dispatch(action: Action) {
        val newState = applyReducers(currState, action)
        if (newState == currState) {
            return
        }
        currState = newState
        subscriptions.forEach { it(currState) }
    }

    override fun subscribe(subscription: Subscription<State>) {
        subscriptions.add(subscription)
        subscription(currState) // notify curr state
    }

    override fun unsubscribe(subscription: Subscription<State>) {
        subscriptions.remove(subscription)
    }

    private fun applyReducers(current: State, action: Action): State {
        var newState = currState
        for (reducer in reducers) {
            newState = reducer(newState, action)
        }

        return newState
    }
}
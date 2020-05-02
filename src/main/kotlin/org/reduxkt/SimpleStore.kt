package org.reduxkt

import org.reduxkt.types.*

abstract class SimpleStore<State>(
    private val initialState: State,
    private val reducers: List<Reducer<State>>
): Store<State> {
    private var currState: State = initialState
    private val subscriptions = arrayListOf<Subscription<State>>()

    private fun dispatch(action: Action) {
        val newState = applyReducers(currState, action)
        if (newState == currState) {
            return
        }
        currState = newState
        subscriptions.forEach { it(currState, ::dispatch) }
    }

    override fun subscribe(subscription: Subscription<State>): Unsubscribe {
        subscriptions.add(subscription)
        subscription(currState, ::dispatch) // notify curr state
        return { subscriptions.remove(subscription) }
    }

    private fun applyReducers(current: State, action: Action): State {
        var newState = currState
        for (reducer in reducers) {
            newState = reducer(newState, action)
        }

        return newState
    }
}
package com.danielks.shoppinglist.navigation

object Destinations {
    const val ABOUT = "about"
    const val CREATE_LIST = "create_list"
    const val LISTS = "lists"
    const val LIST_DETAIL = "list_detail/{listId}"
    const val FINALIZED_LISTS = "finalized_lists"
    const val FINALIZED_LIST_DETAIL = "finalized_list_detail/{listId}"
    const val NOT_FOUND = "not_found"
    const val API_DOWN = "api_down"

    fun listDetail(listId: String) = "list_detail/$listId"
    fun finalizedListDetail(listId: String) = "finalized_list_detail/$listId"
}
package com.issever.cryptodiary.util

class Constants {

    object Remote {
        const val MAIN_URL = "https://www.isseverapps.com/"
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
        const val COINS_LIST = "coins/markets"
        const val COIN = "coins/{id}/market_chart"
        const val COIN_DETAIL = "coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false"
        const val NFT_DETAIL = "nfts/{id}"
        const val TRENDING = "search/trending"
        const val DEFAULT_TARGET_CURRENCY = "usd"
        const val VS_CURRENCY = "vs_currency"
        const val ID = "id"
        const val IDS = "ids"
        const val DAYS = "days"
    }

    object Firebase {
        const val USER_COLLECTION = "user"
        const val WEB_CLIENT_ID = "YOUR_WEB_CLIENT_ID"
        const val FAVORITES = "favorites"
    }

    object LocalData {
        const val FILE_NAME = "LocalData"
        const val TABLE_NAME = "coins"
        const val DATABASE_VERSION = 1
        const val FOLLOW_SYSTEM = "follow system"
        const val DEFAULT_LANG = "default language"
        const val ON_BOARDING_STATUS = "on boarding status"
        const val LANG_KEY = "language_key"
        const val ENGLISH = "en"
        const val TURKISH = "tr"
        const val THEME = "theme"
        const val LIGHT = "light"
        const val DARK = "dark"
    }

    object Tag{
        const val INTERNET_CONNECTION = "Internet Connection"
        const val TASK_HANDLER_ERROR = "TaskHandler Error"
        const val RESPONSE_HANDLER_ERROR = "ResponseHandler Error"
        const val SAFE_CALL_ERROR = "ResponseHandler Error"
    }
}
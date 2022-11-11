package com.vanik.newsbook.domain

import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.Result
import java.util.Collections

object FilterLogic {

    fun filterResults(dbResultsLocal: List<ResultLocal>?, netResults: List<Result>?): List<ResultLocal> {
        if(dbResultsLocal!= null){
            Collections.reverse(dbResultsLocal)
        }
        val onlyFavoriteEmpty = dbResultsLocal == null && netResults != null
        val onlyNetEmpty = dbResultsLocal != null && netResults == null
        val favoriteAndNetNotEmpty = dbResultsLocal != null && netResults != null
        val filterResultsLocal = arrayListOf<ResultLocal>()
        if (favoriteAndNetNotEmpty) {
            filterResultsLocal.addAll(dbResultsLocal!!)
            val favoriteResults = arrayListOf<Result>()
            for(i in dbResultsLocal){
                favoriteResults.add(i.result)
            }
            val netFilterResults = netFilter(favoriteResults, netResults!!)
            for (i in netFilterResults){
                filterResultsLocal.add(ResultLocal(0,i,false))
            }

        } else if (onlyFavoriteEmpty) {
            for (i in netResults!!) {
                filterResultsLocal.add(ResultLocal(0, i, false))
            }
        } else if (onlyNetEmpty) {
            return dbResultsLocal!!
        }
        return filterResultsLocal
    }

    private fun netFilter(favoriteResults: List<Result>, netResults: List<Result>): List<Result> {
        val results = arrayListOf<Result>()
        var isFavorite: Boolean
        for (netResult in netResults) {
            isFavorite = false
            for (favoriteResult in favoriteResults) {
                if (favoriteResult.id == netResult.id) {
                    isFavorite = true
                    break
                }
            }
            if (!isFavorite) {
                results.add(netResult)
            }
        }
        return results
    }
}

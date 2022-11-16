package com.vanik.newsbook.domain

import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.Result
import java.util.*

object FilterLogic {

    fun filterResults(dbResultsLocal: List<ResultLocal>?, netResults: List<Result>?,isFirstCall : Boolean): List<ResultLocal> {
        if(dbResultsLocal!= null){ Collections.reverse(dbResultsLocal) }
        val filterResultsLocal = arrayListOf<ResultLocal>()
        if (dbResultsLocal != null && netResults != null) {
            if(isFirstCall) {
                filterResultsLocal.addAll(dbResultsLocal)
            }
            val favoriteResults = arrayListOf<Result>()
            for(i in dbResultsLocal){ favoriteResults.add(i.result) }
            val netFilterResults = netFilter(favoriteResults, netResults)
            for (i in netFilterResults){ filterResultsLocal.add(ResultLocal(0,i,false)) }
        } else if (netResults != null) { for (i in netResults) { filterResultsLocal.add(ResultLocal(0, i, false)) }
        } else if (dbResultsLocal != null) { return dbResultsLocal }
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
